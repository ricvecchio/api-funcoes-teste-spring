package com.funcoes.controller;

import com.funcoes.logging.LogClient;
import com.funcoes.model.Conta;
import com.funcoes.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaRepository contaRepository;
    private final LogClient log;

    private static final String SERVICE_NAME = "KafkaService";
    private static final String TOPIC_NAME = "conta-aberturas";

    @GetMapping
    public ResponseEntity<List<Conta>> listarContas() {
        log.info(SERVICE_NAME, TOPIC_NAME, "Recebida requisição para listar contas.");
        try {
            List<Conta> contas = contaRepository.findAll();

            if (contas.isEmpty()) {
                log.warn(SERVICE_NAME, TOPIC_NAME, "Nenhuma conta encontrada no banco de dados.");
                return ResponseEntity.noContent().build();
            }

            log.success(SERVICE_NAME, TOPIC_NAME, "LIST_CONTAS", "Listagem retornada com " + contas.size() + " conta(s).");
            return ResponseEntity.ok(contas);

        } catch (Exception e) {
            log.error(SERVICE_NAME, TOPIC_NAME, "Erro ao listar contas: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
