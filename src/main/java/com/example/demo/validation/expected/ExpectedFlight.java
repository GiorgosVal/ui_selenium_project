package com.example.demo.validation.expected;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

/**
 * Object holding information of an expected flight.
 * <p>
 * The fields that form an expected flight are:
 * <ul>
 *     <li>the airline name</li>
 *     <li>a range of departure time</li>
 *     <li>a range of arrival time</li>
 *     <li>the maximum duration of the flight</li>
 *     <li>the maximum flight stops</li>
 * </ul>
 */
@Builder
@Data
public class ExpectedFlight {

    private List<String> airlineNames;
    private LocalTime departureTimeMin;
    private LocalTime departureTimeMax;
    private LocalTime arrivalTimeMin;
    private LocalTime arrivalTimeMax;
    private Duration durationMax;
    private int flightStopsMax;
}
