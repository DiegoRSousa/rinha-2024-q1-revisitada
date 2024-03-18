package com.diego.rinha;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TransacaoRequest(
        @NotNull @Positive int valor,
        @NotNull @Pattern(regexp = "[cd]") String tipo,
        @NotNull @Size(min = 1, max = 10) String descricao) {

    public Transacao toModel(Cliente cliente) {
        return new Transacao(valor, tipo.charAt(0), descricao, cliente);
    }
}