package com.kansoft.paymentapi;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PaymentConsumer {

    private static final Logger log = LoggerFactory.getLogger(PaymentConsumer.class);

    @SqsListener("${app.queue-name:payment-api-queue}")
    public void handle(Map<String, Object> payment) {
        log.info("Processed payment from queue: {}", payment);
    }
}
