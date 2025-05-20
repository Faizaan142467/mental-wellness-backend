package com.mentalwellness.controller;

import com.mentalwellness.dto.PaymentRequestDTO;
import com.mentalwellness.dto.PaymentResponseDTO;
import com.mentalwellness.service.RazorpayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {
    
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private RazorpayService razorpayService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequestDTO paymentRequest) {
        try {
            logger.info("Received payment request: {}", paymentRequest);
            
            if (paymentRequest.getAmount() == null || paymentRequest.getAmount() <= 0) {
                return ResponseEntity.badRequest().body("Invalid amount");
            }
            
            if (paymentRequest.getCurrency() == null || paymentRequest.getCurrency().isEmpty()) {
                return ResponseEntity.badRequest().body("Currency is required");
            }
            
            PaymentResponseDTO response = razorpayService.createPayment(paymentRequest);
            logger.info("Payment created successfully: {}", response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error creating payment: ", e);
            return ResponseEntity.badRequest().body("Error creating payment: " + e.getMessage());
        }
    }
}
