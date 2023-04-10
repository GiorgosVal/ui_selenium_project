package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO object that holds the details for a simple flight
 */
@Data
@Builder
public class SimpleFlight {

    private String fromInput;
    private String fromAirport;
    private String toInput;
    private String toAirport;
    private LocalDateTime departureDate;
}
