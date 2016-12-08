package com.example.billing;

import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

public class Client {

    private final RestOperations restTemplate;

    public Client(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void billUser(String userId, int amount) {
        restTemplate.postForEntity("//billing/reocurringPayment", amount, String.class);
    }
}
