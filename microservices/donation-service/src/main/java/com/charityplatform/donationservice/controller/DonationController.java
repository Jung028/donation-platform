package com.charityplatform.donationservice.controller;

import com.charityplatform.donationservice.model.Donation;
import com.charityplatform.donationservice.model.DonationStatus;
import com.charityplatform.donationservice.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @PostMapping
    public ResponseEntity<Donation> createDonation(@RequestBody Donation donation) {
        return ResponseEntity.ok(donationService.createDonation(donation));
    }

    @GetMapping
    public ResponseEntity<List<Donation>> getAllDonations() {
        return ResponseEntity.ok(donationService.getAllDonations());
    }

    @GetMapping("/donor/{donorName}")
    public ResponseEntity<List<Donation>> getDonationsByDonor(@PathVariable String donorName) {
        return ResponseEntity.ok(donationService.getDonationsByDonor(donorName));
    }

    @GetMapping("/charity/{charityName}")
    public ResponseEntity<List<Donation>> getDonationsByCharity(@PathVariable String charityName) {
        return ResponseEntity.ok(donationService.getDonationsByCharity(charityName));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Donation> updateDonationStatus(
            @PathVariable Long id,
            @RequestParam DonationStatus status) {
        return ResponseEntity.ok(donationService.updateDonationStatus(id, status));
    }
}
