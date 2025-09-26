package com.funcoes.model;

import jakarta.validation.constraints.NotBlank;

public record AbrirContaRequest(
        Long idCliente,
        @NotBlank String nomeCliente,
        @NotBlank String tipoConta
) {}
