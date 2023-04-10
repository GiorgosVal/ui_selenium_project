package com.example.demo.dataloaders;

import com.example.demo.dto.FlightDetails;
import com.example.demo.dto.SimpleFlight;
import com.example.demo.enums.CabinClass;
import com.example.demo.enums.FlightType;
import com.example.demo.enums.PassengerType;

import java.time.LocalDateTime;
import java.util.List;

public class FlightDetailsLoader {

    public static FlightDetails loadOneWayFlight() {
        return FlightDetails.builder()
                .flightType(FlightType.ONE_WAY)
                .simpleFlights(List.of(SimpleFlight.builder()
                        .fromInput("Athens")
                        .fromAirport("Athens, Greece")
                        .toInput("Istanbul")
                        .toAirport("Istanbul (All airports), Turkey")
                        .departureDate(LocalDateTime.now().plusDays(40))
                        .build()))
                .returnDate(LocalDateTime.now().plusDays(90))
                .passengerTypeList(List.of(PassengerType.ADULTS, PassengerType.CHILDREN))
                .cabinClass(CabinClass.FIRST)
                .build();
    }

    public static FlightDetails loadMultiCityFlight() {
        return FlightDetails.builder()
                .flightType(FlightType.MULTI_CITY)
                .simpleFlights(List.of(
                        SimpleFlight.builder()
                                .fromInput("Athens")
                                .fromAirport("Athens, Greece")
                                .toInput("Istanbul")
                                .toAirport("Istanbul (All airports), Turkey")
                                .departureDate(LocalDateTime.now().plusDays(40))
                                .build(),
                        SimpleFlight.builder()
                                .fromInput("Berlin")
                                .fromAirport("Berlin (All airports), Germany")
                                .toInput("Amsterdam")
                                .toAirport("Amsterdam, Netherlands")
                                .departureDate(LocalDateTime.now().plusDays(50))
                                .build()
                ))
                .passengerTypeList(List.of(PassengerType.ADULTS, PassengerType.CHILDREN))
                .cabinClass(CabinClass.FIRST)
                .build();
    }
}
