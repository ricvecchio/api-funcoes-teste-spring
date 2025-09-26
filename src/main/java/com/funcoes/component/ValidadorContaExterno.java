package com.funcoes.component;

import org.springframework.stereotype.Component;

@Component
public class ValidadorContaExterno {

    public boolean validar() {
        // Simula 50% de chance de o sistema estar disponível
        return Math.random() > 0.5;
    }
}
