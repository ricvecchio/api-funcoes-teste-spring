package com.funcoes.logging;

public final class Correlation {
    private static final ThreadLocal<String> TRACE = new ThreadLocal<>();
    public static void setTraceId(String id) { TRACE.set(id); }
    public static String getTraceId() { return TRACE.get(); }
    public static void clear() { TRACE.remove(); }
}
