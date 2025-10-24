package com.funcoes.log.controller;

import com.funcoes.log.dto.LogRequest;
import com.funcoes.logging.LogClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogClient logClient;

    @PostMapping("/info")
    public ResponseEntity<Void> logInfo(@RequestBody LogRequest request) {
        logClient.info("LogService", request.getAcao(), request.getMensagem());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/error")
    public ResponseEntity<Void> logError(@RequestBody LogRequest request) {
        Exception ex = new Exception(request.getMensagem());
        logClient.error("LogService", request.getAcao(), request.getMensagem(), ex);
        return ResponseEntity.ok().build();
    }

}
