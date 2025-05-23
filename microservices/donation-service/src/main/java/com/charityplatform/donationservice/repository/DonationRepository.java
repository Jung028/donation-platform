package com.charityplatform.donationservice.repository;

import com.charityplatform.donationservice.model.Donation;
import com.charityplatform.donationservice.model.DonationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByDonorName(String donorName);
    List<Donation> findByCharityName(String charityName);
    List<Donation> findByStatus(DonationStatus status);
}
