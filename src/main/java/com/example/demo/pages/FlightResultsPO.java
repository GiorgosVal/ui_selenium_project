package com.example.demo.pages;

import com.example.demo.actions.Actions;
import com.example.demo.enums.QuickSortButton;
import com.example.demo.utils.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FlightResultsPO extends Actions {

    /*
     * --------------------------------------------------------------------------------------------------
     * ----------------------------------------- LOCATORS -----------------------------------------------
     * --------------------------------------------------------------------------------------------------
     */
    private enum Locators {
        FLIGHTS_RESULTS_COUNTER("[data-testid='resultPage-filters-text'] + span"),
        QUICK_SORT_BUTTON("//button[@data-testid='result-quick-sort-button']//span[contains(text(),'%s')]"),
        ALL_TRIPS("[data-testId*='resultPage-resultTrip-']"),
        FLIGHT("[data-testId='tripDetails-bound']"),
        FLIGHT_AIRLINE_NAME("div[data-testid='tripDetails-segment'] div img + div"),
        FLIGHT_TIME("[data-testid='trip-segment-%s-time'] span"),    // origin, destination
        FLIGHT_DURATION("[data-testid='searchResults-segment-duration']"),
        FLIGHT_PRICE("[data-testid='result-trip-price-%s']"), // standard, flex
        FLIGHT_STOPS("[data-testid='searchResults-segment-stops']");

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

    private By quickSortButtonLocator(QuickSortButton quickSortButton) {
        return By.xpath(Locators.QUICK_SORT_BUTTON.get(quickSortButton.getButton()));
    }

    private final By allTripsLocator = By.cssSelector(Locators.ALL_TRIPS.get());
    private final By flightLocator = By.cssSelector(Locators.FLIGHT.get());
    private final By flightAirLineNameLocator = By.cssSelector(Locators.FLIGHT_AIRLINE_NAME.get());
    private final By flightDepartureTimeLocator = By.cssSelector(Locators.FLIGHT_TIME.get("origin"));
    private final By flightArrivalTimeLocator = By.cssSelector(Locators.FLIGHT_TIME.get("destination"));
    private final By flightDurationLocator = By.cssSelector(Locators.FLIGHT_DURATION.get());
    private final By flightPriceStandardLocator = By.cssSelector(Locators.FLIGHT_PRICE.get("standard"));
    private final By flightPriceFlexibleLocator = By.cssSelector(Locators.FLIGHT_PRICE.get("flex"));
    private final By flightStopsLocator = By.cssSelector(Locators.FLIGHT_STOPS.get());

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
        return waitUntilElementsAreVisible(allTripsLocator);
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
            trip.findElements(flightLocator).forEach(flightType -> {
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
