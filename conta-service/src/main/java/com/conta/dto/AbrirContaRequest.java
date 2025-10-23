package com.conta.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AbrirContaRequest {
    private String nomeCliente;
    private String cpf;
    private String tipoConta;
}
