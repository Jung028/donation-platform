package com.charityplatform.donationservice.controller;

import com.charityplatform.donationservice.DonationServiceApplication;
import com.charityplatform.donationservice.model.Donation;
import com.charityplatform.donationservice.model.DonationStatus;
import com.charityplatform.donationservice.model.DonationType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = DonationServiceApplication.class)
@AutoConfigureMockMvc
public class DonationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    void testCreateDonation() throws Exception {
        String json = objectMapper.writeValueAsString(testDonation);

        mockMvc.perform(post("/api/donations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("donorName").value("Test Donor"))
                .andExpect(jsonPath("status").value("PENDING"));
    }

    @Test
    void testGetAllDonations() throws Exception {
        mockMvc.perform(get("/api/donations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateDonationStatus() throws Exception {
        mockMvc.perform(put("/api/donations/1/status?status=COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status").value("COMPLETED"));
    }
}
