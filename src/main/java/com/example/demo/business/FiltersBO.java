package com.example.demo.business;

import com.example.demo.dto.FlightDetails;
import com.example.demo.pages.FiltersPO;
import com.example.demo.pages.FlightResultsPO;
import com.example.demo.validation.expected.ExpectedFlight;
import com.example.demo.validation.expected.ExpectedTrip;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class FiltersBO extends BaseBO {

    private final FiltersPO filtersPO;
    private final FlightResultsPO flightResultsPO;

    public FiltersBO() {
        filtersPO = new FiltersPO();
        flightResultsPO = new FlightResultsPO();
    }

    public enum FlightStops {
        NON_STOP, MAX_ONE_STOP, ALL
    }

    public enum Filters {
        FLIGHT_STOPS, PRICE, AIRLINES, DEPARTURE_ARRIVAL, TRAVEL_TIME
    }

    /**
     * Clicks the flight stops filter given
     *
     * @param flightStops - Which filter to click
     * @return - this
     */
    public FiltersBO selectMaximumStops(FlightStops flightStops) {
        switch (flightStops) {
            case NON_STOP:
                filtersPO.clickNonStopFlightsFilter();
                break;
            case MAX_ONE_STOP:
                filtersPO.clickMaxOneStopFlightsFilter();
                break;
            case ALL:
                filtersPO.clickAllFlightsFilter();
                break;
        }
        return this;
    }

    /**
     * Slides the price filter by the pixels given.
     * <p>
     * Positive pixel values will slide the handles to the right, negative will slide to the left
     *
     * @param minX - How many pixels to slide the left handle
     * @param maxX - How many pixels to slide the right handle
     * @return - this
     */
    public FiltersBO selectPriceRange(int minX, int maxX) {
        filtersPO.slidePriceLeftHandle(minX);
        filtersPO.slidePriceRightHandle(maxX);
        return this;
    }

    /**
     * Selects the airlines with index starting from 0 to the index given (inclusive)
     *
     * @param index - Up to which index to select an airline
     * @return - this
     */
    public FiltersBO selectAirlinesUpToRange(int index) {
        filtersPO.clickClearAllAirlinesButton();
        IntStream.range(0, index + 1).forEach(filtersPO::clickAirlineCheckBoxWithIndex);
        return this;
    }

    /**
     * Slides a departure filter by the pixels given.
     * <p>
     * Positive pixel values will slide the handles to the right, negative will slide to the left
     *
     * @param index - The index of the slider
     * @param minX  - How many pixels to slide the left handle
     * @param maxX  - How many pixels to slide the right handle
     * @return - this
     */
    public FiltersBO selectDeparture(int index, int minX, int maxX) {
        filtersPO.clickDepartureRadioButton(index);
        filtersPO.slideDepartureArrivalLeftHandle(index, minX);
        filtersPO.slideDepartureArrivalRightHandle(index, maxX);
        return this;
    }

    /**
     * Slides a arrival filter by the pixels given.
     * <p>
     * Positive pixel values will slide the handles to the right, negative will slide to the left
     *
     * @param index - The index of the slider
     * @param minX  - How many pixels to slide the left handle
     * @param maxX  - How many pixels to slide the right handle
     * @return - this
     */
    public FiltersBO selectArrival(int index, int minX, int maxX) {
        filtersPO.clickArrivalRadioButton(index);
        filtersPO.slideDepartureArrivalLeftHandle(index, minX);
        filtersPO.slideDepartureArrivalRightHandle(index, maxX);
        return this;
    }

    /**
     * Slides the travel time filter by the pixels given.
     * <p>
     * Positive pixel values will slide the handle to the right, negative will slide to the left
     *
     * @param xPixels - How many pixels to slide the handle
     * @return - this
     */
    public FiltersBO selectTravelTime(int xPixels) {
        filtersPO.slideTravelTimeHandle(xPixels);
        return this;
    }

    /**
     * Applies the filters
     *
     * @return - this
     */
    public FiltersBO applyFilters() {
        filtersPO.clickApplyFiltersButton();
        flightResultsPO.waitFiltersToClose();
        return this;
    }

    /**
     * Clears the filters
     *
     * @return - this
     */
    public FiltersBO clearAllFilters() {
        filtersPO.clickClearAllFiltersButton();
        return this;
    }

    /**
     * Clears the specific filter given. The index is needed only when the filter is {@link Filters#DEPARTURE_ARRIVAL}
     *
     * @param filters - The filter to clear
     * @param index   - The index of a {@link Filters#DEPARTURE_ARRIVAL} filter
     * @return - this
     */
    public FiltersBO resetSpecificFilter(Filters filters, int index) {
        switch (filters) {
            case FLIGHT_STOPS:
                filtersPO.resetFlightStopsFilter();
                break;
            case PRICE:
                filtersPO.resetSliderPrice();
                break;
            case AIRLINES:
                filtersPO.resetAirlinesFilterButton();
                break;
            case DEPARTURE_ARRIVAL:
                filtersPO.resetSliderDepartureArrival(index);
                break;
            case TRAVEL_TIME:
                filtersPO.resetSliderTravelTime();
                break;
        }
        return this;
    }


    /**
     * Returns the expected trip according to the state of the flight filters
     *
     * @param flightDetails - The flight details, which is used to determine the number of flights
     * @return -  the expected trip
     */
    public ExpectedTrip getExpectedTrip(FlightDetails flightDetails) {
        return ExpectedTrip.builder()
                .priceMin(filtersPO.getSliderPriceLowestValue())
                .priceMax(filtersPO.getSliderPriceHighestValue())
                .expectedFlightList(getExpectedFlights(flightDetails))
                .build();
    }

    /**
     * Returns a list of expected flights according to the state of the flight filters
     *
     * @param flightDetails - The flight details, which is used to determine the number of flights
     * @return - a list of expected flights
     */
    private List<ExpectedFlight> getExpectedFlights(FlightDetails flightDetails) {
        List<ExpectedFlight> expectedFlightList = new ArrayList<>();
        int flightStopsMax = filtersPO.getFlightStopsMax();
        List<String> airlineNames = filtersPO.getAllAvailableAirlines();
        Duration maxDuration = filtersPO.getSliderTravelTimeValue();
        IntStream.range(0, flightDetails.getSimpleFlights().size()).forEach(flightIndex ->
                expectedFlightList.add(ExpectedFlight.builder()
                        .flightStopsMax(flightStopsMax)
                        .airlineNames(airlineNames)
                        .departureTimeMin(filtersPO.getDepartureArrivalTimeMin(filtersPO.isDepartureRadioButtonChecked(flightIndex), flightIndex))
                        .departureTimeMax(filtersPO.getDepartureArrivalTimeMax(filtersPO.isDepartureRadioButtonChecked(flightIndex), flightIndex))
                        .arrivalTimeMin(filtersPO.getDepartureArrivalTimeMin(filtersPO.isArrivalRadioButtonChecked(flightIndex), flightIndex))
                        .arrivalTimeMax(filtersPO.getDepartureArrivalTimeMax(filtersPO.isArrivalRadioButtonChecked(flightIndex), flightIndex))
                        .durationMax(maxDuration)
                        .build()));
        return expectedFlightList;
    }


}
