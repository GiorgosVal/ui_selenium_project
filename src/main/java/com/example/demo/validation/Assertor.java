package com.example.demo.validation;

import com.example.demo.validation.actual.ActualFlight;
import com.example.demo.validation.actual.ActualTrip;
import com.example.demo.validation.expected.ExpectedFlight;
import com.example.demo.validation.expected.ExpectedTrip;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

public class Assertor {

    SoftAssert softAssert = new SoftAssert();

    public <T> Assertor softAssertEquals(T expected, T actual, String message) {
        softAssert.assertEquals(expected, actual, message);
        return this;
    }

    public Assertor softAssertGreaterThan(int less, int greater, String message) {
        softAssert.assertTrue(less < greater, message);
        return this;
    }

    public Assertor softAssertLessThan(int less, int greater, String message) {
        softAssertGreaterThan(less, greater, message);
        return this;
    }

    public <T> Assertor softAssertListEmpty(List<T> list, String message) {
        softAssert.assertTrue(list.isEmpty(), message);
        return this;
    }

    public Assertor softAssertTrue(boolean condition, String message) {
        softAssert.assertTrue(condition, message);
        return this;
    }

    public Assertor softAssertTrips(ExpectedTrip expected, List<ActualTrip> actualTrips) {
        actualTrips.forEach(actualTrip -> sofAssertTrip(expected, actualTrip));
        return this;
    }

    public Assertor sofAssertTrip(ExpectedTrip expected, ActualTrip actual) {
        softAssert.assertTrue(expected.getPriceMin() <= actual.getStandardPrice() && expected.getPriceMax() >= actual.getStandardPrice(),
                failMessage("Standard price", expected.getPriceMin(), expected.getPriceMax(), actual.getStandardPrice()));
        softAssert.assertEquals(expected.getExpectedFlightList().size(), actual.getActualFlightList().size(),
                failMessage("Flights list size", expected.getExpectedFlightList().size(), actual.getActualFlightList().size()));
        IntStream.range(0, expected.getExpectedFlightList().size()).forEach(index -> {
            softAssertFlight(expected.getExpectedFlightList().get(index), actual.getActualFlightList().get(index));
        });
        return this;
    }

    public Assertor softAssertFlight(ExpectedFlight expected, ActualFlight actual) {
        softAssert.assertTrue(expected.getAirlineNames().contains(actual.getAirlineName()),
                failMessage("Airline names", expected.getAirlineNames(), actual.getAirlineName()));
        softAssert.assertTrue(isTimeBetweenRange(expected.getDepartureTimeMin(), expected.getDepartureTimeMax(), actual.getDepartureTime()),
                failMessage("Departure time", expected.getDepartureTimeMin(), expected.getDepartureTimeMax(), actual.getDepartureTime()));
        softAssert.assertTrue(isTimeBetweenRange(expected.getArrivalTimeMin(), expected.getArrivalTimeMax(), actual.getArrivalTime()),
                failMessage("Arrival time", expected.getArrivalTimeMin(), expected.getArrivalTimeMax(), actual.getArrivalTime()));
        softAssert.assertTrue(isDurationUpToMax(expected.getDurationMax(), actual.getFlightDuration()),
                failMessage("Flight duration", Duration.ofSeconds(0).getSeconds() + "s", expected.getDurationMax().getSeconds() + "s", actual.getFlightDuration().getSeconds() + "s"));
        softAssert.assertTrue(expected.getFlightStopsMax() >= actual.getFlightStops(),
                failMessage("Flight stops", 0, expected.getFlightStopsMax(), actual.getFlightStops()));
        return this;
    }

    public void softAssertAll() {
        softAssert.assertAll();
    }

    public String failMessage(String assertion, Object expected, Object actual) {
        return String.format("%s assertion failed. Expected '%s' but found '%s'", assertion, expected, actual);
    }

    public String failMessage(String assertion, List<Object> expected, Object actual) {
        return String.format("%s assertion failed. Expected one of '%s' but found '%s'", assertion, expected.toString(), actual);
    }

    public <T> String failListEmpty(String assertion, List<T> list) {
        return String.format("%s assertion failed. Expected list to be empty but it was '%s'", assertion, list);
    }

    public <T> String failGreater(String assertion, T less, T greater) {
        return String.format("%s assertion failed. Expected '%s' to be greater than '%s'", assertion, greater, less);
    }

    public <T> String failLess(String assertion, T less, T greater) {
        return String.format("%s assertion failed. Expected '%s' to be less than '%s'", assertion, less, greater);
    }

    public String failMessage(String assertion, Object expectedMin, Object expectedMax, Object actual) {
        return String.format("%s assertion failed. Expected to be between '%s' and '%s' but found '%s'", assertion, expectedMin, expectedMax, actual);
    }

    public boolean isTimeBetweenRange(LocalTime min, LocalTime max, LocalTime actual) {
        return min.compareTo(actual) <= 0 && max.compareTo(actual) >= 0;
    }

    public boolean isDurationUpToMax(Duration max, Duration actual) {
        return max.compareTo(actual) >= 0;
    }


}
