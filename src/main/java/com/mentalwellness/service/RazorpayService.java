package com.mentalwellness.service;

import com.mentalwellness.dto.PaymentRequestDTO;
import com.mentalwellness.dto.PaymentResponseDTO;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {
    
    private static final Logger logger = LoggerFactory.getLogger(RazorpayService.class);

    @Autowired
    private RazorpayClient razorpayClient;

    public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequest) throws RazorpayException {
        try {
            logger.info("Creating Razorpay order with amount: {}, currency: {}", 
                paymentRequest.getAmount(), paymentRequest.getCurrency());
            
            JSONObject options = new JSONObject();
            options.put("amount", paymentRequest.getAmount());
            options.put("currency", paymentRequest.getCurrency());
            options.put("receipt", paymentRequest.getReceipt());
            
            // Create a JSONObject for notes
            JSONObject notes = new JSONObject();
            notes.put("description", paymentRequest.getNotes());
            notes.put("appointment_id", "123");
            notes.put("patient_id", "456");
            options.put("notes", notes);

            logger.info("Razorpay options: {}", options.toString());
            
            Order order = razorpayClient.orders.create(options);
            logger.info("Razorpay order created: {}", order.toString());
            
            PaymentResponseDTO response = new PaymentResponseDTO();
            response.setId(order.get("id").toString());
            response.setEntity(order.get("entity").toString());
            response.setAmount(Long.parseLong(order.get("amount").toString()));
            response.setCurrency(order.get("currency").toString());
            response.setStatus(order.get("status").toString());
            response.setOrderId(order.get("id").toString());
            response.setNotes(order.get("notes").toString());
            
            // Handle created_at as string instead of trying to parse it as Long
            Object createdAt = order.get("created_at");
            response.setCreatedAt(createdAt != null ? createdAt.toString() : null);
            
            return response;
        } catch (RazorpayException e) {
            logger.error("Razorpay error: ", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error: ", e);
            throw new RazorpayException("Error creating payment: " + e.getMessage());
        }
    }
}
