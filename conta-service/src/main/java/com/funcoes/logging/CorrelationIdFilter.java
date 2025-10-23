package com.funcoes.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * Intercepta todas as requisições HTTP e garante a presença de um X-Trace-Id.
 */
@Component
public class CorrelationIdFilter implements Filter {

    public static final String TRACE_HEADER = "X-Trace-Id";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String traceId = req.getHeader(TRACE_HEADER);
            if (traceId == null || traceId.isBlank()) {
                traceId = UUID.randomUUID().toString();
            }
            CorrelationId.setId(traceId);
            chain.doFilter(request, response);
        } finally {
            CorrelationId.clear();
        }
    }
}
