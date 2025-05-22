package com.charity.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main application class for the Notification Service.
 * This microservice is responsible for sending notifications to users via different channels
 * such as email, SMS, and in-app notifications. It listens for events from other microservices
 * and sends appropriate notifications based on those events.
 * 
 * <p>This service uses Spring Boot for the application framework and Spring Cloud OpenFeign
 * for declarative REST client integration with other microservices.</p>
 * 
 * @author Charity Platform Team
 */
@SpringBootApplication
@EnableFeignClients
public class NotificationServiceApplication {

    /**
     * The main method that starts the Notification Service application.
     * Bootstraps the Spring application context and starts the embedded web server.
     * 
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}
