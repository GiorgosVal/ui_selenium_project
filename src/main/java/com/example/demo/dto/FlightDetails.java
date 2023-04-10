package com.example.demo.dto;

import com.example.demo.enums.CabinClass;
import com.example.demo.enums.FlightType;
import com.example.demo.enums.PassengerType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO object that holds the details for a flight
 */
@Data
@Builder
public class FlightDetails {

    private FlightType flightType;
    private List<SimpleFlight> simpleFlights;
    private LocalDateTime returnDate;
    private List<PassengerType> passengerTypeList;
    private CabinClass cabinClass;
    private boolean isNonStopFlight;
}
