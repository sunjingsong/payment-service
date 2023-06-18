package com.javatechie.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.paymentservice.entity.Payment;
import com.javatechie.paymentservice.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    public Payment doPayment(Payment payment) {
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        try {
            log.info("PaymentService request: {}", new ObjectMapper().writeValueAsString(payment));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return repository.save(payment);
    }

    public String paymentProcessing() {
        // api should be 3rd party payment gateway (paypal, paytm...)
        return new Random().nextBoolean()? "success": "false";

    }

    public Payment findPaymentHistoryByOrderId(int orderId) {
        log.info("findPaymentHistoryByOrderId: {}", orderId);
        return repository.findByOrderId(orderId);
    }

}
