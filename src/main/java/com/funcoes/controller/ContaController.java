package com.funcoes.controller;

import com.funcoes.model.AbrirContaRequest;
import com.funcoes.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contas")
@RequiredArgsConstructor
public class ContaController {
    private final ContaService contaService;

    // ============================================
    // 🔹 Abertura de conta
    // ============================================
    @PostMapping("/abrir")
    public ResponseEntity<String> abrirConta(@RequestBody AbrirContaRequest request) {
        contaService.abrirConta(request);
        return ResponseEntity.ok("Solicitação de abertura de conta processada!");
    }

    // ============================================
    // 🔹 Consultas
    // ============================================
//    @GetMapping
//    public ResponseEntity<List<Conta>> listarContas() {
//        return ResponseEntity.ok(contaService.listarContas());
//    }

//    @GetMapping("/{idConta}")
//    public ResponseEntity<Conta> buscarConta(@PathVariable Long idConta) {
//        return contaService.buscarConta(idConta)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

//    @GetMapping("/cliente/{idCliente}")
//    public ResponseEntity<List<Conta>> listarPorCliente(@PathVariable Long idCliente) {
//        return ResponseEntity.ok(contaService.listarPorCliente(idCliente));
//    }

    // ============================================
    // 🔹 Ações de negócio
    // ============================================
//    @PatchMapping("/{idConta}/fechar")
//    public ResponseEntity<String> fecharConta(@PathVariable Long idConta) {
//        contaService.fecharConta(idConta);
//        return ResponseEntity.ok("Conta fechada com sucesso!");
//    }

    // ============================================
    // 🔹 Observabilidade / Infra
    // ============================================
//    @GetMapping("/health")
//    public ResponseEntity<Map<String, String>> healthCheck() {
//        boolean externoOk = contaService.healthCheckValidador();
//        Map<String, String> status = Map.of(
//                "validadorExterno", externoOk ? "UP" : "DOWN"
//        );
//        return ResponseEntity.ok(status);
//    }

}

