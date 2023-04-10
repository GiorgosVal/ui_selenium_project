package com.example.demo.validation.actual;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalTime;

@Builder
@Data
public class ActualFlight {

    private String airlineName;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Duration flightDuration;
    private int flightStops;
}
