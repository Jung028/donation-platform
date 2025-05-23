package com.charityplatform.accountservice.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Utility class for generating unique account numbers.
 */
public class AccountNumberGenerator {
    private static final Random RANDOM = new Random();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * Generates a unique account number in the format: YYYYMMDD-XXXXXX
     * where YYYYMMDD is the current date and XXXXXX is a random 6-digit number
     */
    public static String generateAccountNumber() {
        String datePart = LocalDateTime.now().format(DATE_FORMATTER);
        String randomPart = String.format("%06d", RANDOM.nextInt(1_000_000));
        return datePart + "-" + randomPart;
    }
}
