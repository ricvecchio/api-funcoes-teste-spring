package com.funcoes.util;

import com.funcoes.logging.LogEntry;
import org.springframework.stereotype.Component;

/**
 * Responsável por formatar logs em texto legível para exibição ou persistência.
 */
@Component
public class LogFormatter {

    public String format(LogEntry entry) {
        if (entry == null) {
            return "[LogEntry: null]";
        }

        return String.format(
                "[%s] Service: %s | Level: %s | Message: %s",
                entry.getTimestamp(),
                entry.getService(),
                entry.getLevel(),
                entry.getMessage()
        );
    }
}
