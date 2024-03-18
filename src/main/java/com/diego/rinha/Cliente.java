package com.diego.rinha;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int limite;
    private int saldo;

    public boolean debitar(int valor){
        if(saldo + limite < valor)
            return false;

        saldo -= valor;
        return true;
    }

    public void creditar(int valor) {
        saldo += valor;
    }

    public Long getId() {
        return id;
    }

    public int getLimite() {
        return limite;
    }
    public int getSaldo() {
        return saldo;
    }
}
