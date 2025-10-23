package com.conta.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EndpointController {

    @Resource(name = "requestMappingHandlerMapping")
    private RequestMappingHandlerMapping handlerMapping;

    public record EndpointDTO(String path, String methods, String controller, String methodName) {
    }

    @GetMapping("/endpoints")
    public List<EndpointDTO> listarEndpoints() {
        return handlerMapping.getHandlerMethods().entrySet().stream()
                .flatMap(entry -> toDtos(entry.getKey(), entry.getValue()).stream())
                .filter(e -> !e.path().startsWith("/error"))
                .filter(e -> !e.path().startsWith("/actuator"))
                .filter(e -> !e.path().equals("/favicon.ico"))
                .distinct()
                .sorted(Comparator.comparing(EndpointDTO::path))
                .toList();
    }

    private List<EndpointDTO> toDtos(RequestMappingInfo info, HandlerMethod method) {
        Set<String> paths = Optional.ofNullable(info.getPathPatternsCondition())
                .map(c -> c.getPatternValues())
                .orElseGet(() -> {
                    PatternsRequestCondition legacy = info.getPatternsCondition();
                    return legacy != null ? legacy.getPatterns() : Set.of();
                });

        String methods = info.getMethodsCondition() != null
                ? info.getMethodsCondition().getMethods().stream()
                .map(Enum::name).sorted().collect(Collectors.joining(", "))
                : "";

        String controller = method.getBeanType().getSimpleName();
        String methodName = method.getMethod().getName();

        return paths.stream().map(p -> new EndpointDTO(p, methods, controller, methodName)).toList();
    }
}