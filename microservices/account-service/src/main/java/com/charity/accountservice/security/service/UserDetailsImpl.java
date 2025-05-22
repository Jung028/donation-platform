package com.charity.accountservice.security.service;

import com.charity.accountservice.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of Spring Security's UserDetails interface that represents a user's security details.
 * This class is used to store user information along with their authorities/roles for authentication and authorization.
 *
 * @author Your Name
 * @version 1.0
 */
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier for the user.
     */
    private final Long id;

    /**
     * Username for authentication.
     */
    private final String username;

    /**
     * User's email address.
     */
    private final String email;

    /**
     * User's password (not exposed in JSON responses due to @JsonIgnore).
     */
    @JsonIgnore
    private final String password;

    /**
     * Collection of authorities/roles granted to the user.
     */
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * Constructor for creating a UserDetailsImpl instance.
     *
     * @param id           User's unique identifier
     * @param username     Username for authentication
     * @param email        User's email address
     * @param password     User's password
     * @param authorities  Collection of authorities/roles for the user
     */
    public UserDetailsImpl(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Factory method to create a UserDetailsImpl instance from a User entity.
     *
     * @param user The User entity to convert
     * @return A UserDetailsImpl instance representing the user
     */
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    @Override
    /**
     * Returns the collection of authorities granted to the user.
     *
     * @return Collection of GrantedAuthority objects
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Gets the user's unique identifier.
     *
     * @return User's ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the user's email address.
     *
     * @return User's email
     */
    public String getEmail() {
        return email;
    }

    @Override
    /**
     * Gets the user's password.
     *
     * @return User's password
     */
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    /**
     * Gets the user's username.
     *
     * @return User's username
     */
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    /**
     * Checks if the account has expired.
     *
     * @return true if account is not expired
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    /**
     * Checks if the account is locked.
     *
     * @return true if account is not locked
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    /**
     * Checks if the credentials have expired.
     *
     * @return true if credentials are not expired
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    /**
     * Checks if the account is enabled.
     *
     * @return true if account is enabled
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    /**
     * Checks if this UserDetailsImpl instance equals another object.
     * Equality is determined by comparing user IDs.
     *
     * @param o Object to compare with
     * @return true if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
