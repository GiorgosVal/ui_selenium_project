package com.example.demo.validation.actual;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Object holding information of the actual flight found.
 * <p>
 * The fields retrieved are
 * <ul>
 *     <li>the airline name</li>
 *     <li>the departure time</li>
 *     <li>the arrival time</li>
 *     <li>the flight duration</li>
 *     <li> the flight stops</li>
 */
@Builder
@Data
public class ActualFlight {

    private String airlineName;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Duration flightDuration;
    private int flightStops;
}
