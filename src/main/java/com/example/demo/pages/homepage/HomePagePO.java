package com.example.demo.pages.homepage;

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

    HomePageLocators homePageLocators;

    public HomePagePO() {
        homePageLocators = new HomePageLocators();
    }

    /**
     * Clicks accept cookies button
     *
     * @return - this
     */
    public HomePagePO clickAcceptCookies() {
        waitUntilElementIsClickable(homePageLocators.acceptCookiesLocator).click();
        return this;
    }

    /**
     * Clicks the return radio button
     *
     * @return - this
     */
    public HomePagePO clickReturnRadioButton() {
        waitUntilElementIsClickable(homePageLocators.returnRadioButtonLocator).click();
        return this;
    }

    /**
     * Clicks the one-way radio button
     *
     * @return - this
     */
    public HomePagePO clickOneWayRadioButton() {
        waitUntilElementIsClickable(homePageLocators.oneWayRadioButtonLocator).click();
        return this;
    }

    /**
     * Clicks the one-way radio button
     *
     * @return - this
     */
    public HomePagePO clickMultiCityRadioButton() {
        waitUntilElementIsClickable(homePageLocators.multiCityRadioButtonLocator).click();
        return this;
    }

    /**
     * Writes a text in the origin airport input
     *
     * @param from - the text to write
     * @return - this
     */
    public HomePagePO writeFromAirportInput(String from) {
        waitUntilElementIsVisible(homePageLocators.fromAirportInputLocator).sendKeys(from);
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
        waitUntilElementIsVisible(homePageLocators.fromAirportMultiBoundLocator(index)).sendKeys(from);
        return this;
    }

    /**
     * Writes a text in the destination airport input
     *
     * @param to - the text to write
     * @return - this
     */
    public HomePagePO writeToAirportInput(String to) {
        waitUntilElementIsVisible(homePageLocators.toAirportInputLocator).sendKeys(to);
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
        waitUntilElementIsVisible(homePageLocators.toAirportMultiBoundLocator(index)).sendKeys(from);
        return this;
    }

    /**
     * Clicks the add trip button (available on multi city flights)
     *
     * @return - this
     */
    public HomePagePO clickAddTripButton() {
        waitUntilElementIsClickable(homePageLocators.addTripButtonLocator).click();
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
        selectFutureDate(date, homePageLocators.departureDateInputLocator);
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
        selectFutureDate(date, homePageLocators.departureDateMultiInputLocator(index));
        return this;
    }


    /**
     * Selects the return date
     *
     * @param date - The date to select
     * @return - this
     */
    public HomePagePO selectReturnDate(LocalDateTime date) {
        selectFutureDate(date, homePageLocators.returnDateInputLocator);
        return this;
    }

    /**
     * Clicks the next month button in the date picker
     *
     * @return - this
     */
    public HomePagePO clickNextMonth() {
        waitUntilElementIsClickable(homePageLocators.nextMonthLocator).click();
        return this;
    }

    /**
     * Clicks the previous month button in the date picker
     *
     * @return - this
     */
    public HomePagePO clickPreviousMonth() {
        waitUntilElementIsClickable(homePageLocators.previousMonthLocator).click();
        return this;
    }

    /**
     * Clicks the passenger dropdown
     *
     * @return - this
     */
    public HomePagePO clickPassengersDropdown() {
        waitUntilElementIsClickable(homePageLocators.passengersDropDownLocator).click();
        return this;
    }

    /**
     * Increases the seats for the specific passenger type, one each time
     *
     * @param passengerType - For which passenger type to increase the seats
     * @return - this
     */
    public HomePagePO increasePassengerSeats(PassengerType passengerType) {
        waitUntilElementIsVisible(homePageLocators.passengersDropDownLocator);
        WebElement passengerElement = Objects.requireNonNull(findPassengerMatchingType(passengerType));
        waitUntilElementIsClickable(homePageLocators.passengersIncreaseSeatButtonLocator(passengerType));
        passengerElement.findElement(homePageLocators.passengersIncreaseSeatButtonLocator(passengerType)).click();
        return this;
    }

    /**
     * Decreases the seats for the specific passenger type, one each time
     *
     * @param passengerType - For which passenger type to decrease the seats
     * @return - this
     */
    public HomePagePO decreasePassengerSeats(PassengerType passengerType) {
        waitUntilElementIsVisible(homePageLocators.passengersDropDownLocator);
        Objects.requireNonNull(findPassengerMatchingType(passengerType)).findElement(homePageLocators.passengersDecreaseSeatButtonLocator(passengerType)).click();
        return this;
    }

    /**
     * Returns the number of seats that are selected for this specific passenger type
     *
     * @param passengerType - For which passenger type to get the seats number
     * @return - the seats number for the passenger type
     */
    public int getPassengerSeats(PassengerType passengerType) {
        waitUntilElementIsVisible(homePageLocators.passengersDropDownLocator);
        return Integer.parseInt(Objects.requireNonNull(findPassengerMatchingType(passengerType)).findElement(homePageLocators.passengersSeatsCounterLocator(passengerType)).getText());
    }

    /**
     * Clicks the cabin class dropdown
     *
     * @return - this
     */
    public HomePagePO clickCabinClassDropdown() {
        waitUntilElementIsClickable(homePageLocators.cabinClassDropdownLocator).click();
        return this;
    }

    /**
     * Selects the element from the dropdown, that matches its text matches the {@code elementText} given (case-insensitive)
     *
     * @param elementText - The text of the element
     * @return - this
     */
    public HomePagePO selectElementFromDropdown(String elementText) {
        Objects.requireNonNull(waitUntilElementsAreVisible(homePageLocators.genericDropDownLocator).stream()
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
        waitUntilElementIsClickable(homePageLocators.nonStopFlightsLocator).click();
        return this;
    }

    /**
     * Clicks the search flights button
     *
     * @return - this
     */
    public HomePagePO clickSearchFlightsButton() {
        waitUntilElementIsClickable(homePageLocators.searchFlightsButtonLocator).click();
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
        waitUntilElementIsClickable(homePageLocators.selectedDateLocator(date.format(DateTimeFormatter.ofPattern("EEE MMM dd yyyy")))).click(); // Tue Mar 28 2023
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
        String currentDate = waitUntilElementIsVisible(homePageLocators.currentMonthLocator).getText();
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
        List<WebElement> elementList = WebDriverFactory.getDriver().findElement(homePageLocators.passengersDropDownLocator).findElements(homePageLocators.genericDropDownLocator);
        return elementList.stream().filter(element -> element.getText().toLowerCase().startsWith(passengerType.get().toLowerCase())).findFirst().orElse(null);
    }


}
