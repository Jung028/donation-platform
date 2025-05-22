package com.charity.donationservice.repository;

import com.charity.donationservice.model.Donation;
import com.charity.donationservice.model.DonationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    
    List<Donation> findByDonorId(Long donorId);
    
    List<Donation> findByCampaignId(Long campaignId);
    
    List<Donation> findByStatus(DonationStatus status);
}
