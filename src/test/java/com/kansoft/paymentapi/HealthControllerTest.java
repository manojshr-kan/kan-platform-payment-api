package com.kansoft.paymentapi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HealthControllerTest {

    @Test
    void healthReportsUp() {
        var body = new HealthController().health();
        assertEquals("UP", body.get("status"));
        assertEquals("payment-api", body.get("service"));
    }
}
