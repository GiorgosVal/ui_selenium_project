package com.example.demo.validation.expected;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

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
