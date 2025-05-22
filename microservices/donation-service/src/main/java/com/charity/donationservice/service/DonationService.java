package com.charity.donationservice.service;

import com.charity.donationservice.client.AccountServiceClient;
import com.charity.donationservice.client.TransactionServiceClient;
import com.charity.donationservice.dto.DonationRequest;
import com.charity.donationservice.dto.DonationResponse;
import com.charity.donationservice.exception.InsufficientFundsException;
import com.charity.donationservice.exception.ResourceNotFoundException;
import com.charity.donationservice.model.Donation;
import com.charity.donationservice.model.DonationStatus;
import com.charity.donationservice.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DonationService {
    
    private final DonationRepository donationRepository;
    private final AccountServiceClient accountServiceClient;
    private final TransactionServiceClient transactionServiceClient;
    
    @Transactional
    public DonationResponse processDonation(DonationRequest request) {
        log.info("Processing donation request: {}", request);
        
        // Validate funds
        boolean hasSufficientFunds = validateFunds(request.getDonorId(), request.getAmount(), request.getCurrency());
        if (!hasSufficientFunds) {
            throw new InsufficientFundsException("Donor has insufficient funds for this donation");
        }
        
        // Create donation record
        Donation donation = new Donation();
        donation.setDonorId(request.getDonorId());
        donation.setCampaignId(request.getCampaignId());
        donation.setAmount(request.getAmount());
        donation.setCurrency(request.getCurrency());
        donation.setStatus(DonationStatus.PENDING);
        
        donation = donationRepository.save(donation);
        log.info("Created donation record with ID: {}", donation.getId());
        
        try {
            // Trigger transaction
            donation.setStatus(DonationStatus.PROCESSING);
            donation = donationRepository.save(donation);
            
            // Call transaction service to process payment
            TransactionServiceClient.TransactionRequest transactionRequest = new TransactionServiceClient.TransactionRequest(
                    request.getDonorId(),
                    request.getCampaignId(),
                    request.getAmount(),
                    request.getCurrency(),
                    "Donation to campaign " + request.getCampaignId()
            );
            
            TransactionServiceClient.TransactionResponse transactionResponse = 
                    transactionServiceClient.createTransaction(transactionRequest).getBody();
            
            if (transactionResponse != null) {
                donation.setTransactionId(transactionResponse.transactionId());
                donation.setStatus(DonationStatus.COMPLETED);
                donation = donationRepository.save(donation);
                log.info("Donation completed with transaction ID: {}", transactionResponse.transactionId());
            } else {
                donation.setStatus(DonationStatus.FAILED);
                donation = donationRepository.save(donation);
                log.error("Transaction failed for donation ID: {}", donation.getId());
            }
        } catch (Exception e) {
            donation.setStatus(DonationStatus.FAILED);
            donation = donationRepository.save(donation);
            log.error("Error processing donation: {}", e.getMessage(), e);
            throw e;
        }
        
        return mapToResponse(donation);
    }
    
    public DonationResponse getDonationById(Long id) {
        log.info("Fetching donation with ID: {}", id);
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found with ID: " + id));
        return mapToResponse(donation);
    }
    
    public List<DonationResponse> getDonationsByDonor(Long donorId) {
        log.info("Fetching donations for donor ID: {}", donorId);
        return donationRepository.findByDonorId(donorId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public List<DonationResponse> getDonationsByCampaign(Long campaignId) {
        log.info("Fetching donations for campaign ID: {}", campaignId);
        return donationRepository.findByCampaignId(campaignId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    private boolean validateFunds(Long accountId, java.math.BigDecimal amount, String currency) {
        log.info("Validating funds for account ID: {}, amount: {}, currency: {}", accountId, amount, currency);
        try {
            AccountServiceClient.FundsValidationRequest validationRequest = 
                    new AccountServiceClient.FundsValidationRequest(amount, currency);
            
            Boolean result = accountServiceClient.validateFunds(accountId, validationRequest).getBody();
            return result != null && result;
        } catch (Exception e) {
            log.error("Error validating funds: {}", e.getMessage(), e);
            return false;
        }
    }
    
    private DonationResponse mapToResponse(Donation donation) {
        return new DonationResponse(
                donation.getId(),
                donation.getDonorId(),
                donation.getCampaignId(),
                donation.getAmount(),
                donation.getCurrency(),
                donation.getStatus(),
                donation.getTransactionId(),
                donation.getCreatedAt()
        );
    }
}
