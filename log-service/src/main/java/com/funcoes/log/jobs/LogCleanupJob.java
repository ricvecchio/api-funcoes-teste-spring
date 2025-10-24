package com.funcoes.log.jobs;

import com.funcoes.log.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Job agendado que limpa logs antigos periodicamente.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LogCleanupJob {

    private final LogService logService;

    /**
     * Executa todo dia à meia-noite (00:00).
     * Remove logs com mais de 30 dias.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void cleanupOldLogs() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(30);
        log.info("Executando limpeza de logs anteriores a {}", cutoff);
        logService.purgeOlderThan(cutoff);
    }
}
