package com.funcoes.controller;

import com.funcoes.model.AbrirContaRequest;
import com.funcoes.model.Conta;
import com.funcoes.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP", "service", "conta-service"));
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> info() {
        return ResponseEntity.ok(Map.of(
                "name", "conta-service",
                "version", "1.0.0",
                "status", "running"
        ));
    }
}



