package com.funcoes.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conta")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "tipo_conta", nullable = false)
    private String tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_conta", nullable = false)
    private StatusConta status = StatusConta.ATIVA;

    /**
     * Relacionamento N:1 com Cliente.
     * Várias contas podem pertencer a um mesmo cliente.
     * FetchType.EAGER é adequado aqui, pois normalmente você precisa
     * dos dados do cliente junto da conta.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false,
            foreignKey = @ForeignKey(name = "fk_conta_cliente"))
    private Cliente cliente;
}