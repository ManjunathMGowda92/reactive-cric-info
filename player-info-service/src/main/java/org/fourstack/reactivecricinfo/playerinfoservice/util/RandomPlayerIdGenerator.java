package org.fourstack.reactivecricinfo.playerinfoservice.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

/**
 * Class which is responsible for generating the Random and unique ID.
 *
 * @author manjunath
 */
public class RandomPlayerIdGenerator {

    private static final String[] alpha = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * Method which generates Unique and Random ID for Player using the
     * player firstname.
     *
     * @param playerFirstName firstname of Player.
     * @return Unique and Random String ID.
     */
    public static String generateRandomId(String playerFirstName) {
        LocalDateTime current = LocalDateTime.now(ZoneId.of("UTC"));
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(getPlayerFirstName(playerFirstName))
                .append(getYear(current))
                .append("-")
                .append(getMonth(current))
                .append(dynamicAlpha(new Random().nextInt(2)))
                .append(getDay(current))
                .append("-")
                .append(getHour(current))
                .append(dynamicAlpha(new Random().nextInt(1)))
                .append(getMinute(current))
                .append("-")
                .append(getSecond(current))
                .append(dynamicAlpha(new Random().nextInt(3)))
                .append("-")
                .append(getNanoSecond(current));
        return strBuilder.toString();
    }

    /**
     * Method extracts the first 3 alphabets of String value. If String value
     * is null, then dynamic value will be generated and uppercase substring
     * will be returned.
     *
     * @param playerFirstName First name of Player.
     * @return UpperCase String from firstname.
     */
    private static String getPlayerFirstName(String playerFirstName) {
        String firstName = "";
        if (playerFirstName != null) {
            String firstThreeLetters = (playerFirstName.length() > 3) ? playerFirstName.substring(0, 3)
                    : playerFirstName;
            firstName = firstName.concat(firstThreeLetters);
        } else {
            firstName = dynamicAlpha(new Random().nextInt(3));
        }
        return firstName.toUpperCase();
    }

    /**
     * Method to generate the String with dynamic alphabets with provided length.
     *
     * @param length Length of string to be generated.
     * @return String value with provided length.
     */
    private static String dynamicAlpha(int length) {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i <= length; i++) {
            strBuilder.append(alpha[new Random().nextInt(26)]);
        }
        return strBuilder.toString();
    }

    private static int getYear(LocalDateTime time) {
        return time.getYear();
    }

    private static int getMonth(LocalDateTime time) {
        return time.getMonthValue();
    }

    private static int getDay(LocalDateTime time) {
        return time.getDayOfMonth();
    }

    private static int getHour(LocalDateTime time) {
        return time.getHour();
    }

    private static int getMinute(LocalDateTime time) {
        return time.getMinute();
    }

    private static int getSecond(LocalDateTime time) {
        return time.getSecond();
    }

    private static int getNanoSecond(LocalDateTime time) {
        return time.getNano();
    }
}
