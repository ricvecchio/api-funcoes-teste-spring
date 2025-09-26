package com.funcoes.controller;

import com.funcoes.service.ContaService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping("/abrir")
    public String abrirConta(@RequestParam(required = false) Long idCliente,
                             @RequestParam String nomeCliente,
                             @RequestParam String tipoConta) {
        contaService.abrirConta(idCliente, nomeCliente, tipoConta);
        return "Solicitação de abertura de conta processada!";
    }
}

