package com.funcoes.service;

import com.funcoes.logging.LogEntry;
import com.funcoes.util.LogFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Serviço responsável por gerenciar logs em memória.
 * Não depende de banco de dados.
 */
@Slf4j
@Service
public class LogService {

    private final List<LogEntry> logs = new CopyOnWriteArrayList<>();

    /**
     * Retorna todos os logs ordenados por data decrescente.
     */
    public List<LogEntry> getAllLogs() {
        return logs.stream()
                .sorted(Comparator.comparing(LogEntry::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Adiciona um novo log.
     */
    public void addLog(LogEntry entry) {
        if (entry.getTimestamp() == null) {
            entry.setTimestamp(LocalDateTime.now());
        }
        logs.add(entry);
        log.info(LogFormatter.format(entry));
    }

    /**
     * Remove todos os logs anteriores à data especificada.
     */
    public void purgeOlderThan(LocalDateTime cutoffDate) {
        logs.removeIf(l -> l.getTimestamp().isBefore(cutoffDate));
        log.info("Removidos logs anteriores a {}", cutoffDate);
    }

    /**
     * Filtra logs por nome de serviço e ação.
     */
    public List<LogEntry> findByServiceAndAction(String serviceName, String action) {
        return logs.stream()
                .filter(l -> l.getServiceName().equalsIgnoreCase(serviceName))
                .filter(l -> l.getAction().equalsIgnoreCase(action))
                .collect(Collectors.toList());
    }
}
