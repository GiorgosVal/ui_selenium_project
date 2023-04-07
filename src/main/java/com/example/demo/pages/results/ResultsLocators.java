package com.example.demo.pages.results;

import org.openqa.selenium.By;

public class ResultsLocators {
    private enum Locators {

        FLIGHTS_RESULTS_COUNTER("[data-testid='resultPage-filters-text'] + span"),
        FILTERS_BUTTON("[data-testid='resultPage-toggleFiltersButton-button']"),
        NUMBER_OF_STOPS_FILTER("[data-testid='MAX_STOPS-%s']"), //'direct'    'max1'   'all'
        GENERIC_SLIDER_FILTER_HANDLE("[data-testid='resultPage-%s-content'] [data-testid='handle-%s']"),     //the 1st is one of: PRICEFilter, departureArrival0Filter, departureArrival1Filter, TRAVEL_TIMEFilter - the 2nd is 0 for the low handle, 1 for the high handle
        GENERIC_SLIDER_FILTER_LOWEST_VALUE("[data-testid='resultPage-%s-content'] .slider-tracks + div"),            //the 1st is one of: PRICEFilter, departureArrival0Filter, departureArrival1Filter, TRAVEL_TIMEFilter
        GENERIC_SLIDER_FILTER_HIGHEST_VALUE("[data-testid='resultPage-%s-content'] .slider-tracks + div + div"),     //the 1st is one of: PRICEFilter, departureArrival0Filter, departureArrival1Filter
        AIRLINES_FILTER_CLEAR_ALL("[data-testid='resultPage-AIRLINESFilter-content'] span"),
        AIRLINES_FILTER_SELECT_ALL("[data-testid='resultPage-AIRLINESFilter-content'] span + span"),
        AIRLINES_FILTER_CHECKBOXES("[data-testid='resultPage-AIRLINESFilter-content'] input"),
        DEPARTURE_ARRIVAL_RADIO("[data-testid='resultPage-departureArrivalFilter-%s%s-radio']"),  //the first is: departure, arrival - the second is the index starting from 0
        CLEAR_APPLY_FILTERS_BUTTON("[data-testid='filtersForm-%sFilters-button']")   // 'reset' or 'apply'
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

    protected final By flightResultsCounter = By.cssSelector(Locators.FLIGHTS_RESULTS_COUNTER.get());
    protected final By filtersToggleButton = By.cssSelector(Locators.FILTERS_BUTTON.get());
    // stops filter
    protected final By nonStopFlightsFilterButton = By.cssSelector(Locators.NUMBER_OF_STOPS_FILTER.get("direct"));
    protected final By maxOneStopFilterButton = By.cssSelector(Locators.NUMBER_OF_STOPS_FILTER.get("max1"));
    protected final By allFlightsFilterButton = By.cssSelector(Locators.NUMBER_OF_STOPS_FILTER.get("all"));
    // price filter
    protected final By priceFilterLeftHandle = By.cssSelector(Locators.GENERIC_SLIDER_FILTER_HANDLE.get("PRICEFilter", "0"));
    protected final By priceFilterRightHandle = By.cssSelector(Locators.GENERIC_SLIDER_FILTER_HANDLE.get("PRICEFilter", "1"));
    protected final By priceFilterLowestPrice = By.cssSelector(Locators.GENERIC_SLIDER_FILTER_LOWEST_VALUE.get("PRICEFilter"));
    protected final By priceFilterHighestPrice = By.cssSelector(Locators.GENERIC_SLIDER_FILTER_HIGHEST_VALUE.get("PRICEFilter"));
    // airlines filter
    protected final By clearAllAirlinesButton = By.cssSelector(Locators.AIRLINES_FILTER_CLEAR_ALL.get());
    protected final By selectAllAirlinesButton = By.cssSelector(Locators.AIRLINES_FILTER_SELECT_ALL.get());
    protected final By airLineFilterCheckboxes = By.cssSelector(Locators.AIRLINES_FILTER_CHECKBOXES.get());

    //departure - arrival filters
    protected final By departureFilterRadioButton(int index) {
        return By.cssSelector(Locators.DEPARTURE_ARRIVAL_RADIO.get("departure", index));
    }

    protected final By arrivalFilterRadioButton(int index) {
        return By.cssSelector(Locators.DEPARTURE_ARRIVAL_RADIO.get("arrival", index));
    }

    protected final By departureArrivalSliderLeftHandle(int flightIndex) {
        return By.cssSelector(Locators.GENERIC_SLIDER_FILTER_HANDLE.get("departureArrival" + flightIndex + "Filter", "0"));
    }

    protected final By departureArrivalSliderRightHandle(int flightIndex) {
        return By.cssSelector(Locators.GENERIC_SLIDER_FILTER_HANDLE.get("departureArrival" + flightIndex + "Filter", "1"));
    }

    protected final By departureArrivalSliderLowestValue(int flightIndex) {
        return By.cssSelector(Locators.GENERIC_SLIDER_FILTER_LOWEST_VALUE.get("departureArrival" + flightIndex + "Filter"));
    }

    protected final By departureArrivalSliderHighestValue(int flightIndex) {
        return By.cssSelector(Locators.GENERIC_SLIDER_FILTER_HIGHEST_VALUE.get("departureArrival" + flightIndex + "Filter"));
    }

    // travel time filter
    protected final By travelTimeSliderHandle = By.cssSelector(Locators.GENERIC_SLIDER_FILTER_HANDLE.get("TRAVEL_TIMEFilter", "0"));
    protected final By travelTimeSliderValue = By.cssSelector(Locators.GENERIC_SLIDER_FILTER_LOWEST_VALUE.get("TRAVEL_TIMEFilter"));

    // clear - select
    protected final By clearAllFiltersButton = By.cssSelector(Locators.CLEAR_APPLY_FILTERS_BUTTON.get("reset"));
    protected final By applyAllFiltersButton = By.cssSelector(Locators.CLEAR_APPLY_FILTERS_BUTTON.get("apply"));

}
