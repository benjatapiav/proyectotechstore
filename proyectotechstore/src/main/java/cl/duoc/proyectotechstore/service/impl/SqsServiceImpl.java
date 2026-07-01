package cl.duoc.proyectotechstore.service.impl;

import cl.duoc.proyectotechstore.dto.AuditEvent;
import cl.duoc.proyectotechstore.service.SqsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
public class SqsServiceImpl implements SqsService {

    private final SqsClient sqsClient;
    private final ObjectMapper mapper = new ObjectMapper();

    private static final String QUEUE_URL =
            "https://sqs.us-east-1.amazonaws.com/110760778387/techstore-audit-queue";

    public SqsServiceImpl(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Override
    public void enviarMensaje(AuditEvent evento) {

        try {

            String json = mapper.writeValueAsString(evento);

            SendMessageRequest request =
                    SendMessageRequest.builder()
                            .queueUrl(QUEUE_URL)
                            .messageBody(json)
                            .build();

            sqsClient.sendMessage(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}