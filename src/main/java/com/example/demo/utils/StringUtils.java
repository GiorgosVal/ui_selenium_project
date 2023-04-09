package com.example.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private static final Pattern numberPattern = Pattern.compile("\\d+");
    private static final Pattern moneyPattern = Pattern.compile("\\d*,*\\d{3}\\.\\d{2}"); // like CA$1,966.75 or CA$456.75
    private static final Pattern durationPattern = Pattern.compile("\\d+h\\s*\\d+min"); // like 14h 35min

    /**
     * <p>
     * Extracts the first number found in a string. For example in the string:
     * <i>"Found 400 flights served by 10 airlines"</i> the method will return the number 400.
     * </p>
     * <p>
     * If none number is found, the method will return {@code null}
     * </p>
     *
     * @param string - The string to extract the number from
     * @return - The number, or null
     */
    public static String extractFirstNumberFromString(String string) {
        return extractFromStringUsingPattern(string, numberPattern);
    }

    /**
     * <p>
     * Extracts the price from a string. For example in the string:
     * <i>"CA$1,966.75"</i> the method will return the number 1,966.75.
     * </p>
     * <p>
     * If none price is found, the method will return {@code null}
     * </p>
     *
     * @param string - The string to extract the price from
     * @return - The price, or null
     */
    public static String extractPriceFromString(String string) {
        return extractFromStringUsingPattern(string, moneyPattern);
    }

    /**
     * Extracts the first occurrence that the pattern will find inside the string
     *
     * @param string  - The string to extract the occurence from
     * @param pattern - The pattern to use
     * @return - The occurence found, or null
     */
    public static String extractFromStringUsingPattern(String string, Pattern pattern) {
        String result = null;
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            result = matcher.group();
        }
        return result;
    }
}
