package com.funcoes.log.util;

import com.funcoes.logging.LogEntry;
import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;

/**
 * Utilitário responsável por formatar logs em uma string legível.
 */
@UtilityClass
public class LogFormatter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String format(LogEntry entry) {
        return String.format("[%s] [%s] [%s] %s",
                entry.getTimestamp().format(FORMATTER),
                entry.getServiceName(),
                entry.getAction(),
                entry.getMessage());
    }
}
