package com.diego.rinha;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransacaoRepository extends JpaRepository<Transacao, Long>{

    @Query(value = "select t.id, t.valor, t.tipo, t.descricao, t.realizada_em, t.cliente_id from Transacao t where t.cliente_id = :clienteId order by realizada_em desc limit 10", nativeQuery = true)
    List<Transacao> findByClienteIdOrderByRealizadaEmDesc(long clienteId);
}
