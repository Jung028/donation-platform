package com.charityplatform.donationservice.service;

import com.charityplatform.donationservice.model.Donation;
import com.charityplatform.donationservice.model.DonationStatus;
import com.charityplatform.donationservice.model.DonationType;
import com.charityplatform.donationservice.repository.DonationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DonationServiceTest {

    @Mock
    private DonationRepository donationRepository;

    @InjectMocks
    private DonationService donationService;

    private Donation testDonation;

    @BeforeEach
    void setUp() {
        testDonation = new Donation();
        testDonation.setDonorName("Test Donor");
        testDonation.setAmount(100.0);
        testDonation.setDonationType(DonationType.ONE_TIME);
        testDonation.setCharityName("Test Charity");
        testDonation.setMessage("Test donation");
    }

    @Test
    void testCreateDonation() {
        when(donationRepository.save(any(Donation.class))).thenReturn(testDonation);

        Donation createdDonation = donationService.createDonation(testDonation);

        assertNotNull(createdDonation);
        assertNotNull(createdDonation.getDonationDate());
        assertEquals(DonationStatus.PENDING, createdDonation.getStatus());
    }

    @Test
    void testGetAllDonations() {
        List<Donation> donations = new ArrayList<>();
        donations.add(testDonation);
        when(donationRepository.findAll()).thenReturn(donations);

        List<Donation> result = donationService.getAllDonations();

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateDonationStatus() {
        testDonation.setId(1L);
        when(donationRepository.findById(1L)).thenReturn(Optional.of(testDonation));
        when(donationRepository.save(any(Donation.class))).thenReturn(testDonation);

        Donation updatedDonation = donationService.updateDonationStatus(1L, DonationStatus.COMPLETED);

        assertNotNull(updatedDonation);
        assertEquals(DonationStatus.COMPLETED, updatedDonation.getStatus());
    }
}
