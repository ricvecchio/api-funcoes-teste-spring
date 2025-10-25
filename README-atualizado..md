# 🧩 API FUNÇÕES - Sistema de Estudo em Arquitetura de Microserviços

## 📖 Descrição

API desenvolvida para estudo de arquitetura de microserviços com mensageria Kafka, utilizando Java 21 + Spring Boot 3.3.x e PostgreSQL.
O projeto é composto por microsserviços independentes que se comunicam de forma assíncrona via Kafka e síncrona via REST.

- **conta-service** → API REST responsável pela abertura e gestão de contas bancárias.
- **kafka-service** → Serviço assíncrono que consome mensagens Kafka para processar novas contas.
- **log-service** → Serviço dedicado a centralizar logs e métricas das operações distribuídas.

## 🚀 Principais Funcionalidades

### Conta Service (`conta-service`)
- Exposição de API REST para abertura de contas.
- Produção de mensagens Kafka no tópico `conta-aberturas`.
- Validação de CPF e tipo de conta antes do envio.
- Endpoints auxiliares (`/health`, `/endpoints`) para monitoramento e documentação.

### Kafka Service (`kafka-service`)
- Consumo de mensagens Kafka publicadas no tópico `conta-aberturas`.
- Criação automática de clientes e contas no PostgreSQL.
- Persistência via JPA/Hibernate.
- Observabilidade via `/health` e logs estruturados.

### Log Service (`log-service`)
- Recebe e armazena logs enviados pelos demais microserviços.
- Expõe endpoints REST para visualização de logs de INFO e ERROR.
- Integração nativa com **Datadog APM** e **Java Agent** para rastreabilidade distribuída.

## 🏗️ Arquitetura Geral

```
+------------------+        Kafka Topic: conta-aberturas        +------------------+
|  conta-service   |  --------------------------------------->  |  kafka-service   |
| (API REST)       |                                          | (Consumer Kafka) |
+------------------+                                          +------------------+
         |                                                           |
         | HTTP:8081                                                 | HTTP:8082
         |                                                           |
         v                                                           v
 PostgreSQL (schema contas)                             PostgreSQL (schema contas)
         |
         | ---> Produz logs via HTTP ---> log-service (8083)
```


## 🛠 Tecnologias Utilizadas

| Categoria | Tecnologias |
|------------|-------------|
| **Linguagem** | Java 21 |
| **Frameworks** | Spring Boot 3.3.x, Spring Data JPA, Spring Kafka |
| **Banco de Dados** | PostgreSQL 15 |
| **Mensageria** | Apache Kafka + Zookeeper |
| **Observabilidade** | Datadog (APM, Logs, Metrics), Actuator |
| **Containerização** | Docker, Docker Compose |
| **CI/CD** | GitHub Actions |
| **Ferramentas** | Maven, Lombok |
| **Frontend Integrado** | Angular 14 (projeto externo) |

## 🧱 Infraestrutura Docker Compose

Arquivo: `infra/docker-compose.yml`

**Serviços:**
- `postgres-db` – banco PostgreSQL.
- `zookeeper` – gerenciador de metadados Kafka.
- `kafka-broker` – broker principal Kafka.
- `conta-service` – API REST produtora.
- `kafka-service` – consumidor Kafka.
- `log-service` – centralizador de logs e métricas.
- `datadog-agent` – agente de observabilidade para métricas, logs e traces.
- `kafka-init` – inicializa tópicos automaticamente.

**Rede e volume:**
- Rede: `app-network`
- Volume persistente: `pgdata`

## 📊 Integração Datadog

A aplicação utiliza o **Datadog Agent** para coletar métricas de logs, traces e performance de cada microserviço.

### 🔗 Dashboard Criado via API

Dashboard:  
📈 **📊 API Funções - Microservices Dashboard**

Inclui:
- Requisições HTTP por serviço
- Mensagens Kafka processadas
- Uso de memória por container
- Erros 4xx / 5xx
- Logs em tempo real

### 🧠 Serviços Reconhecidos

A plataforma Datadog detecta automaticamente os serviços monitorados:

| Serviço | Ambiente | Latência (P95) | Health |
|----------|-----------|----------------|--------|
| **conta-service** | `env:docker` | 413ms | ✅ OK |
| **kafka-service** | `env:docker` | 135ms | ✅ OK |
| **log-service** | `env:docker` | 38.5ms | ✅ OK |

---

## 🧩 Arquitetura em Execução e Observabilidade

Abaixo, é possível visualizar a infraestrutura dos microserviços em execução via Docker e o painel de monitoramento no Datadog.

### 🐳 Containers em Execução
![Containers](./imagens/containers.png)

### 📊 Monitoramento Datadog
![Datadog Dashboard](./imagens/datadog.png)

---

## 🔑 Variáveis de Ambiente

Arquivo `.env`:

```bash
# PostgreSQL
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=contas_db

# Kafka
KAFKA_TOPIC_CONTAS_ABRIR=conta-aberturas
KAFKA_BOOTSTRAP_SERVERS=kafka-broker:9092

# Conta Service
CONTA_SERVICE_PORT=8081
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-db:5432/contas_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres

# Kafka Service
KAFKA_SERVICE_PORT=8082
SPRING_DATASOURCE_URL_KAFKA=jdbc:postgresql://postgres-db:5432/contas_db
SPRING_DATASOURCE_USERNAME_KAFKA=postgres
SPRING_DATASOURCE_PASSWORD_KAFKA=postgres

# Log Service
LOG_SERVICE_PORT=8083
```

## 🧩 Observabilidade e Logs

- Integração com **Datadog APM** via `dd-java-agent.jar` em cada container.
- Configuração automática no `Dockerfile` de cada serviço:

```dockerfile
RUN curl -L -o dd-java-agent.jar https://dtdg.co/latest-java-tracer
```

- Coleta automática de:
  - Traces (`trace.http.request`)
  - Logs (`service:log-service`)
  - Métricas de CPU e memória (`system.mem`, `container.cpu.usage`)

## 📦 Endpoints Principais

### Conta Service (`http://localhost:8081`)
| Método | Endpoint | Descrição |
|--------|-----------|------------|
| `POST` | `/api/contas/abrir` | Abre uma nova conta e envia mensagem Kafka. |
| `GET` | `/api/endpoints` | Lista os endpoints disponíveis. |
| `GET` | `/health` | Health check. |

### Kafka Service (`http://localhost:8082`)
| Método | Endpoint | Descrição |
|--------|-----------|------------|
| `GET` | `/api/contas` | Lista todas as contas persistidas. |
| `GET` | `/api/contas/cliente/{cpf}` | Lista contas por CPF. |
| `GET` | `/health` | Health check. |

### Log Service (`http://localhost:8083`)
| Método | Endpoint | Descrição |
|--------|-----------|------------|
| `POST` | `/api/logs/info` | Registra log de informação. |
| `POST` | `/api/logs/error` | Registra log de erro. |
| `GET` | `/actuator/health` | Verifica status do serviço. |

## ⚙️ Comandos Docker e Logs

### 📦 Build completo (sem cache)
```bash
mvn clean
docker compose down -v
docker compose build --no-cache
docker compose --env-file .env.local up -d
```

### 🪵 Logs dos serviços
```bash
docker logs -f conta-service
docker logs -f kafka-service
docker logs -f log-service
docker logs -f datadog-agent
```

### 🔍 Ver logs limitados
```bash
docker logs --tail 50 log-service
```

## 🧠 Diagnóstico dos Microserviços

| Serviço | Porta | Health |
|----------|-------|--------|
| **conta-service** | 8081 | ✅ `/actuator/health` |
| **kafka-service** | 8082 | ✅ `/actuator/health` |
| **log-service** | 8083 | ✅ `/actuator/health` |
| **datadog-agent** | 8126 | ✅ Healthy |
| **postgres-db** | 5432 | ✅ Healthy |
| **kafka-broker** | 9092 | ✅ Healthy |

## 🔮 Próximas Melhorias

- [ ] Criação de `auth-service` com JWT.
- [ ] Integração com API Gateway.
- [ ] Implementação de DLQ (Dead Letter Queue).
- [ ] Envio automático de métricas customizadas para o Datadog.
- [ ] Testes E2E com WireMock e Testcontainers.
- [ ] Deploy automatizado em VPS.

## 👨‍💻 Autor

**Ricardo Del Vecchio**  
Desenvolvedor Back-end | Arquitetura de Microsserviços | Java & Spring Boot

📍 GitHub: [@ricvecchio](https://github.com/ricvecchio)
