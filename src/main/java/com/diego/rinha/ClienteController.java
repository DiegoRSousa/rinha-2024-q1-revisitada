package com.diego.rinha;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RequestMapping("/clientes")
@RestController
public class ClienteController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("{id}/transacoes")
    @Transactional
    public ResponseEntity<TransacaoResponse> novaTransacao(@PathVariable long id,
            @Valid @RequestBody TransacaoRequest request) {

        var cliente = manager.find(Cliente.class, id, LockModeType.PESSIMISTIC_WRITE);

        if (null == cliente) {
            return ResponseEntity.notFound().build();
        }

        var transacao = request.toModel(cliente);

        if (!transacao.executar()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        manager.persist(transacao);
        manager.persist(cliente);

        return ResponseEntity.ok(new TransacaoResponse(cliente));
    }

    @GetMapping("/{id}/extrato")
    @Transactional
    public ResponseEntity<Extrato> getExtrato(@PathVariable long id) {

        var cliente = manager.find(Cliente.class, id, LockModeType.PESSIMISTIC_WRITE);

        var ultimas10Transacoes = manager.createNativeQuery("""
                    select * from Transacao t
                    where t.cliente_id = :clienteId
                    order by realizada_em desc limit 10
                """, Transacao.class)
                .setParameter("clienteId", id)
                .getResultList();

        return ResponseEntity.ok(new Extrato(cliente, ultimas10Transacoes));

    }

}
