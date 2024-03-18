package com.diego.rinha;

public record TransacaoResponse(int limite, int saldo) {
    public TransacaoResponse(Cliente cliente){
        this(cliente.getLimite(), cliente.getSaldo());
    }
}
