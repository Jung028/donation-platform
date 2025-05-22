package com.charity.donationservice.dto;

import com.charity.donationservice.model.DonationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonationResponse {
    
    private Long id;
    private Long donorId;
    private Long campaignId;
    private BigDecimal amount;
    private String currency;
    private DonationStatus status;
    private String transactionId;
    private LocalDateTime createdAt;
}
