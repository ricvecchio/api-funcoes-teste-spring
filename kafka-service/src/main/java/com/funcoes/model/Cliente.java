package com.funcoes.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "clientes",
        uniqueConstraints = @UniqueConstraint(name = "uk_cliente_cpf", columnNames = "cpf")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    /**
     * CPF armazenado sem pontuação, com até 11 caracteres.
     * Exemplo: 12345678901
     */
    @Column(nullable = false, length = 11)
    private String cpf;

    public void setCpf(String cpf) {
        this.cpf = cpf != null ? cpf.replaceAll("\\D", "") : null;
    }
}