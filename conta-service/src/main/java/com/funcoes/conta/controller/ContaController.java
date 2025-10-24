package com.funcoes.conta.controller;

import com.funcoes.conta.dto.AbrirContaRequest;
import com.funcoes.conta.service.ContaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
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
}
