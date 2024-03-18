package com.diego.rinha;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int valor;
    private char tipo;
    private String descricao;
    @ManyToOne
    private Cliente cliente;
    private LocalDateTime realizadaEm;

    public Transacao(){}

    public Transacao(int valor, char tipo, String descricao, Cliente cliente) {
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.cliente = cliente;
        this.realizadaEm = LocalDateTime.now();
    }

    public boolean executar() {
        if ('c' == tipo) {
            cliente.creditar(valor);
            return true;
        }
        return cliente.debitar(valor);
    }

    public Long getId() {
        return id;
    }

    public int getValor() {
        return valor;
    }

    public char getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDateTime getRealizadaEm() {
        return realizadaEm;
    }

}
