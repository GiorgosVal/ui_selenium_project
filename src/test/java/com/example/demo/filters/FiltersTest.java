package com.example.demo.filters;

import com.beust.ah.A;
import com.example.demo.base.BaseTest;
import com.example.demo.dataloaders.FlightDetailsLoader;
import com.example.demo.dto.FlightDetails;
import com.example.demo.validation.Assertor;
import com.example.demo.validation.actual.Trip;
import com.example.demo.validation.expected.Flight;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.List;

public class FiltersTest extends BaseTest {

    FlightDetails flightDetails;

    @BeforeClass
    void loadData() {
        String myDateString = "13:24:40";
        LocalTime localTime = LocalTime.parse(myDateString, DateTimeFormatter.ofPattern("HH:mm:ss"));
        int hour = localTime.get(ChronoField.CLOCK_HOUR_OF_DAY);
        int minute = localTime.get(ChronoField.MINUTE_OF_HOUR);
        int second = localTime.get(ChronoField.SECOND_OF_MINUTE);

        flightDetails = FlightDetailsLoader.loadMultiCityFlight();
    }

    @BeforeClass(dependsOnMethods = "loadData")
    void init() {
        homePageBO.searchFlight(flightDetails);
    }

    @Test
    void contextLoads() {
        List<Trip> actualTrips = flightResultsBO.getActualTrips();

        Flight expectedFlight = Flight.builder()
                .airlineNames(List.of("hi"))
                .arrivalTimeMax(LocalTime.of(13, 23))
                .arrivalTimeMin(LocalTime.of(13, 23))
                .departureTimeMax(LocalTime.of(13, 23))
                .departureTimeMin(LocalTime.of(13, 23))
                .durationMax(Duration.ofHours(1))
                .flightStopsMax(2)
                .build();

        com.example.demo.validation.expected.Trip expectedTrip = com.example.demo.validation.expected.Trip.builder()
                .priceMax(123.34f)
                .priceMin(123.45f)
                .expectedFlightList(List.of(expectedFlight, expectedFlight, expectedFlight))
                .build();

        Assertor assertor = new Assertor();
        assertor.softAssertTrips(expectedTrip, actualTrips).softAssertAll();


        System.out.println();

//        Map<Integer, List<WebElement>> flightsPerType = flightResultsPO.tripsToFlightTypesMap(trips);
//
//        trips.forEach(trip -> {
//            System.out.println("TRIP standard price: " + flightResultsPO.getTripStandardPrice(trip));
//            System.out.println("TRIP flexible price: " + flightResultsPO.getTripFlexiblePrice(trip));
//        });
//        flightsPerType.forEach((flightType, flights) -> {
//            System.out.println("FLIGHT " + flightType);
//            flights.forEach(flight -> {
//                System.out.println("Airline: " + flightResultsPO.getFlightAirlineName(flight));
//                System.out.println("Departure: " + flightResultsPO.getFlightDepartureTime(flight));
//                System.out.println("Arrival: " + flightResultsPO.getFlightArrivalTime(flight));
//                System.out.println("Duration: " + flightResultsPO.getFlightDurationTime(flight));
//                System.out.println("Stops: " + flightResultsPO.getFlightStops(flight));
//                System.out.println();
//            });
//        });
//        System.out.println(trips);


    }


}
