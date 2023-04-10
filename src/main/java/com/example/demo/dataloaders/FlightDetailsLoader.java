package com.example.demo.dataloaders;

import com.example.demo.dto.FlightDetails;
import com.example.demo.dto.SimpleFlight;
import com.example.demo.enums.CabinClass;
import com.example.demo.enums.FlightType;
import com.example.demo.enums.PassengerType;

import java.time.LocalDateTime;
import java.util.List;

public class FlightDetailsLoader {

    private FlightDetailsLoader() {
    }

    /**
     * Loads a one way flight with predefined fields
     *
     * @return - the DTO holding the information for the flight
     */
    public static FlightDetails loadOneWayFlight() {
        return loadOneWayFlight(SimpleFlight.builder()
                .fromInput("Athens")
                .fromAirport("Athens, Greece")
                .toInput("Istanbul")
                .toAirport("Istanbul (All airports), Turkey")
                .departureDate(LocalDateTime.now().plusDays(40))
                .build());
    }

    /**
     * Loads a one way flight with predefined fields for return date, passengers, cabin class
     *
     * @return - the DTO holding the information for the flight
     */
    public static FlightDetails loadOneWayFlight(SimpleFlight simpleFlight) {
        return loadFlight(FlightType.ONE_WAY, List.of(simpleFlight), LocalDateTime.now().plusDays(90), List.of(PassengerType.ADULTS, PassengerType.CHILDREN), CabinClass.FIRST);
    }

    /**
     * Loads a multi city flight with predefined fields
     *
     * @return - the DTO holding the information for the flight
     */
    public static FlightDetails loadMultiCityFlight() {
        return loadMultiCityFlight(List.of(
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
        ));
    }

    /**
     * Loads a multi city flight with predefined fields for return date, passengers, cabin class
     *
     * @return - the DTO holding the information for the flight
     */
    public static FlightDetails loadMultiCityFlight(List<SimpleFlight> simpleFlights) {
        return loadFlight(FlightType.MULTI_CITY, simpleFlights, null, List.of(PassengerType.ADULTS, PassengerType.CHILDREN), CabinClass.FIRST);
    }

    /**
     * Loads a flight
     *
     * @return - the DTO holding the information for the flight
     */
    private static FlightDetails loadFlight(FlightType flightType, List<SimpleFlight> simpleFlights, LocalDateTime returnDate, List<PassengerType> passengerTypes, CabinClass cabinClass) {
        return FlightDetails.builder()
                .flightType(flightType)
                .simpleFlights(simpleFlights)
                .returnDate(returnDate)
                .passengerTypeList(passengerTypes)
                .cabinClass(cabinClass)
                .build();
    }
}
