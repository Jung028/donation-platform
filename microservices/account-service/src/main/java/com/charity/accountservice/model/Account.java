package com.charity.accountservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an account entity in the system.
 * This entity is mapped to the "accounts" table in the database.
 */
@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    /**
     * The unique identifier for the account.
     * This is the primary key and is auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The unique account number for the account.
     * This field is mandatory and must be unique.
     */
    @Column(unique = true, nullable = false)
    private String accountNumber;

    /**
     * The user associated with the account.
     * This is a one-to-one relationship and is mandatory.
     */
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The type of the account (e.g., SAVINGS, CHECKING).
     * Stored as a string in the database.
     */
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    /**
     * The current balance of the account.
     * Precision is set to 19 digits with 4 decimal places.
     * Defaults to BigDecimal.ZERO.
     */
    @Column(precision = 19, scale = 4)
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * The status of the account (e.g., ACTIVE, INACTIVE).
     * Stored as a string in the database.
     */
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    /**
     * The list of transactions associated with the account.
     * This is a one-to-many relationship with cascade and orphan removal enabled.
     */
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;

    /**
     * The timestamp when the account was created.
     * Automatically populated by Hibernate.
     */
    @CreationTimestamp
    private LocalDateTime createdAt;

    /**
     * The timestamp when the account was last updated.
     * Automatically populated by Hibernate.
     */
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}