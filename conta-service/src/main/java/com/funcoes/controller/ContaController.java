package com.funcoes.controller;

import com.funcoes.model.AbrirContaRequest;
import com.funcoes.model.Conta;
import com.funcoes.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/contas")
@RequiredArgsConstructor
public class ContaController {
    private final ContaService contaService;

    @PostMapping("/abrir")
    public ResponseEntity<String> abrirConta(@RequestBody AbrirContaRequest request) {
        contaService.abrirConta(request);
        return ResponseEntity.ok("Solicitação de abertura de conta processada!");
    }

    @GetMapping
    public ResponseEntity<List<Conta>> listarContas() {
        return ResponseEntity.ok(contaService.listarContas());
    }

}




