package com.kansoft.paymentapi;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PaymentController {

    private final SqsTemplate sqsTemplate;
    private final String queueName;

    public PaymentController(SqsTemplate sqsTemplate,
                             @Value("${app.queue-name:payment-api-queue}") String queueName) {
        this.sqsTemplate = sqsTemplate;
        this.queueName = queueName;
    }

    @PostMapping("/payments")
    public Map<String, String> createPayment(@RequestBody Map<String, Object> payment) {
        sqsTemplate.send(queueName, payment);
        return Map.of("status", "accepted", "queue", queueName);
    }
}
