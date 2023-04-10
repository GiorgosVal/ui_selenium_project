package com.example.demo.utils;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class TimeUtils {

    private TimeUtils() {
    }

    private static final String HOURS_MINUTES_FORMAT = "HH:mm";

    /**
     * Transforms a string like 1h 30min to {@link Duration}
     *
     * @param duration - the duration as string
     * @return - the duration as {@link Duration}
     */
    public static Duration stringToDuration(String duration) {
        String hours = StringUtils.extractFromStringUsingPattern(duration, Pattern.compile("\\d+(?=h)"));
        String minutes = StringUtils.extractFromStringUsingPattern(duration, Pattern.compile("\\d+(?=min)"));
        return Duration.ofHours(Long.parseLong(hours != null ? hours : "0")).plusMinutes(Long.parseLong(minutes != null ? minutes : "0"));
    }

    /**
     * Reads a string like 23:52 and returns the {@link LocalTime}
     *
     * @param hoursMinutes - a string in the format {@link #HOURS_MINUTES_FORMAT}
     * @return - a {@link LocalTime}
     */
    public static LocalTime hoursMinutesToLocalTime(String hoursMinutes) {
        return LocalTime.parse(hoursMinutes, DateTimeFormatter.ofPattern(HOURS_MINUTES_FORMAT));
    }
}
