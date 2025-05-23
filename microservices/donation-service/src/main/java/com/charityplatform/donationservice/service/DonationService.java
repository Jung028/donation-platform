package com.charityplatform.donationservice.service;

import com.charityplatform.donationservice.model.Donation;
import com.charityplatform.donationservice.model.DonationStatus;
import com.charityplatform.donationservice.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    public Donation createDonation(Donation donation) {
        donation.setDonationDate(LocalDateTime.now());
        donation.setStatus(DonationStatus.PENDING);
        return donationRepository.save(donation);
    }

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public List<Donation> getDonationsByDonor(String donorName) {
        return donationRepository.findByDonorName(donorName);
    }

    public List<Donation> getDonationsByCharity(String charityName) {
        return donationRepository.findByCharityName(charityName);
    }

    public Donation updateDonationStatus(Long id, DonationStatus status) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found"));
        donation.setStatus(status);
        return donationRepository.save(donation);
    }
}
