package com.funcoes.log.controller;

import com.funcoes.logging.LogClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogClient logClient;

    @PostMapping("/info")
    public void logInfo(
            @RequestParam String acao,
            @RequestParam String mensagem
    ) {
        // Origem fixa "LogService"
        logClient.info("LogService", acao, mensagem);
    }

    @PostMapping("/error")
    public void logError(
            @RequestParam String acao,
            @RequestParam String mensagem
    ) {
        // O método error espera uma Exception, então criamos uma genérica com a mensagem
        Exception ex = new Exception(mensagem);
        logClient.error("LogService", acao, mensagem, ex);
    }
}
