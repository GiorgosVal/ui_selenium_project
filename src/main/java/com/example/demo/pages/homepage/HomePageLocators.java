package com.example.demo.pages.homepage;

import com.example.demo.enums.PassengerType;
import org.openqa.selenium.By;

public class HomePageLocators {

    private enum Locators {
        ACCEPT_COOKIES_BUTTON("[data-testid='cookieBanner-confirmButton']"),
        FLIGHT_TYPE_RADIO_BUTTON("[data-testid='searchForm-%s-radio-label']"),
        AIRPORT_INPUT_PARENT("[data-testId='searchForm-singleBound-%s-input']"),
        AIRPORT_INPUT("[data-testid='searchForm-singleBound-%s-input'] input"),
        AIRPORT_MULTI_INPUT("[data-testId='searchForm-multiBound[%s]-%s-input'] input"),
        ADD_TRIP_BUTTON("[data-testId='searchForm-multiStop-addTrip-button']"),
        GENERIC_DROPDOWN("[data-testId='etiDropdownOption']"),
        DATE_INPUT("[data-testid='singleBound.%s-input']"),
        DATE_INPUT_MULTI("[data-testid='multiBound[%s].%s-input']"),
        DATE_MONTH_PICKER("[data-testid='searchForm-%sMonth-input']"),
        CURRENT_MONTH(".DayPicker-Caption div"),
        SELECTED_DATE(".DayPicker-Day:not(.DayPicker-Day--disabled)[aria-label='%s']"),
        PASSENGERS_DROPDOWN("[data-testid='searchForm-passengers-dropdown']"),
        PASSENGER_INCREASE_SEAT_BUTTON("[data-testId='counter-%s-plus-button']"),
        PASSENGER_DECREASE_SEAT_BUTTON("[data-testId='counter-%s-minus-button']"),
        PASSENGER_SEATS_COUNTER("[data-testId='counter-%s-value']"),
        CABIN_CLASS_DROPDOWN("[data-testid='searchForm-cabinClasses-dropdown']"),
        NON_STOP_FLIGHTS("[data-testid='directFlight-input']"),
        SEARCH_FLIGHTS_BUTTON("[data-testid='searchForm-searchFlights-button']");

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

    protected final By acceptCookiesLocator = By.cssSelector(Locators.ACCEPT_COOKIES_BUTTON.get());
    protected final By returnRadioButtonLocator = By.cssSelector(Locators.FLIGHT_TYPE_RADIO_BUTTON.get("return"));
    protected final By oneWayRadioButtonLocator = By.cssSelector(Locators.FLIGHT_TYPE_RADIO_BUTTON.get("oneWay"));
    protected final By multiCityRadioButtonLocator = By.cssSelector(Locators.FLIGHT_TYPE_RADIO_BUTTON.get("multiStop"));
    protected final By fromAirportInputLocator = By.cssSelector(Locators.AIRPORT_INPUT.get("origin"));
    protected final By toAirportInputLocator = By.cssSelector(Locators.AIRPORT_INPUT.get("destination"));
    protected final By addTripButtonLocator = By.cssSelector(Locators.ADD_TRIP_BUTTON.get());
    protected final By genericDropDownLocator = By.cssSelector(Locators.GENERIC_DROPDOWN.get());
    protected final By departureDateInputLocator = By.cssSelector(Locators.DATE_INPUT.get("departureDate"));
    protected final By returnDateInputLocator = By.cssSelector(Locators.DATE_INPUT.get("returnDate"));
    protected final By currentMonthLocator = By.cssSelector(Locators.CURRENT_MONTH.get());
    protected final By previousMonthLocator = By.cssSelector(Locators.DATE_MONTH_PICKER.get("previous"));
    protected final By nextMonthLocator = By.cssSelector(Locators.DATE_MONTH_PICKER.get("next"));
    protected final By passengersDropDownLocator = By.cssSelector(Locators.PASSENGERS_DROPDOWN.get());

    protected final By passengersIncreaseSeatButtonLocator(PassengerType passengerType) {
        return By.cssSelector(Locators.PASSENGER_INCREASE_SEAT_BUTTON.get(passengerType.get()));
    }

    protected final By passengersDecreaseSeatButtonLocator(PassengerType passengerType) {
        return By.cssSelector(Locators.PASSENGER_DECREASE_SEAT_BUTTON.get(passengerType.get()));
    }

    protected final By passengersSeatsCounterLocator(PassengerType passengerType) {
        return By.cssSelector(Locators.PASSENGER_SEATS_COUNTER.get(passengerType.get()));
    }

    protected final By cabinClassDropdownLocator = By.cssSelector(Locators.CABIN_CLASS_DROPDOWN.get());
    protected final By nonStopFlightsLocator = By.cssSelector(Locators.FLIGHT_TYPE_RADIO_BUTTON.get());
    protected final By searchFlightsButtonLocator = By.cssSelector(Locators.SEARCH_FLIGHTS_BUTTON.get());

    /**
     * Returns the locator for the date element of a date picker
     *
     * @param date - The date for which to return the locator
     * @return - the locator
     */
    protected By selectedDateLocator(String date) {
        return By.cssSelector(Locators.SELECTED_DATE.get(date));
    }

    /**
     * Returns the locator for the origin airport when on multi city flights
     *
     * @param index - the index of the element
     * @return - this
     */
    protected By fromAirportMultiBoundLocator(int index) {
        return By.cssSelector(Locators.AIRPORT_MULTI_INPUT.get(index, "origin"));
    }

    /**
     * Returns the locator for the origin airport when on multi city flights
     *
     * @param index - the index of the element
     * @return - this
     */
    protected By toAirportMultiBoundLocator(int index) {
        return By.cssSelector(Locators.AIRPORT_MULTI_INPUT.get(index, "destination"));
    }

    /**
     * Returns the locator for the departure date when on multi city flights
     *
     * @param index - the index of the element
     * @return - this
     */
    protected By departureDateMultiInputLocator(int index) {
        return By.cssSelector(Locators.DATE_INPUT_MULTI.get(index, "departureDate"));
    }

}
