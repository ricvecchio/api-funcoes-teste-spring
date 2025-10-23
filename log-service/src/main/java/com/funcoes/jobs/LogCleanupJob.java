package com.funcoes.jobs;

import com.funcoes.service.LogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LogCleanupJob {

    private final LogService logService;

    @Value("${log.retention.minutes:60}")
    private long retentionMinutes;

    public LogCleanupJob(LogService logService) {
        this.logService = logService;
    }

    // roda a cada 1 min
    @Scheduled(fixedDelay = 60_000)
    public void cleanup() {
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(retentionMinutes);
        logService.purgeOlderThan(cutoff);
    }
}
