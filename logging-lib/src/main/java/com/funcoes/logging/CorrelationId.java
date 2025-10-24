package com.funcoes.logging;

public class CorrelationId {

    private static final ThreadLocal<String> correlationId = new ThreadLocal<>();

    public static void setId(String id) {
        correlationId.set(id);
    }

    public static String getId() {
        return correlationId.get();
    }

    public static void clear() {
        correlationId.remove();
    }
}
