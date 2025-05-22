package com.charity.accountservice.repository;

import com.charity.accountservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Account entities.
 * Extends JpaRepository to provide CRUD operations and additional JPA functionality.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Finds an account by its unique account number.
     *
     * @param accountNumber The unique account number to search for
     * @return Optional containing the account if found, empty Optional otherwise
     */
    Optional<Account> findByAccountNumber(String accountNumber);

    /**
     * Retrieves an account associated with a specific user ID.
     * This method is useful for getting account details for a particular user.
     *
     * @param userId The ID of the user whose account should be retrieved
     * @return Optional containing the user's account if found, empty Optional otherwise
     */
    Optional<Account> findByUserId(Long userId);
}