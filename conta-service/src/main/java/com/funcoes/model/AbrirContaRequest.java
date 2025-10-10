package com.funcoes.model;

import jakarta.validation.constraints.NotBlank;

public record AbrirContaRequest(
        @NotBlank String nomeCliente,
        @NotBlank String cpf,
        @NotBlank String tipoConta
) {
}

