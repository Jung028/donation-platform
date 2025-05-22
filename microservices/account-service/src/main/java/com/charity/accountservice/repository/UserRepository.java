package com.charity.accountservice.repository;

import com.charity.accountservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entities in the database.
 * Provides methods for user-related database operations including CRUD and custom queries.
 * Extends JpaRepository to inherit standard database operations and pagination support.
 *
 * @see User
 * @see JpaRepository
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Retrieves a user by their unique username.
     * This method is primarily used for authentication and user lookup purposes.
     *
     * @param username The username to search for
     * @return Optional containing the user if found, empty Optional otherwise
     * @throws org.springframework.dao.DataAccessException if there's a database access error
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Retrieves a user by their email address.
     * Useful for password recovery and email verification features.
     *
     * @param email The email address to search for
     * @return Optional containing the user if found, empty Optional otherwise
     * @throws org.springframework.dao.DataAccessException if there's a database access error
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Checks if a username already exists in the system.
     * Used during user registration to ensure username uniqueness.
     *
     * @param username The username to check for existence
     * @return true if the username exists, false otherwise
     * @throws org.springframework.dao.DataAccessException if there's a database access error
     */
    Boolean existsByUsername(String username);
    
    /**
     * Checks if an email address is already registered in the system.
     * Used during user registration to prevent duplicate email addresses.
     *
     * @param email The email address to check for existence
     * @return true if the email exists, false otherwise
     * @throws org.springframework.dao.DataAccessException if there's a database access error
     */
    Boolean existsByEmail(String email);
}