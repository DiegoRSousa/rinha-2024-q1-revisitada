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

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RequestMapping("/clientes")
@RestController
public class ClienteController {

    private ClienteRepository clienteRepository;
    private TransacaoRepository transacaoRepository;

    public ClienteController(ClienteRepository clienteRepository, TransacaoRepository transacaoRepository) {
        this.clienteRepository = clienteRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @PostMapping("{id}/transacoes")
    @Transactional
    public ResponseEntity<TransacaoResponse> novaTransacao(@PathVariable long id, @Valid @RequestBody TransacaoRequest request) {

        var cliente = clienteRepository.findByIdLock(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var transacao = request.toModel(cliente);

        if (!transacao.executar()) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);            
        }

        transacaoRepository.save(transacao);
        clienteRepository.save(cliente);
        
        return ResponseEntity.ok(new TransacaoResponse(cliente));
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<Extrato> getExtrato(@PathVariable long id) {
        var cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var ultimas10Transacoes = transacaoRepository.findByClienteIdOrderByRealizadaEmDesc(id);

        return ResponseEntity.ok(new Extrato(cliente, ultimas10Transacoes));

    }

}
