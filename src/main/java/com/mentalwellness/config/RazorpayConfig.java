package com.mentalwellness.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Configuration
public class RazorpayConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(RazorpayConfig.class);

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Bean
    public RazorpayClient razorpayClient() {
        try {
            logger.info("Initializing Razorpay client with key ID: {}", keyId);
            RazorpayClient client = new RazorpayClient(keyId, keySecret);
            logger.info("Razorpay client initialized successfully");
            return client;
        } catch (RazorpayException e) {
            logger.error("Failed to initialize Razorpay client: ", e);
            throw new RuntimeException("Failed to initialize Razorpay client", e);
        }
    }
} 