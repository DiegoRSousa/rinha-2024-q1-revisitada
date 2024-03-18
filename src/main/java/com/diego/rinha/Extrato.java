package com.diego.rinha;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Extrato(@JsonProperty("saldo") Saldo saldo, @JsonProperty("ultimas_transacoes") List<UltimaTransacao> transacaos) {
    public Extrato(Cliente cliente, List<Transacao> transacoes){
        this(new Saldo(cliente), transacoes.stream().map(t ->
                new UltimaTransacao(t.getValor(), t.getTipo(), t.getDescricao(), t.getRealizadaEm())
        ).toList());
    }
}

record Saldo(@JsonProperty("total") int total, @JsonProperty("data_extrato") LocalDateTime dataExtrato, @JsonProperty("limite") int limite) {
    public Saldo(Cliente cliente) {
        this(cliente.getSaldo(), LocalDateTime.now(), cliente.getLimite());

    }
}

record UltimaTransacao(@JsonProperty("valor")  int valor, @JsonProperty("tipo")  char tipo, @JsonProperty("descricao")  String descricao,
                       @JsonProperty("realizada_em") LocalDateTime realizadaEm) {
}