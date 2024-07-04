package com.GOTEL.SchoolCar.Utils;

import java.util.List;
import java.util.Random;

public class IDGenerator {
    private static final int INITIAL_ID_LENGTH = 8;
    private static final int MAX_ATTEMPTS = 1000;
    private static final Random random = new Random();
    private static int currentIdLength = INITIAL_ID_LENGTH;

    public String generateID(List<String> haven, String type) {
        String userId;
        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            userId = generateRandomId(currentIdLength);
            if (!haven.contains(userId)) {
                return type + userId;
            } else if (attempts >= (int) (Math.pow(36, currentIdLength) * 0.9)) {
                currentIdLength++;
                attempts = 0;
            }
            attempts++;
        }

        throw new RuntimeException("Unable to generate a unique user ID after " + MAX_ATTEMPTS + " attempts.");
    }

    private static String generateRandomId(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(36);
            if (number < 10) {
                stringBuilder.append(number);
            } else {
                stringBuilder.append((char) (number + (int) 'A' - 10));
            }
        }
        return stringBuilder.toString();
    }
}
