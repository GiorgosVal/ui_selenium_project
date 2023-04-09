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
        flightDetails = FlightDetailsLoader.loadMultiCityFlight();
    }

    @BeforeClass(dependsOnMethods = "loadData")
    void init() {
        homePageBO.searchFlight(flightDetails);
    }

    @Test
    void contextLoads() {

        flightResultsBO.openFilters();
        System.out.println("ALL AIRLINES");
        filtersPO.getAllAvailableAirlines().stream().forEach(System.out::println);
        System.out.println();
        filtersPO.clickCheckBoxWithIndex(0);
        System.out.println("SELECTED AIRLINES");
        filtersPO.getAllSelectedAirlines().stream().forEach(System.out::println);
        filtersPO.clickNonStopFlightsFilter();
        filtersPO.clickMaxOneStopFlightsFilter();
        filtersPO.clickAllFlightsFilter();
        filtersPO.clickClearAllAirlinesButton();
        filtersPO.clickSelectAllAirlinesButton();
        filtersPO.clickArrivalRadioButton(0);
        filtersPO.clickDepartureRadioButton(0);
        filtersPO.clickArrivalRadioButton(1);
        filtersPO.clickDepartureRadioButton(1);
        filtersPO.clickArrivalRadioButton(2);
        filtersPO.clickDepartureRadioButton(2);
        System.out.println(filtersPO.getSliderDepartureArrivalLowestValue(0));
        System.out.println(filtersPO.getSliderDepartureArrivalHighestValue(0));
        filtersPO.slideDepartureArrivalLeftHandle(0, 30);
        filtersPO.slideDepartureArrivalLeftHandle(0, 30);
        filtersPO.slideDepartureArrivalLeftHandle(0, -30);
        filtersPO.slideDepartureArrivalRightHandle(0, -30);
        filtersPO.slideDepartureArrivalRightHandle(0, 30);
        System.out.println(filtersPO.getSliderDepartureArrivalLowestValue(0));
        System.out.println(filtersPO.getSliderDepartureArrivalHighestValue(0));

        System.out.println(filtersPO.getSliderDepartureArrivalLowestValue(1));
        System.out.println(filtersPO.getSliderDepartureArrivalHighestValue(1));
        filtersPO.slideDepartureArrivalLeftHandle(1, 30);
        filtersPO.slideDepartureArrivalLeftHandle(1, 30);
        filtersPO.slideDepartureArrivalLeftHandle(1, -30);
        filtersPO.slideDepartureArrivalRightHandle(1, -30);
        filtersPO.slideDepartureArrivalRightHandle(1, 30);
        System.out.println(filtersPO.getSliderDepartureArrivalLowestValue(1));
        System.out.println(filtersPO.getSliderDepartureArrivalHighestValue(1));

        System.out.println(filtersPO.getSliderDepartureArrivalLowestValue(2));
        System.out.println(filtersPO.getSliderDepartureArrivalHighestValue(2));
        filtersPO.slideDepartureArrivalLeftHandle(2, 30);
        filtersPO.slideDepartureArrivalLeftHandle(2, 30);
        filtersPO.slideDepartureArrivalLeftHandle(2, -30);
        filtersPO.slideDepartureArrivalRightHandle(2, -30);
        filtersPO.slideDepartureArrivalRightHandle(2, 30);
        System.out.println(filtersPO.getSliderDepartureArrivalLowestValue(2));
        System.out.println(filtersPO.getSliderDepartureArrivalHighestValue(2));

        System.out.println(filtersPO.getSliderPriceLowestValue());
        System.out.println(filtersPO.getSliderPriceHighestValue());
        filtersPO.slidePriceLeftHandle(60);
        filtersPO.slidePriceLeftHandle(-30);
        filtersPO.slidePriceRightHandle(-60);
        filtersPO.slidePriceRightHandle(30);
        System.out.println(filtersPO.getSliderPriceLowestValue());
        System.out.println(filtersPO.getSliderPriceHighestValue());

        System.out.println(filtersPO.getSliderTravelTimeValue());
        filtersPO.slideTravelTimeHandle(-60);
        filtersPO.slideTravelTimeHandle(30);
        System.out.println(filtersPO.getSliderTravelTimeValue());
        filtersPO.clickClearAllFiltersButton();
        filtersPO.slideTravelTimeHandle(-60);
        filtersPO.clickResetFiltersButton();
        filtersPO.slideTravelTimeHandle(-60);
        filtersPO.clickApplyFiltersButton();


//        List<Trip> actualTrips = flightResultsBO.getActualTrips();
//
//
//        Flight expectedFlight = Flight.builder()
//                .airlineNames(List.of("agean", "turkinsh"))
//                .arrivalTimeMax(LocalTime.of(13, 23))
//                .arrivalTimeMin(LocalTime.of(13, 23))
//                .departureTimeMax(LocalTime.of(13, 23))
//                .departureTimeMin(LocalTime.of(13, 23))
//                .durationMax(Duration.ofHours(1))
//                .flightStopsMax(2)
//                .build();
//
//        com.example.demo.validation.expected.Trip expectedTrip = com.example.demo.validation.expected.Trip.builder()
//                .priceMax(123.34f)
//                .priceMin(123.45f)
//                .expectedFlightList(List.of(expectedFlight, expectedFlight, expectedFlight))
//                .build();
//
//        Assertor assertor = new Assertor();
//        assertor.softAssertTrips(expectedTrip, actualTrips).softAssertAll();
//
//
//        System.out.println();

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
