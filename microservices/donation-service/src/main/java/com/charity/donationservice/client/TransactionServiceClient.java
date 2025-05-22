package com.charity.donationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@FeignClient(name = "transaction-service", url = "${app.services.transaction-service.url}")
public interface TransactionServiceClient {
    
    @PostMapping("/api/transactions")
    ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest request);
    
    record TransactionRequest(
            Long sourceAccountId, 
            Long destinationAccountId, 
            BigDecimal amount, 
            String currency, 
            String description) {}
    
    record TransactionResponse(
            String transactionId, 
            String status, 
            BigDecimal amount, 
            String currency) {}
}
