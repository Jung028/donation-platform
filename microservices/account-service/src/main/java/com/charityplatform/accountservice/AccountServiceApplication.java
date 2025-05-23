package com.charityplatform.accountservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Main application class for the Account Service microservice.
 * 
 * This service manages user accounts, authentication, and financial transactions
 * for the Charity Platform system. It provides RESTful APIs for account management
 * and integrates with other microservices through Eureka service discovery.
 * 
 * @author Adam Jung
 * @version 1.0.0
 * @since 2025-05-21
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableWebSecurity
@EnableTransactionManagement
public class AccountServiceApplication implements WebMvcConfigurer {
    /**
     * Main entry point for the Account Service application.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }


}
