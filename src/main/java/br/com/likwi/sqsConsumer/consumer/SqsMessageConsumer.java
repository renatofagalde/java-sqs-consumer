package br.com.likwi.sqsConsumer.consumer;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class SqsMessageConsumer {

    private static final Logger log = LoggerFactory.getLogger(SqsMessageConsumer.class);

    @SqsListener(value = "${sqs.queue.name}")
    public void receiveMessage(String message) {
        try {

            log.info("Mensagem recebida da fila SQS: {}", message);
            log.debug("Mensagem confirmada com sucesso");

        } catch (Exception e) {
            log.error("Erro ao processar mensagem SQS: {}", e.getMessage(), e);
        }
    }
}