package com.funcoes.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EndpointController {

    @Resource(name = "requestMappingHandlerMapping")
    private RequestMappingHandlerMapping handlerMapping;

    public record EndpointDTO(String path, String methods, String controller, String methodName) {}

    @GetMapping("/endpoints")
    public List<EndpointDTO> listarEndpoints() {
        return handlerMapping.getHandlerMethods().entrySet().stream()
                .map(entry -> {
                    RequestMappingInfo mappingInfo = entry.getKey();
                    var handlerMethod = entry.getValue();

                    Set<String> paths = mappingInfo.getPathPatternsCondition() != null
                            ? mappingInfo.getPathPatternsCondition().getPatternValues()
                            : Set.of();

                    String methods = mappingInfo.getMethodsCondition() != null
                            ? mappingInfo.getMethodsCondition().getMethods().stream()
                            .map(Enum::name)
                            .sorted() // ordena alfabeticamente
                            .collect(Collectors.joining(", "))
                            : "";

                    String controller = handlerMethod.getBeanType().getSimpleName();
                    String methodName = handlerMethod.getMethod().getName();

                    return paths.stream()
                            .map(path -> new EndpointDTO(path, methods, controller, methodName))
                            .toList();
                })
                .flatMap(List::stream)
                // Filtra apenas endpoints que você criou
                .filter(e -> !e.path().startsWith("/error"))
                .filter(e -> !e.path().startsWith("/actuator"))
                .filter(e -> !e.path().equals("/favicon.ico"))
                .distinct()
                .sorted((a, b) -> a.path().compareTo(b.path()))
                .toList();
    }
}