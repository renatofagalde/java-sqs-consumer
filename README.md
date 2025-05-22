# 🚀 SQS Consumer - Likwi

Um projeto Spring Boot para consumir mensagens de filas Amazon SQS usando LocalStack para desenvolvimento local.

## 📋 Sobre o Projeto

Este projeto implementa um consumer de mensagens SQS utilizando Spring Boot e AWS Spring Cloud. A aplicação está configurada para funcionar com LocalStack, permitindo desenvolvimento e testes locais sem a necessidade de recursos AWS reais.

### 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **AWS Spring Cloud SQS**
- **LocalStack** (para simulação local do SQS)
- **Docker & Docker Compose**
- **Logback** com saída JSON

## 🏗️ Arquitetura

O projeto possui uma arquitetura simples e eficiente:

- **SqsMessageConsumer**: Responsável por consumir mensagens da fila
- **AwsSqsConfig**: Configuração do cliente SQS assíncrono
- **LocalStack**: Simula o ambiente AWS SQS localmente

## 🚀 Como Executar

### 1. Inicializar o Ambiente Local

```bash
docker-compose up -d
```

Este comando irá:
- Inicializar o LocalStack na porta 4566
- Criar automaticamente a fila FIFO `EXEMPLOSQS.fifo`
- Configurar todas as dependências necessárias

### 2. Executar a Aplicação

```bash
./mvnw spring-boot:run
```

## 🔧 Comandos AWS CLI Úteis

### Listar Filas Disponíveis
```shell
aws --endpoint-url=http://localhost:4566 sqs list-queues
```

### Enviar Mensagem para a Fila
```shell
aws --endpoint-url=http://localhost:4566 sqs send-message \
  --queue-url "http://sqs.sa-east-1.localhost.localstack.cloud:4566/000000000000/EXEMPLOSQS.fifo" \
  --message-body '{"eventId":"123","type":"test","message":"Primeira mensagem de teste!"}' \
  --message-group-id "test-group" \
  --message-deduplication-id "msg-$(date +%s)"
```

### Receber Mensagem da Fila
```shell
aws --endpoint-url=http://localhost:4566 sqs receive-message \
  --queue-url "http://sqs.sa-east-1.localhost.localstack.cloud:4566/000000000000/EXEMPLOSQS.fifo"
```

### Verificar Quantidade de Mensagens na Fila
```shell
aws --endpoint-url=http://localhost:4566 sqs get-queue-attributes \
  --queue-url "http://sqs.sa-east-1.localhost.localstack.cloud:4566/000000000000/EXEMPLOSQS.fifo" \
  --attribute-names ApproximateNumberOfMessages
```

### Receber Múltiplas Mensagens (até 10)
```shell
aws --endpoint-url=http://localhost:4566 sqs receive-message \
  --queue-url "http://sqs.sa-east-1.localhost.localstack.cloud:4566/000000000000/EXEMPLOSQS.fifo" \
  --max-number-of-messages 10
```

### Limpar Todas as Mensagens da Fila
```shell
aws --endpoint-url=http://localhost:4566 sqs purge-queue \
  --queue-url "http://sqs.sa-east-1.localhost.localstack.cloud:4566/000000000000/EXEMPLOSQS.fifo"
```

## ⚙️ Configuração

### Variáveis de Ambiente

| Variável | Valor Padrão | Descrição |
|----------|-------------|-----------|
| `AWS_ACCESS_KEY` | `test` | Chave de acesso AWS (LocalStack) |
| `AWS_SECRET_KEY` | `test` | Chave secreta AWS (LocalStack) |
| `AWS_SQS_ENDPOINT` | `http://localhost:4566` | Endpoint do SQS |
| `SQS_QUEUE_NAME` | `EXEMPLOSQS.fifo` | Nome da fila SQS |

### Configurações do Consumer

- **Máximo de mensagens concorrentes**: 10
- **Timeout de polling**: 20 segundos
- **Visibilidade da mensagem**: 30 segundos
- **Máximo de mensagens por poll**: 10

## 📁 Estrutura do Projeto

```
src/
├── main/
│   ├── java/br/com/likwi/sqsConsumer/
│   │   ├── config/
│   │   │   └── AwsSqsConfig.java          # Configuração do SQS
│   │   ├── consumer/
│   │   │   └── SqsMessageConsumer.java    # Consumer de mensagens
│   │   └── SQSConsumerApplication.java    # Classe principal
│   └── resources/
│       ├── application.yaml               # Configurações da aplicação
│       └── logback-spring.xml            # Configuração de logs
└── test/
    └── java/br/com/likwi/sqsConsumer/
        └── SQSConsumerApplicationTests.java
```

## 📊 Logs

A aplicação está configurada para gerar logs em formato JSON, facilitando a integração com ferramentas de monitoramento como Datadog. Os logs incluem:

- Timestamp em UTC
- Nível do log
- Nome do logger
- Thread
- Mensagem
- Contexto (MDC)
- Stack traces em caso de erro

## 🧪 Testando o Consumer

1. **Inicie o ambiente**: `docker-compose up -d`
2. **Execute a aplicação**: `./mvnw spring-boot:run`
3. **Envie uma mensagem** usando o comando AWS CLI fornecido acima
4. **Verifique os logs** da aplicação para confirmar o processamento

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -am 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

---

**🔗 Links Úteis:**
- [LocalStack Documentation](https://docs.localstack.cloud/)
- [AWS SQS Documentation](https://docs.aws.amazon.com/sqs/)
- [Spring Cloud AWS](https://spring.io/projects/spring-cloud-aws)