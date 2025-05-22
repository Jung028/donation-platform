package com.charity.donationservice.controller;

import com.charity.donationservice.dto.DonationRequest;
import com.charity.donationservice.dto.DonationResponse;
import com.charity.donationservice.service.DonationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
@Slf4j
public class DonationController {
    
    private final DonationService donationService;
    
    @PostMapping
    public ResponseEntity<DonationResponse> createDonation(@Valid @RequestBody DonationRequest request) {
        log.info("Received donation request: {}", request);
        DonationResponse response = donationService.processDonation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DonationResponse> getDonation(@PathVariable Long id) {
        log.info("Fetching donation with ID: {}", id);
        DonationResponse response = donationService.getDonationById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/donor/{donorId}")
    public ResponseEntity<List<DonationResponse>> getDonationsByDonor(@PathVariable Long donorId) {
        log.info("Fetching donations for donor ID: {}", donorId);
        List<DonationResponse> donations = donationService.getDonationsByDonor(donorId);
        return ResponseEntity.ok(donations);
    }
    
    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<List<DonationResponse>> getDonationsByCampaign(@PathVariable Long campaignId) {
        log.info("Fetching donations for campaign ID: {}", campaignId);
        List<DonationResponse> donations = donationService.getDonationsByCampaign(campaignId);
        return ResponseEntity.ok(donations);
    }
}
