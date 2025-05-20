package com.mentalwellness.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private Long amount;
    private String currency;
    private String receipt;
    private String notes;
}
