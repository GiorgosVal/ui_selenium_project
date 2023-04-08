package com.example.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private static final Pattern numberPattern = Pattern.compile("\\d+");

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
        String result = null;
        Matcher matcher = numberPattern.matcher(string);
        if (matcher.find()) {
            result = matcher.group();
        }
        return result;
    }
}
