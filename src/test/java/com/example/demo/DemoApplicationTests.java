package com.example.demo;

import com.example.demo.base.BaseTest;
import com.example.demo.enums.CabinClass;
import com.example.demo.enums.FlightType;
import com.example.demo.enums.PassengerType;
import com.example.demo.dto.FlightDetails;
import com.example.demo.dto.SimpleFlight;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.util.List;

public class DemoApplicationTests extends BaseTest {

    FlightDetails flightDetails;

    @BeforeClass
    void loadData() {
        flightDetails = FlightDetails.builder()
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

        flightDetails = FlightDetails.builder()
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
                                .build(),
                        SimpleFlight.builder()
                                .fromInput("Cairo")
                                .fromAirport("Cairo (All airports), Egypt")
                                .toInput("Tokyo")
                                .toAirport("Tokyo (All airports), Japan")
                                .departureDate(LocalDateTime.now().plusDays(60))
                                .build()
                ))
                .passengerTypeList(List.of(PassengerType.ADULTS, PassengerType.CHILDREN))
                .cabinClass(CabinClass.FIRST)
                .build();
    }

    @Test
    void contextLoads() {
        homePageBO.navigateTo("https://www.flightnetwork.com/");
        homePageBO.searchFlight(flightDetails);
        System.out.println();
    }


}
