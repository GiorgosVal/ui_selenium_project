package com.example.demo.pages;

import com.example.demo.actions.BaseCommands;
import com.example.demo.enums.QuickSortButton;
import com.example.demo.utils.StringUtils;
import com.example.demo.utils.TimeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FlightResultsPO extends BaseCommands {

    /*
     * --------------------------------------------------------------------------------------------------
     * ----------------------------------------- LOCATORS -----------------------------------------------
     * --------------------------------------------------------------------------------------------------
     */
    private enum Locators {
        FLIGHTS_RESULTS_COUNTER("[data-testid='resultPage-filters-text'] + span"),
        FILTERS_BUTTON("[data-testid='resultPage-toggleFiltersButton-button']"),
        FILTERS_CONTENT("[data-testid='resultPage-searchFilters-content']"),
        QUICK_SORT_BUTTON("//button[@data-testid='result-quick-sort-button']//span[contains(text(),'%s')]"),
        ALL_TRIPS("[data-testId*='resultPage-resultTrip-']"),
        FLIGHT("[data-testId='tripDetails-bound']"),
        FLIGHT_AIRLINE_NAME("div[data-testid='tripDetails-segment'] div img + div div"),
        FLIGHT_TIME("[data-testid='trip-segment-%s-time'] span"),    // origin, destination
        FLIGHT_STOPS("[data-testid='searchResults-segment-stops']"),
        FLIGHT_DURATION("[data-testid='searchResults-segment-duration']"),
        TRIP_PRICE("[data-testid='result-trip-price-%s']"), // standard, flex

        ;

        String locator;

        Locators(String locator) {
            this.locator = locator;
        }

        String get() {
            return locator;
        }

        String get(Object... params) {
            return String.format(get(), params);
        }
    }

    private final By flightResultsCounterLocator = By.cssSelector(Locators.FLIGHTS_RESULTS_COUNTER.get());

    private final By filtersToggleButton = By.cssSelector(Locators.FILTERS_BUTTON.get());

    private final By filtersContentLocator = By.cssSelector(Locators.FILTERS_CONTENT.get());

    private By quickSortButtonLocator(QuickSortButton quickSortButton) {
        return By.xpath(Locators.QUICK_SORT_BUTTON.get(quickSortButton.getButton()));
    }

    private final By allTripsLocator = By.cssSelector(Locators.ALL_TRIPS.get());
    private final By flightLocator = By.cssSelector(Locators.FLIGHT.get());
    private final By flightAirLineNameLocator = By.cssSelector(Locators.FLIGHT_AIRLINE_NAME.get());
    private final By flightDepartureTimeLocator = By.cssSelector(Locators.FLIGHT_TIME.get("origin"));
    private final By flightArrivalTimeLocator = By.cssSelector(Locators.FLIGHT_TIME.get("destination"));
    private final By flightDurationLocator = By.cssSelector(Locators.FLIGHT_DURATION.get());
    private final By flightStopsLocator = By.cssSelector(Locators.FLIGHT_STOPS.get());
    private final By tripPriceStandardLocator = By.cssSelector(Locators.TRIP_PRICE.get("standard"));
    private final By tripPriceFlexibleLocator = By.cssSelector(Locators.TRIP_PRICE.get("flex"));


    /*
     * --------------------------------------------------------------------------------------------------
     * ------------------------------------- PAGE OBJECT METHODS ----------------------------------------
     * --------------------------------------------------------------------------------------------------
     */

    /**
     * Returns the flights counter number
     *
     * @return - this
     */
    public int getFlightsResultsCount() {
        return Integer.parseInt(Objects.requireNonNull(
                StringUtils.extractFirstNumberFromString(waitUntilElementIsVisible(flightResultsCounterLocator).getText())));
    }

    /**
     * Clicks the filters toggle
     *
     * @return - this
     */
    public FlightResultsPO clickFiltersToggle() {
        waitUntilElementIsClickable(filtersToggleButton).click();
        return this;
    }

    /**
     * Checks if the filters are displayed
     *
     * @return - true if displayed, false otherwise
     */
    public boolean areFiltersDisplayed() {
        return elementExistsNoWait(waitUntilElementIsVisible(By.cssSelector("body")), filtersContentLocator);
    }

    /**
     * Clicks the quick sort button specified
     *
     * @param quickSortButton - The {@link QuickSortButton} to click
     * @return - this
     */
    public FlightResultsPO clickQuickSortButton(QuickSortButton quickSortButton) {
        waitUntilElementIsClickable(quickSortButtonLocator(quickSortButton)).click();
        return this;
    }

    /**
     * Returns all the trips found in the results
     *
     * @return - a list containing all the trips found
     */
    public List<WebElement> getAllTrips() {
        return waitUntilElementsAreVisibleWithRetry(allTripsLocator);
    }

    /**
     * Returns all the flights of a trip
     *
     * @param trip - The trip web element
     * @return - a list with the flights
     */
    public List<WebElement> getTripFlights(WebElement trip) {
        return trip.findElements(flightLocator);
    }

    /**
     * Returns the airline name for a flight
     *
     * @param flight - The flight web element
     * @return - the airline name
     */
    public String getFlightAirlineName(WebElement flight) {
        return flight.findElement(flightAirLineNameLocator).getText();
    }

    /**
     * Returns the arrival time of a flight
     *
     * @param flight - The flight web element
     * @return - the arrival time
     */
    public LocalTime getFlightArrivalTime(WebElement flight) {
        return LocalTime.parse(flight.findElement(flightArrivalTimeLocator).getText(), DateTimeFormatter.ofPattern("HH:mm"));
    }

    /**
     * Returns the departure time of a flight
     *
     * @param flight - The flight web element
     * @return - the departure time
     */
    public LocalTime getFlightDepartureTime(WebElement flight) {
        return LocalTime.parse(flight.findElement(flightDepartureTimeLocator).getText(), DateTimeFormatter.ofPattern("HH:mm"));
    }

    /**
     * Returns the duration of a flight
     *
     * @param flight - The flight web element
     * @return - the duration
     */
    public Duration getFlightDurationTime(WebElement flight) {
        String duration = flight.findElement(flightDurationLocator).getText();
        return TimeUtils.stringToDuration(duration);
    }

    /**
     * Returns the stops of a flight
     *
     * @param flight - The flight web element
     * @return - the stops
     */
    public int getFlightStops(WebElement flight) {
        int stops = 0;
        if (elementExistsNoWait(flight, flightStopsLocator)) {
            stops = Integer.parseInt(StringUtils.extractFirstNumberFromString(flight.findElement(flightStopsLocator).getText()));
        }
        return stops;
    }

    /**
     * Returns the standard price of a trip
     *
     * @param trip - The trip web element
     * @return - the standard price
     */
    public Float getTripStandardPrice(WebElement trip) {
        return Float.valueOf(StringUtils.extractPriceFromString(trip.findElement(tripPriceStandardLocator).getText()).replace(",", ""));
    }

    /**
     * Returns the flexible price of a trip
     *
     * @param trip - The trip web element
     * @return - the /** price
     */
    public Float getTripFlexiblePrice(WebElement trip) {
        return Float.valueOf(StringUtils.extractPriceFromString(trip.findElement(tripPriceFlexibleLocator).getText()).replace(",", ""));
    }


    /**
     * Converts a list of trips, to a map containing the all flights of all trips according to their type
     * (e.g. single flight, return flight, intermediate flight, etc).
     * <p>
     * For example if there are multiple trips, each having a 2 flights (single, return), it will return the map:
     * <ul>
     *     <li>0: List.of(single flight 1, single flight 2, ...)</li>
     *     <li>1: List.of(return flight 1, return flight 2, ...)</li>
     * </ul>
     *
     * @param trips - A list of trips
     * @return - a map with sorted flights according to their type
     */
    public Map<Integer, List<WebElement>> tripsToFlightTypesMap(List<WebElement> trips) {   //TODO make this private or add to utils
        Map<Integer, List<WebElement>> flightsMap = new HashMap<>();
        trips.forEach(trip -> {
            waitUntilElementsAreVisible(flightLocator);
            AtomicInteger flightTypeIndex = new AtomicInteger();
            getTripFlights(trip).forEach(flightType -> {
                List<WebElement> flightsRegistered;
                if (flightsMap.containsKey(flightTypeIndex.get())) {
                    flightsRegistered = flightsMap.get(flightTypeIndex.get());
                } else {
                    flightsRegistered = new ArrayList<>();
                }
                flightsRegistered.add(flightType);
                flightsMap.put(flightTypeIndex.get(), flightsRegistered);
                flightTypeIndex.getAndIncrement();
            });
        });
        return flightsMap;
    }
}
