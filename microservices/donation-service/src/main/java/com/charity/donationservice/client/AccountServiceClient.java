package com.charity.donationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@FeignClient(name = "account-service", url = "${app.services.account-service.url}")
public interface AccountServiceClient {
    
    @GetMapping("/api/accounts/{accountId}")
    ResponseEntity<AccountDto> getAccountById(@PathVariable("accountId") Long accountId);
    
    @GetMapping("/api/accounts/{accountId}/balance")
    ResponseEntity<BalanceDto> getAccountBalance(@PathVariable("accountId") Long accountId);
    
    @PostMapping("/api/accounts/{accountId}/validate-funds")
    ResponseEntity<Boolean> validateFunds(@PathVariable("accountId") Long accountId, @RequestBody FundsValidationRequest request);
    
    record AccountDto(Long id, String name, String email) {}
    
    record BalanceDto(BigDecimal amount, String currency) {}
    
    record FundsValidationRequest(BigDecimal amount, String currency) {}
}
