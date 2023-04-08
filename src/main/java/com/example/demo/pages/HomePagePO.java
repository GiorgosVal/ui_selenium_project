package com.example.demo.pages;

import com.example.demo.enums.PassengerType;
import com.example.demo.actions.Actions;
import com.example.demo.factories.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class HomePagePO extends Actions {

    /*
     * --------------------------------------------------------------------------------------------------
     * ----------------------------------------- LOCATORS -----------------------------------------------
     * --------------------------------------------------------------------------------------------------
     */
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

    private final By acceptCookiesLocator = By.cssSelector(Locators.ACCEPT_COOKIES_BUTTON.get());
    private final By returnRadioButtonLocator = By.cssSelector(Locators.FLIGHT_TYPE_RADIO_BUTTON.get("return"));
    private final By oneWayRadioButtonLocator = By.cssSelector(Locators.FLIGHT_TYPE_RADIO_BUTTON.get("oneWay"));
    private final By multiCityRadioButtonLocator = By.cssSelector(Locators.FLIGHT_TYPE_RADIO_BUTTON.get("multiStop"));
    private final By fromAirportInputLocator = By.cssSelector(Locators.AIRPORT_INPUT.get("origin"));
    private final By toAirportInputLocator = By.cssSelector(Locators.AIRPORT_INPUT.get("destination"));
    private final By addTripButtonLocator = By.cssSelector(Locators.ADD_TRIP_BUTTON.get());
    private final By genericDropDownLocator = By.cssSelector(Locators.GENERIC_DROPDOWN.get());
    private final By departureDateInputLocator = By.cssSelector(Locators.DATE_INPUT.get("departureDate"));
    private final By returnDateInputLocator = By.cssSelector(Locators.DATE_INPUT.get("returnDate"));
    private final By currentMonthLocator = By.cssSelector(Locators.CURRENT_MONTH.get());
    private final By previousMonthLocator = By.cssSelector(Locators.DATE_MONTH_PICKER.get("previous"));
    private final By nextMonthLocator = By.cssSelector(Locators.DATE_MONTH_PICKER.get("next"));
    private final By passengersDropDownLocator = By.cssSelector(Locators.PASSENGERS_DROPDOWN.get());

    private final By passengersIncreaseSeatButtonLocator(PassengerType passengerType) {
        return By.cssSelector(Locators.PASSENGER_INCREASE_SEAT_BUTTON.get(passengerType.get()));
    }

    private final By passengersDecreaseSeatButtonLocator(PassengerType passengerType) {
        return By.cssSelector(Locators.PASSENGER_DECREASE_SEAT_BUTTON.get(passengerType.get()));
    }

    private final By passengersSeatsCounterLocator(PassengerType passengerType) {
        return By.cssSelector(Locators.PASSENGER_SEATS_COUNTER.get(passengerType.get()));
    }

    private final By cabinClassDropdownLocator = By.cssSelector(Locators.CABIN_CLASS_DROPDOWN.get());
    private final By nonStopFlightsLocator = By.cssSelector(Locators.FLIGHT_TYPE_RADIO_BUTTON.get());
    private final By searchFlightsButtonLocator = By.cssSelector(Locators.SEARCH_FLIGHTS_BUTTON.get());

    /**
     * Returns the locator for the date element of a date picker
     *
     * @param date - The date for which to return the locator
     * @return - the locator
     */
    private By selectedDateLocator(String date) {
        return By.cssSelector(Locators.SELECTED_DATE.get(date));
    }

    /**
     * Returns the locator for the origin airport when on multi city flights
     *
     * @param index - the index of the element
     * @return - this
     */
    private By fromAirportMultiBoundLocator(int index) {
        return By.cssSelector(Locators.AIRPORT_MULTI_INPUT.get(index, "origin"));
    }

    /**
     * Returns the locator for the origin airport when on multi city flights
     *
     * @param index - the index of the element
     * @return - this
     */
    private By toAirportMultiBoundLocator(int index) {
        return By.cssSelector(Locators.AIRPORT_MULTI_INPUT.get(index, "destination"));
    }

    /**
     * Returns the locator for the departure date when on multi city flights
     *
     * @param index - the index of the element
     * @return - this
     */
    private By departureDateMultiInputLocator(int index) {
        return By.cssSelector(Locators.DATE_INPUT_MULTI.get(index, "departureDate"));
    }

    /*
     * --------------------------------------------------------------------------------------------------
     * ------------------------------------- PAGE OBJECT METHODS ----------------------------------------
     * --------------------------------------------------------------------------------------------------
     */

    /**
     * Clicks accept cookies button
     *
     * @return - this
     */
    public HomePagePO clickAcceptCookies() {    //TODO make this optional
        waitUntilElementIsClickable(acceptCookiesLocator).click();
        return this;
    }

    /**
     * Clicks the return radio button
     *
     * @return - this
     */
    public HomePagePO clickReturnRadioButton() {
        waitUntilElementIsClickable(returnRadioButtonLocator).click();
        return this;
    }

    /**
     * Clicks the one-way radio button
     *
     * @return - this
     */
    public HomePagePO clickOneWayRadioButton() {
        waitUntilElementIsClickable(oneWayRadioButtonLocator).click();
        return this;
    }

    /**
     * Clicks the one-way radio button
     *
     * @return - this
     */
    public HomePagePO clickMultiCityRadioButton() {
        waitUntilElementIsClickable(multiCityRadioButtonLocator).click();
        return this;
    }

    /**
     * Writes a text in the origin airport input
     *
     * @param from - the text to write
     * @return - this
     */
    public HomePagePO writeFromAirportInput(String from) {
        waitUntilElementIsVisible(fromAirportInputLocator).sendKeys(from);
        return this;
    }

    /**
     * Writes a text in the origin airport input, when on multi city flights
     *
     * @param index - the index of the input
     * @param from  - the text to write
     * @return - this
     */
    public HomePagePO writeFromAirportInputMultiBound(int index, String from) {
        waitUntilElementIsVisible(fromAirportMultiBoundLocator(index)).sendKeys(from);
        return this;
    }

    /**
     * Writes a text in the destination airport input
     *
     * @param to - the text to write
     * @return - this
     */
    public HomePagePO writeToAirportInput(String to) {
        waitUntilElementIsVisible(toAirportInputLocator).sendKeys(to);
        return this;
    }

    /**
     * Writes a text in the destination airport input, when on multi city flights
     *
     * @param index - the index of the input
     * @param from  - the text to write
     * @return - this
     */
    public HomePagePO writeToAirportInputMultiBound(int index, String from) {
        waitUntilElementIsVisible(toAirportMultiBoundLocator(index)).sendKeys(from);
        return this;
    }

    /**
     * Clicks the add trip button (available on multi city flights)
     *
     * @return - this
     */
    public HomePagePO clickAddTripButton() {
        waitUntilElementIsClickable(addTripButtonLocator).click();
        return this;
    }

    /**
     * Selects the departure date
     * <p>
     * Note: Do not use when multi city flights. Use the {@link #selectDepartureDateMulti(int, LocalDateTime)} instead
     *
     * @param date - The date to select
     * @return - this
     */
    public HomePagePO selectDepartureDate(LocalDateTime date) {
        selectFutureDate(date, departureDateInputLocator);
        return this;
    }

    /**
     * Selects the departure date when on multi city flights
     *
     * @param date  - The date to select
     * @param index - The index of the date picker element
     * @return - this
     */
    public HomePagePO selectDepartureDateMulti(int index, LocalDateTime date) {
        selectFutureDate(date, departureDateMultiInputLocator(index));
        return this;
    }


    /**
     * Selects the return date
     *
     * @param date - The date to select
     * @return - this
     */
    public HomePagePO selectReturnDate(LocalDateTime date) {
        selectFutureDate(date, returnDateInputLocator);
        return this;
    }

    /**
     * Clicks the next month button in the date picker
     *
     * @return - this
     */
    public HomePagePO clickNextMonth() {
        waitUntilElementIsClickable(nextMonthLocator).click();
        return this;
    }

    /**
     * Clicks the previous month button in the date picker
     *
     * @return - this
     */
    public HomePagePO clickPreviousMonth() {
        waitUntilElementIsClickable(previousMonthLocator).click();
        return this;
    }

    /**
     * Clicks the passenger dropdown
     *
     * @return - this
     */
    public HomePagePO clickPassengersDropdown() {
        waitUntilElementIsClickable(passengersDropDownLocator).click();
        return this;
    }

    /**
     * Increases the seats for the specific passenger type, one each time
     *
     * @param passengerType - For which passenger type to increase the seats
     * @return - this
     */
    public HomePagePO increasePassengerSeats(PassengerType passengerType) {
        waitUntilElementIsVisible(passengersDropDownLocator);
        WebElement passengerElement = Objects.requireNonNull(findPassengerMatchingType(passengerType));
        waitUntilElementIsClickable(passengersIncreaseSeatButtonLocator(passengerType));
        passengerElement.findElement(passengersIncreaseSeatButtonLocator(passengerType)).click();
        return this;
    }

    /**
     * Decreases the seats for the specific passenger type, one each time
     *
     * @param passengerType - For which passenger type to decrease the seats
     * @return - this
     */
    public HomePagePO decreasePassengerSeats(PassengerType passengerType) {
        waitUntilElementIsVisible(passengersDropDownLocator);
        Objects.requireNonNull(findPassengerMatchingType(passengerType)).findElement(passengersDecreaseSeatButtonLocator(passengerType)).click();
        return this;
    }

    /**
     * Returns the number of seats that are selected for this specific passenger type
     *
     * @param passengerType - For which passenger type to get the seats number
     * @return - the seats number for the passenger type
     */
    public int getPassengerSeats(PassengerType passengerType) {
        waitUntilElementIsVisible(passengersDropDownLocator);
        return Integer.parseInt(Objects.requireNonNull(findPassengerMatchingType(passengerType)).findElement(passengersSeatsCounterLocator(passengerType)).getText());
    }

    /**
     * Clicks the cabin class dropdown
     *
     * @return - this
     */
    public HomePagePO clickCabinClassDropdown() {
        waitUntilElementIsClickable(cabinClassDropdownLocator).click();
        return this;
    }

    /**
     * Selects the element from the dropdown, that matches its text matches the {@code elementText} given (case-insensitive)
     *
     * @param elementText - The text of the element
     * @return - this
     */
    public HomePagePO selectElementFromDropdown(String elementText) {
        Objects.requireNonNull(waitUntilElementsAreVisible(genericDropDownLocator).stream()
                .filter(airportElement -> airportElement.getText().equalsIgnoreCase(elementText)).findFirst()
                .orElse(null)).click();
        return this;
    }

    /**
     * Clicks the nonstop flights only checkbox
     *
     * @return - this
     */
    public HomePagePO clickNonStopFlightsCheckbox() {
        waitUntilElementIsClickable(nonStopFlightsLocator).click();
        return this;
    }

    /**
     * Clicks the search flights button
     *
     * @return - this
     */
    public HomePagePO clickSearchFlightsButton() {
        waitUntilElementIsClickable(searchFlightsButtonLocator).click();
        return this;
    }

    /*-- PRIVATE METHODS --*/

    /**
     * Selects date from a date picker. Selection happens on the departure or return date picker,
     * according to the locator passed.
     *
     * @param date    - The date to pick
     * @param locator - The locator specifying the departure / return date picker
     * @return - this
     */
    private HomePagePO selectFutureDate(LocalDateTime date, By locator) {
        waitUntilElementIsVisible(locator).click();
        switchNextMonthInDatePickerToMatchDate(date);
        waitUntilElementIsClickable(selectedDateLocator(date.format(DateTimeFormatter.ofPattern("EEE MMM dd yyyy")))).click(); // Tue Mar 28 2023
        return this;
    }

    /**
     * Clicks next month in a date picker until the {@code date} given matches with the month displayed by the date picker
     *
     * @param date - The target date
     * @return - this
     */
    private HomePagePO switchNextMonthInDatePickerToMatchDate(LocalDateTime date) {
        while (LocalDateTime.of(date.getYear(), date.getMonth(), 1, 0, 0).isAfter(getCurrentDate())) {
            clickNextMonth();
        }
        return this;
    }

    /**
     * Returns the current date (year, month) that is displayed in a date picker.
     * <p>
     * Note: The value returned will have dayOfMonth = 1, hour = 0, minute = 0
     *
     * @return - the current date
     */
    private LocalDateTime getCurrentDate() {
        String currentDate = waitUntilElementIsVisible(currentMonthLocator).getText();
        int year = Integer.parseInt(currentDate.split("\\s+")[1]);
        Month month = Month.valueOf(currentDate.split(" ")[0].toUpperCase());
        return LocalDateTime.of(year, month, 1, 0, 0);
    }


    /**
     * Filters the passenger types from the Passengers dropdown according to the {@code PassengerType} passed
     *
     * @param passengerType - Which passenger type to match
     * @return - The WebElement matching the passenger type, or null
     */
    private WebElement findPassengerMatchingType(PassengerType passengerType) {
        List<WebElement> elementList = WebDriverFactory.getDriver().findElement(passengersDropDownLocator).findElements(genericDropDownLocator);
        return elementList.stream().filter(element -> element.getText().toLowerCase().startsWith(passengerType.get().toLowerCase())).findFirst().orElse(null);
    }


}
