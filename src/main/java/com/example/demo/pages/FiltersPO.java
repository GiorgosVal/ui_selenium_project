package com.example.demo.pages;

import com.example.demo.actions.BaseCommands;
import com.example.demo.utils.StringUtils;
import com.example.demo.utils.TimeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.example.demo.utils.TimeUtils.hoursMinutesToLocalTime;

public class FiltersPO extends BaseCommands {

    /*
     * --------------------------------------------------------------------------------------------------
     * ----------------------------------------- LOCATORS -----------------------------------------------
     * --------------------------------------------------------------------------------------------------
     */
    private enum Locators {

        NUMBER_OF_STOPS_FILTER("[data-testid='MAX_STOPS-%s']"), //'direct'    'max1'   'all'
        GENERIC_SLIDER_FILTER_HANDLE("[data-testid='resultPage-%s-content'] [data-testid='handle-%s']"),     //the 1st is one of: PRICEFilter, departureArrival0Filter, departureArrival1Filter, TRAVEL_TIMEFilter - the 2nd is 0 for the low handle, 1 for the high handle
        GENERIC_SLIDER_FILTER_LOWEST_VALUE("[data-testid='resultPage-%s-content'] .slider-tracks + div"),            //the 1st is one of: PRICEFilter, departureArrival0Filter, departureArrival1Filter, TRAVEL_TIMEFilter
        GENERIC_SLIDER_FILTER_HIGHEST_VALUE("[data-testid='resultPage-%s-content'] .slider-tracks + div + div"),     //the 1st is one of: PRICEFilter, departureArrival0Filter, departureArrival1Filter
        GENERIC_RESET_FILTER_BUTTON("[data-testid='resultPage-filterHeader-%sFilterResetButton-button']"),    //MAX_STOPS, PRICE, AIRLINES, departureArrival0, TRAVEL_TIME
        AIRLINES_FILTER_CLEAR_ALL("[data-testid='resultPage-AIRLINESFilter-content'] span"),
        AIRLINES_FILTER_SELECT_ALL("[data-testid='resultPage-AIRLINESFilter-content'] span + span"),
        AIRLINES_FILTER_CHECKBOXES("[data-testid='resultPage-AIRLINESFilter-content'] input"),
        AIRLINES_FILTER_LABELS("[data-testid='resultPage-AIRLINESFilter-content'] label"),
        DEPARTURE_ARRIVAL_RADIO("[data-testid='resultPage-departureArrivalFilter-%s%s-radio']"),  //the first is: departure, arrival - the second is the index starting from 0
        CLEAR_APPLY_FILTERS_BUTTON("[data-testid='filtersForm-%sFilters-button']"),   // 'reset' or 'apply'
        RESET_FILTERS_BUTTON("[data-testid='resultPage-filterHeader-allFilterResetButton-button']");

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

    // stops filter
    private final By nonStopFlightsFilterButtonLocator = By.cssSelector(Locators.NUMBER_OF_STOPS_FILTER.get("direct"));
    private final By maxOneStopFilterButtonLocator = By.cssSelector(Locators.NUMBER_OF_STOPS_FILTER.get("max1"));
    private final By allFlightsFilterButtonLocator = By.cssSelector(Locators.NUMBER_OF_STOPS_FILTER.get("all"));
    private final By resetStopFlightsFilterLocator = By.cssSelector(Locators.GENERIC_RESET_FILTER_BUTTON.get("MAX_STOPS"));
    // price filter
    private final By priceFilterLeftHandleLocator = By.cssSelector(Locators.GENERIC_SLIDER_FILTER_HANDLE.get("PRICEFilter", "0"));
    private final By priceFilterRightHandleLocator = By.cssSelector(Locators.GENERIC_SLIDER_FILTER_HANDLE.get("PRICEFilter", "1"));
    private final By priceFilterLowestPriceLocator = By.cssSelector(Locators.GENERIC_SLIDER_FILTER_LOWEST_VALUE.get("PRICEFilter"));
    private final By priceFilterHighestPriceLocator = By.cssSelector(Locators.GENERIC_SLIDER_FILTER_HIGHEST_VALUE.get("PRICEFilter"));
    private final By priceFilterResetButtonLocator = By.cssSelector(Locators.GENERIC_RESET_FILTER_BUTTON.get("PRICE"));
    // airlines filter
    private final By clearAllAirlinesButtonLocator = By.cssSelector(Locators.AIRLINES_FILTER_CLEAR_ALL.get());
    private final By resetAirlinesFilterLocator = By.cssSelector(Locators.GENERIC_RESET_FILTER_BUTTON.get("AIRLINES"));
    private final By selectAllAirlinesButtonLocator = By.cssSelector(Locators.AIRLINES_FILTER_SELECT_ALL.get());
    private final By airLineFilterCheckboxesLocator = By.cssSelector(Locators.AIRLINES_FILTER_CHECKBOXES.get());
    private final By airLineFilterLabelsLocator = By.cssSelector(Locators.AIRLINES_FILTER_LABELS.get());

    //departure - arrival filters
    private By departureFilterRadioButtonLocator(int index) {
        return By.cssSelector(Locators.DEPARTURE_ARRIVAL_RADIO.get("departure", index));
    }

    private By arrivalFilterRadioButtonLocator(int index) {
        return By.cssSelector(Locators.DEPARTURE_ARRIVAL_RADIO.get("arrival", index));
    }

    private By departureArrivalSliderLeftHandleLocator(int flightIndex) {
        return By.cssSelector(Locators.GENERIC_SLIDER_FILTER_HANDLE.get("departureArrival" + flightIndex + "Filter", "0"));
    }

    private By departureArrivalSliderRightHandleLocator(int flightIndex) {
        return By.cssSelector(Locators.GENERIC_SLIDER_FILTER_HANDLE.get("departureArrival" + flightIndex + "Filter", "1"));
    }

    private By departureArrivalSliderLowestValueLocator(int flightIndex) {
        return By.cssSelector(Locators.GENERIC_SLIDER_FILTER_LOWEST_VALUE.get("departureArrival" + flightIndex + "Filter"));
    }

    private By departureArrivalSliderHighestValueLocator(int flightIndex) {
        return By.cssSelector(Locators.GENERIC_SLIDER_FILTER_HIGHEST_VALUE.get("departureArrival" + flightIndex + "Filter"));
    }

    private By departureArrivalSliderResetLocator(int flightIndex) {
        return By.cssSelector(Locators.GENERIC_RESET_FILTER_BUTTON.get("departureArrival" + flightIndex));
    }

    // travel time filter
    private final By travelTimeSliderHandleLocator = By.cssSelector(Locators.GENERIC_SLIDER_FILTER_HANDLE.get("TRAVEL_TIMEFilter", "0"));
    private final By travelTimeSliderValueLocator = By.cssSelector(Locators.GENERIC_SLIDER_FILTER_LOWEST_VALUE.get("TRAVEL_TIMEFilter"));
    private final By travelTimeFilterResetButtonLocator = By.cssSelector(Locators.GENERIC_RESET_FILTER_BUTTON.get("TRAVEL_TIME"));

    // clear - select
    private final By clearAllFiltersButtonLocator = By.cssSelector(Locators.CLEAR_APPLY_FILTERS_BUTTON.get("reset"));
    private final By applyAllFiltersButtonLocator = By.cssSelector(Locators.CLEAR_APPLY_FILTERS_BUTTON.get("apply"));
    private final By resetFiltersButtonLocator = By.cssSelector(Locators.RESET_FILTERS_BUTTON.get());

    /*
     * --------------------------------------------------------------------------------------------------
     * ------------------------------------- PAGE OBJECT METHODS ----------------------------------------
     * --------------------------------------------------------------------------------------------------
     */

    /**
     * Clicks the nonstop flights filter button
     *
     * @return - this
     */
    public FiltersPO clickNonStopFlightsFilter() {
        waitUntilElementIsClickable(nonStopFlightsFilterButtonLocator).click();
        return this;
    }

    /**
     * Checks if the nonstop flights filter button is selected
     *
     * @return - true if it is selected, false otherwise
     */
    public boolean isNonStopFlightsSelected() {
        return waitUntilElementIsClickable(nonStopFlightsFilterButtonLocator).isSelected();
    }

    /**
     * Clicks the max one stop flights filter button
     *
     * @return - this
     */
    public FiltersPO clickMaxOneStopFlightsFilter() {
        waitUntilElementIsClickable(maxOneStopFilterButtonLocator).click();
        return this;
    }

    /**
     * Resets the flight stops filter
     *
     * @return - this
     */
    public FiltersPO resetFlightStopsFilter() {
        waitUntilElementIsClickable(resetStopFlightsFilterLocator).click();
        return this;
    }

    /**
     * Checks if the max one stop flights filter button is selected
     *
     * @return - true if it is selected, false otherwise
     */
    public boolean isMaxOneStopFlightsSelected() {
        return waitUntilElementIsClickable(maxOneStopFilterButtonLocator).isSelected();
    }

    /**
     * Clicks the all flights filter button
     *
     * @return - this
     */
    public FiltersPO clickAllFlightsFilter() {
        waitUntilElementIsClickable(allFlightsFilterButtonLocator).click();
        return this;
    }

    /**
     * Checks if the all flights filter button is selected
     *
     * @return - true if it is selected, false otherwise
     */
    public boolean isMaxAllFlightsSelected() {
        return waitUntilElementIsClickable(allFlightsFilterButtonLocator).isSelected();
    }

    /**
     * Returns the maximum flight stops, according to which stops filter is selected
     *
     * @return - the maximum flight stops
     */
    public int getFlightStopsMax() {
        int max = Integer.MAX_VALUE;
        if (isNonStopFlightsSelected()) {
            max = 0;
        } else if (isMaxOneStopFlightsSelected()) {
            max = 1;
        }
        return max;
    }

    /**
     * Slides the left handle of the price slider by the pixels given
     *
     * @param xPixels - how many pixels to move the slider handle (positive value to slide right, negative to slide left)
     * @return - this
     */
    public FiltersPO slidePriceLeftHandle(int xPixels) {
        slideElement(priceFilterLeftHandleLocator, xPixels, 0);
        return this;
    }

    /**
     * Slides the left handle of the price slider by the pixels given
     *
     * @param xPixels - how many pixels to move the slider handle (positive value to slide right, negative to slide left)
     * @return - this
     */
    public FiltersPO slidePriceRightHandle(int xPixels) {
        slideElement(priceFilterRightHandleLocator, xPixels, 0);
        return this;
    }

    /**
     * Returns the lowest value of the price slider
     *
     * @return - the lowest value
     */
    public Float getSliderPriceLowestValue() {
        return Float.valueOf(StringUtils.extractPriceFromString(waitUntilElementIsVisible(priceFilterLowestPriceLocator).getText()).replace(",", ""));
    }

    /**
     * Returns the highest value of the price slider
     *
     * @return - the highest value
     */
    public Float getSliderPriceHighestValue() {
        return Float.valueOf(StringUtils.extractPriceFromString(waitUntilElementIsVisible(priceFilterHighestPriceLocator).getText()).replace(",", ""));
    }

    /**
     * Resets the price slider filter
     *
     * @return - this
     */
    public FiltersPO resetSliderPrice() {
        waitUntilElementIsClickable(priceFilterResetButtonLocator).click();
        return this;
    }

    /**
     * Clicks the clear all airlines filter button
     *
     * @return - this
     */
    public FiltersPO clickClearAllAirlinesButton() {
        waitUntilElementIsClickable(clearAllAirlinesButtonLocator).click();
        return this;
    }

    /**
     * Clicks the select all airlines filter button
     *
     * @return - this
     */
    public FiltersPO clickSelectAllAirlinesButton() {
        waitUntilElementIsClickable(selectAllAirlinesButtonLocator).click();
        return this;
    }

    /**
     * Clicks the reset airlines filter button
     *
     * @return - this
     */
    public FiltersPO resetAirlinesFilterButton() {
        waitUntilElementIsClickable(resetAirlinesFilterLocator).click();
        return this;
    }

    /**
     * Returns all the available airlines (regardless if they are selected or not)
     *
     * @return - a list containing the airlines
     */
    public List<String> getAllAvailableAirlines() {
        List<String> availableAirlines = new ArrayList<>();
        waitUntilElementsAreVisible(airLineFilterLabelsLocator).forEach(airline -> availableAirlines.add(airline.getText()));
        return availableAirlines;
    }

    /**
     * Returns all the airline checkboxes
     *
     * @return - a list of the checkboxes
     */
    public List<WebElement> getAllAirlineCheckboxes() {
        return waitUntilElementsArePresent(airLineFilterCheckboxesLocator);
    }

    /**
     * Returns all the selected airlines
     *
     * @return - a list containing the selected airlines
     */
    public List<String> getAllSelectedAirlines() {
        List<String> availableAirlines = getAllAvailableAirlines();
        List<WebElement> airlinesCheckboxes = getAllAirlineCheckboxes();
        List<String> selectedAirlines = new ArrayList<>();
        IntStream.range(0, airlinesCheckboxes.size()).forEach(checkBoxIndex -> {
            if (airlinesCheckboxes.get(checkBoxIndex).isSelected()) {
                selectedAirlines.add(availableAirlines.get(checkBoxIndex));
            }
        });
        return selectedAirlines;
    }

    /**
     * Clicks an airline checkbox based on its index
     *
     * @param index - the index of the checkbox
     * @return - this
     */
    public FiltersPO clickAirlineCheckBoxWithIndex(int index) {
        getAllAirlineCheckboxes().get(index).click();
        return this;
    }


    /**
     * Clicks a departure radio button based on its index
     *
     * @param index - The index of the button
     * @return - this
     */
    public FiltersPO clickDepartureRadioButton(int index) {
        waitUntilElementsArePresent(departureFilterRadioButtonLocator(index));
        clickElementWithJs(departureFilterRadioButtonLocator(index));
        return this;
    }

    /**
     * Checks whether a departure radio button is checked
     *
     * @param index - the index of the button
     * @return - true if it is checked, false otherwise
     */
    public boolean isDepartureRadioButtonChecked(int index) {
        waitUntilElementsArePresent(departureFilterRadioButtonLocator(index));
        return isElementCheckedJs(departureFilterRadioButtonLocator(index));
    }

    /**
     * Clicks an arrival radio button based on its index
     *
     * @param index - The index of the button
     * @return - this
     */
    public FiltersPO clickArrivalRadioButton(int index) {
        waitUntilElementIsPresent(arrivalFilterRadioButtonLocator(index));
        clickElementWithJs(arrivalFilterRadioButtonLocator(index));
        return this;
    }

    /**
     * Checks whether a arrival radio button is checked
     *
     * @param index - the index of the button
     * @return - true if it is checked, false otherwise
     */
    public boolean isArrivalRadioButtonChecked(int index) {
        waitUntilElementsArePresent(arrivalFilterRadioButtonLocator(index));
        return isElementCheckedJs(arrivalFilterRadioButtonLocator(index));
    }

    /**
     * Slides the left handle of a departure/arrival slider by the pixels given
     *
     * @param index   - the index of the slider
     * @param xPixels - how many pixels to move the slider handle (positive value to slide right, negative to slide left)
     * @return - this
     */
    public FiltersPO slideDepartureArrivalLeftHandle(int index, int xPixels) {
        slideElement(departureArrivalSliderLeftHandleLocator(index), xPixels, 0);
        return this;
    }

    /**
     * Slides the left handle of a departure/arrival slider by the pixels given
     *
     * @param index   - the index of the slider
     * @param xPixels - how many pixels to move the slider handle (positive value to slide right, negative to slide left)
     * @return - this
     */
    public FiltersPO slideDepartureArrivalRightHandle(int index, int xPixels) {
        slideElement(departureArrivalSliderRightHandleLocator(index), xPixels, 0);
        return this;
    }

    /**
     * Returns the lowest value of a departure/arrival slider
     *
     * @param index - the index of the slider
     * @return - the value in {@link LocalTime}
     */
    public LocalTime getSliderDepartureArrivalLowestValue(int index) {
        return hoursMinutesToLocalTime(waitUntilElementIsVisible(departureArrivalSliderLowestValueLocator(index)).getText());
    }

    /**
     * Returns the highest value of a departure/arrival slider
     *
     * @param index - the index of the slider
     * @return - the value in {@link LocalTime}
     */
    public LocalTime getSliderDepartureArrivalHighestValue(int index) {
        return hoursMinutesToLocalTime(waitUntilElementIsVisible(departureArrivalSliderHighestValueLocator(index)).getText());
    }

    /**
     * Returns the minimum time of a departure / arrival filter according to a condition.
     * The condition should provide information about whether the departure or arrival radio button is checked.
     *
     * @param condition   - Whether the departure or arrival radio button is checked
     * @param flightIndex - The index of the departure / arrival filter
     * @return - the minimum time of the filter
     */
    public LocalTime getDepartureArrivalTimeMin(boolean condition, int flightIndex) {
        LocalTime departureArrivalTimeMin;
        if (condition) {
            departureArrivalTimeMin = getSliderDepartureArrivalLowestValue(flightIndex);
        } else {
            departureArrivalTimeMin = hoursMinutesToLocalTime("00:00");
        }
        return departureArrivalTimeMin;
    }

    /**
     * Returns the maximum time of a departure / arrival filter according to a condition.
     * The condition should provide information about whether the departure or arrival radio button is checked.
     *
     * @param condition   - Whether the departure or arrival radio button is checked
     * @param flightIndex - The index of the departure / arrival filter
     * @return - the maximum time of the filter
     */
    public LocalTime getDepartureArrivalTimeMax(boolean condition, int flightIndex) {
        LocalTime departureArrivalTimeMin;
        if (condition) {
            departureArrivalTimeMin = getSliderDepartureArrivalHighestValue(flightIndex);
        } else {
            departureArrivalTimeMin = hoursMinutesToLocalTime("23:59");
        }
        return departureArrivalTimeMin;
    }

    /**
     * Resets a departure/arrival slider filter
     *
     * @param index - the index of the slider
     * @return - this
     */
    public FiltersPO resetSliderDepartureArrival(int index) {
        waitUntilElementIsClickable(departureArrivalSliderResetLocator(index)).click();
        return this;
    }

    /**
     * Slides the handle of the travel trime slider by the pixels given
     *
     * @param xPixels - how many pixels to move the slider handle (positive value to slide right, negative to slide left)
     * @return - this
     */
    public FiltersPO slideTravelTimeHandle(int xPixels) {
        slideElement(travelTimeSliderHandleLocator, xPixels, 0);
        return this;
    }

    /**
     * Returns the value of the travel time slider
     *
     * @return - the travel time value in {@link Duration}
     */
    public Duration getSliderTravelTimeValue() {
        return TimeUtils.stringToDuration(waitUntilElementIsVisible(travelTimeSliderValueLocator).getText());
    }

    /**
     * Resets the travel time slider filter
     *
     * @return - this
     */
    public FiltersPO resetSliderTravelTime() {
        waitUntilElementIsClickable(travelTimeFilterResetButtonLocator).click();
        return this;
    }

    /**
     * Clicks the clear all filters button
     *
     * @return - this
     */
    public FiltersPO clickClearAllFiltersButton() {
        waitUntilElementIsClickable(clearAllFiltersButtonLocator).click();
        return this;
    }

    /**
     * Clicks the apply filters button
     *
     * @return - this
     */
    public FiltersPO clickApplyFiltersButton() {
        waitUntilElementIsClickable(applyAllFiltersButtonLocator).click();
        waitUntilElementIsInvisible(applyAllFiltersButtonLocator);
        return this;
    }

    /**
     * Clicks the reset filters button
     *
     * @return - this
     */
    public FiltersPO clickResetFiltersButton() {
        waitUntilElementIsClickable(resetFiltersButtonLocator).click();
        return this;
    }


}
