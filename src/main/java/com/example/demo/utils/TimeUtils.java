package com.example.demo.utils;

import java.time.Duration;
import java.util.regex.Pattern;

public class TimeUtils {

    /**
     * Transforms a string like 1h 30min to {@link Duration}
     *
     * @param duration - the duration as string
     * @return - the duration as {@link Duration}
     */
    public static Duration stringToDuration(String duration) {
        String hours = StringUtils.extractFromStringUsingPattern(duration, Pattern.compile("\\d+(?=h)"));   //TODO move this to util
        String minutes = StringUtils.extractFromStringUsingPattern(duration, Pattern.compile("\\d+(?=min)"));
        return Duration.ofHours(Long.parseLong(hours != null ? hours : "0")).plusMinutes(Long.parseLong(minutes != null ? minutes : "0"));
    }
}
