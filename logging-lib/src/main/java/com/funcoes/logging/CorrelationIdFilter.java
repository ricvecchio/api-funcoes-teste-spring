package com.funcoes.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.UUID;

public class CorrelationIdFilter extends HttpFilter {

    public static final String TRACE_HEADER = "X-Correlation-Id";

    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {

        String correlationId = request.getHeader(TRACE_HEADER);

        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        CorrelationId.setId(correlationId);
        MDC.put(TRACE_HEADER, correlationId);
        response.setHeader(TRACE_HEADER, correlationId);

        try {
            chain.doFilter(request, response);
        } finally {
            CorrelationId.clear();
            MDC.remove(TRACE_HEADER);
        }
    }
}
