package com.mentalwellness.dto;

import lombok.Data;

@Data
public class PaymentResponseDTO {
    private String id;
    private String entity;
    private Long amount;
    private String currency;
    private String status;
    private String orderId;
    private String invoiceId;
    private String international;
    private String method;
    private Long amountRefunded;
    private String refundStatus;
    private Boolean captured;
    private String description;
    private String cardId;
    private String bank;
    private String wallet;
    private String vpa;
    private String email;
    private String contact;
    private String notes;
    private String fee;
    private String tax;
    private String errorCode;
    private String errorDescription;
    private String errorSource;
    private String errorStep;
    private String errorReason;
    private String acquirerData;
    private String createdAt;
}
