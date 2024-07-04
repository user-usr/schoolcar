package com.GOTEL.SchoolCar.Utils;

import java.util.Map;
import java.util.Random;

public class IToken {
    private static final Random random = new Random();
    private static final int LOCAL_LENGTH = 10;
    private static final int INDEX = 5;

    public String createToken(String id, Map<String, String> tokenIDMap) {
        while (true) {
            StringBuilder token = new StringBuilder(generateLocalString());
            int firstLength = random.nextInt(id.length() - 1);
            int secondLength = id.length() - firstLength;
            String firstPart = id.substring(0, firstLength);
            String secondPart = id.substring(firstLength);
            int firstIndex = random.nextInt(INDEX);
            int secondIndex = random.nextInt(INDEX) + INDEX;

            token.insert(secondIndex, secondPart)
                    .insert(firstIndex, firstPart)
                    .append("-")
                    .append(firstIndex)
                    .append("-")
                    .append(firstLength)
                    .append("-")
                    .append(secondIndex)
                    .append("-")
                    .append(secondLength);

            if (!tokenIDMap.containsKey(token.toString())) {
                return token.toString();
            }
        }
    }


    public String decodeToken(String token) {
        StringBuilder id = new StringBuilder();
        String[] split = token.split("-");
        String body = split[0];
        int firstIndex = Integer.parseInt(split[1]);
        int firstLength = Integer.parseInt(split[2]);
        int secondIndex = Integer.parseInt(split[3]) + firstLength;
        int secondLength = Integer.parseInt(split[4]);

        id.append(body, firstIndex, firstIndex + firstLength)
                .append(body, secondIndex, secondIndex + secondLength);

        return id.toString();
    }


    public boolean validate(String token, String id, Map<String, String> tokenIDMap) {
        if (tokenIDMap.containsKey(token)) {
            return tokenIDMap.get(token).equals(id);
        }
        return false;
    }

    private String generateLocalString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < LOCAL_LENGTH; i++) {
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
