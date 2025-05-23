package com.charityplatform.donationservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "donations")
@NoArgsConstructor
@AllArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String donorName;

    @Column(nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    private DonationType donationType;

    @Column(nullable = false)
    private String charityName;

    @Column(nullable = false)
    private LocalDateTime donationDate;

    private String message;

    @Enumerated(EnumType.STRING)
    private DonationStatus status;
}
