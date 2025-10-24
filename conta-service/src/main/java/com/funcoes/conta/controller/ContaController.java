package com.funcoes.conta.controller;

import com.funcoes.conta.dto.AbrirContaRequest;
import com.funcoes.conta.model.Conta;
import com.funcoes.conta.service.ContaService;
import com.funcoes.conta.repository.ContaRepository;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    private final ContaService contaService;
    private final ContaRepository contaRepository;

    public ContaController(ContaService contaService, ContaRepository contaRepository) {
        this.contaService = contaService;
        this.contaRepository = contaRepository;
    }

    @PostMapping("/abrir")
    public ResponseEntity<Map<String, String>> abrirConta(@Valid @RequestBody AbrirContaRequest request) {
        contaService.abrirConta(request);

        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "✅ Solicitação de abertura de conta processada!");
        response.put("status", "ACCEPTED");
        response.put("timestamp", Instant.now().toString());

        return ResponseEntity.accepted().body(response);
    }

    @GetMapping
    public ResponseEntity<List<Conta>> listarContas() {
        List<Conta> contas = contaRepository.findAll();
        if (contas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(contas);
    }

}
