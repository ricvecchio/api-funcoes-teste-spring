package com.funcoes.controller;

import com.funcoes.model.Conta;
import com.funcoes.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller responsável por expor endpoints REST de leitura das contas.
 * Esse controller pertence ao kafka-service, pois é o serviço que mantém os dados.
 */
@RestController
@RequestMapping("/api/contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaRepository contaRepository;

    /**
     * Retorna todas as contas cadastradas no banco.
     */
    @GetMapping
    public ResponseEntity<List<Conta>> listarContas() {
        List<Conta> contas = contaRepository.findAll();
        if (contas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(contas);
    }

    /**
     * Retorna as contas de um cliente específico, filtrando pelo CPF.
     */
    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<List<Conta>> listarPorCpf(@PathVariable String cpf) {
        String cpfLimpo = cpf.replaceAll("\\D", "");
        List<Conta> contas = contaRepository.findByClienteCpf(cpfLimpo);
        if (contas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(contas);
    }
}
