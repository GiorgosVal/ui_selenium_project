package com.example.demo.validation;

import com.example.demo.validation.expected.Flight;
import com.example.demo.validation.expected.Trip;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

public class Assertor {

    SoftAssert softAssert = new SoftAssert();

    public Assertor softAssertTrips(Trip expected, List<com.example.demo.validation.actual.Trip> actualTrips) {
        actualTrips.forEach(actualTrip -> sofAssertTrip(expected, actualTrip));
        return this;
    }

    public Assertor sofAssertTrip(Trip expected, com.example.demo.validation.actual.Trip actual) {
        softAssert.assertTrue(expected.getPriceMin() <= actual.getStandardPrice() && expected.getPriceMax() >= actual.getStandardPrice(),
                failMessage("Standard price", expected.getPriceMin(), expected.getPriceMax(), actual.getStandardPrice()));
        softAssert.assertEquals(expected.getExpectedFlightList().size(), actual.getActualFlightList().size(),
                failMessage("Flights list size", expected.getExpectedFlightList().size(), actual.getActualFlightList().size()));
        IntStream.range(0, expected.getExpectedFlightList().size()).forEach(index -> {
            softAssertFlight(expected.getExpectedFlightList().get(index), actual.getActualFlightList().get(index));
        });
        return this;
    }

    public Assertor softAssertFlight(Flight expected, com.example.demo.validation.actual.Flight actual) {
        softAssert.assertTrue(expected.getAirlineNames().contains(actual.getAirlineName()),
                failMessage("Airline names", expected.getAirlineNames(), actual.getAirlineName()));
        softAssert.assertTrue(isTimeBetweenRange(expected.getDepartureTimeMin(), expected.getDepartureTimeMax(), actual.getDepartureTime()),
                failMessage("Departure time", expected.getDepartureTimeMin(), expected.getDepartureTimeMax(), actual.getDepartureTime()));
        softAssert.assertTrue(isTimeBetweenRange(expected.getArrivalTimeMin(), expected.getArrivalTimeMax(), actual.getArrivalTime()),
                failMessage("Arrival time", expected.getArrivalTimeMin(), expected.getArrivalTimeMax(), actual.getArrivalTime()));
        softAssert.assertTrue(isDurationUpToMax(expected.getDurationMax(), actual.getFlightDuration()),
                failMessage("Flight duration", Duration.ofSeconds(0).getSeconds() + "s", expected.getDurationMax().getSeconds() + "s", actual.getFlightDuration().getSeconds() + "s"));
        softAssert.assertEquals(expected.getFlightStopsMax(), actual.getFlightStops(),
                failMessage("Flight stops", 0, expected.getFlightStopsMax(), actual.getFlightStops()));
        return this;
    }

    public void softAssertAll() {
        softAssert.assertAll();
    }

    private String failMessage(String assertion, Object expected, Object actual) {
        return String.format("%s assertion failed. Expected '%s' but found '%s'", assertion, expected, actual);
    }

    private String failMessage(String assertion, List<Object> expected, Object actual) {
        return String.format("%s assertion failed. Expected one of '%s' but found '%s'", assertion, expected.toString(), actual);
    }

    private String failMessage(String assertion, Object expectedMin, Object expectedMax, Object actual) {
        return String.format("%s assertion failed. Expected to be between '%s' and '%s' but found '%s'", assertion, expectedMin, expectedMax, actual);
    }

    private boolean isTimeBetweenRange(LocalTime min, LocalTime max, LocalTime actual) {
        return min.compareTo(actual) <= 0 && max.compareTo(actual) >= 0;
    }

    private boolean isDurationUpToMax(Duration max, Duration actual) {
        return max.compareTo(actual) >= 0;
    }


}
