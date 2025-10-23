package com.funcoes.service;

import com.funcoes.logging.LogEntry;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class LogService {

    private final List<LogEntry> logs = new CopyOnWriteArrayList<>();

    @Value("${log.max.entries:5000}")
    private int maxEntries;

    private final MeterRegistry meter;

    public LogService(MeterRegistry meterRegistry) {
        this.meter = meterRegistry;
    }

    public void save(LogEntry log) {
        logs.add(log);
        while (logs.size() > maxEntries) {
            logs.remove(0);
        }
        // Métrica: contagem de logs por level
        meter.counter("logs.ingested", "level", safe(log.getLevel()), "service", safe(log.getService())).increment();
    }

    public List<LogEntry> getAll() {
        return logs.stream()
                .sorted(Comparator.comparing(LogEntry::getTimestamp).reversed())
                .toList();
    }

    public void clear() {
        logs.clear();
    }

    public void purgeOlderThan(LocalDateTime cutoff) {
        logs.removeIf(l -> l.getTimestamp().isBefore(cutoff));
    }

    private String safe(String s) { return s == null ? "unknown" : s; }
}
