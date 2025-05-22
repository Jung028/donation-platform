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

/**
 * REST controller responsible for handling all donation-related HTTP endpoints.
 * This controller exposes APIs for creating and retrieving donations, including filtering
 * by donor or campaign. It maps to the "/api/donations" base path and delegates
 * business logic to the DonationService.
 *
 * @author Charity Platform Team
 * @see com.charity.donationservice.service.DonationService
 * @see com.charity.donationservice.dto.DonationRequest
 * @see com.charity.donationservice.dto.DonationResponse
 */
@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
@Slf4j
public class DonationController {
    
    /**
     * The donation service responsible for processing donation requests
     * and retrieving donation information.
     */
    private final DonationService donationService;
    
    /**
     * Creates a new donation based on the provided request data.
     * This endpoint processes the donation through validation, creation, and payment processing.
     * If successful, returns the created donation with a CREATED (201) status.
     * 
     * @param request The donation request containing donor ID, campaign ID, amount, and currency
     *                Must be valid according to the validation constraints in DonationRequest
     * @return ResponseEntity containing the created donation with HTTP 201 status
     * @throws com.charity.donationservice.exception.InsufficientFundsException if the donor has insufficient funds
     * @throws org.springframework.web.bind.MethodArgumentNotValidException if the request fails validation
     */
    @PostMapping
    public ResponseEntity<DonationResponse> createDonation(@Valid @RequestBody DonationRequest request) {
        log.info("Received donation request: {}", request);
        DonationResponse response = donationService.processDonation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    /**
     * Retrieves a specific donation by its unique identifier.
     * Returns the donation details if found.
     * 
     * @param id The unique identifier of the donation to retrieve
     * @return ResponseEntity containing the donation details with HTTP 200 status
     * @throws com.charity.donationservice.exception.ResourceNotFoundException if no donation exists with the given ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<DonationResponse> getDonation(@PathVariable Long id) {
        log.info("Fetching donation with ID: {}", id);
        DonationResponse response = donationService.getDonationById(id);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Retrieves all donations made by a specific donor.
     * Returns a list of donations associated with the given donor ID.
     * 
     * @param donorId The unique identifier of the donor whose donations should be retrieved
     * @return ResponseEntity containing a list of donations with HTTP 200 status
     *         Returns an empty list if the donor has no donations
     */
    @GetMapping("/donor/{donorId}")
    public ResponseEntity<List<DonationResponse>> getDonationsByDonor(@PathVariable Long donorId) {
        log.info("Fetching donations for donor ID: {}", donorId);
        List<DonationResponse> donations = donationService.getDonationsByDonor(donorId);
        return ResponseEntity.ok(donations);
    }
    
    /**
     * Retrieves all donations made to a specific campaign.
     * Returns a list of donations associated with the given campaign ID.
     * This endpoint is particularly useful for campaign statistics and reporting.
     * 
     * @param campaignId The unique identifier of the campaign whose donations should be retrieved
     * @return ResponseEntity containing a list of donations with HTTP 200 status
     *         Returns an empty list if the campaign has no donations
     */
    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<List<DonationResponse>> getDonationsByCampaign(@PathVariable Long campaignId) {
        log.info("Fetching donations for campaign ID: {}", campaignId);
        List<DonationResponse> donations = donationService.getDonationsByCampaign(campaignId);
        return ResponseEntity.ok(donations);
    }
}
