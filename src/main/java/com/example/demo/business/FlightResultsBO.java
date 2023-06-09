package com.example.demo.business;

import com.example.demo.pages.FlightResultsPO;
import com.example.demo.validation.actual.ActualFlight;
import com.example.demo.validation.actual.ActualTrip;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class FlightResultsBO extends BaseBO {

    private final FlightResultsPO flightResultsPO;

    public FlightResultsBO() {
        flightResultsPO = new FlightResultsPO();
    }

    /**
     * Returns the number of flight results
     *
     * @return - the number of flights
     */
    public int getFlightResultsCount() {
        return flightResultsPO.getFlightsResultsCount();
    }

    /**
     * Returns the applied filters labels
     *
     * @return - a list of the applied filters
     */
    public List<String> getAppliedFilters() {
        return flightResultsPO.getAppliedFilters();
    }

    /**
     * Returns the actual trips. If none trip is found returns an empty list
     *
     * @return - the actual trips, or empty
     */
    public List<ActualTrip> getActualTrips() {
        List<ActualTrip> actualTrips = new ArrayList<>();
        try {
            List<WebElement> trips = flightResultsPO.getAllTrips();
            trips.forEach(trip -> {
                List<WebElement> flights = flightResultsPO.getTripFlights(trip);
                List<ActualFlight> actualFlights = new ArrayList<>();
                flights.forEach(flight ->
                        actualFlights.add(ActualFlight.builder()
                                .airlineName(flightResultsPO.getFlightAirlineName(flight))
                                .departureTime(flightResultsPO.getFlightDepartureTime(flight))
                                .arrivalTime(flightResultsPO.getFlightArrivalTime(flight))
                                .flightDuration(flightResultsPO.getFlightDurationTime(flight))
                                .flightStops(flightResultsPO.getFlightStops(flight))
                                .build()));
                actualTrips.add(ActualTrip.builder()
                        .standardPrice(flightResultsPO.getTripStandardPrice(trip))
                        .actualFlightList(actualFlights)
                        .build());
            });
        } catch (Exception e) {
        }
        return actualTrips;
    }

    /**
     * Checks if the trips lists is empty
     *
     * @return - true if the list is empty, false otherwise
     */
    public boolean areTripsEmpty() {
        return flightResultsPO.areTripsEmpty();
    }

    /**
     * Opens the filters
     *
     * @return - this
     */
    public FlightResultsBO openFilters() {
        if (!flightResultsPO.areFiltersDisplayed()) {
            flightResultsPO.clickFiltersToggle();
            flightResultsPO.waitFiltersToOpen();
        }
        return this;
    }

    /**
     * Closes the filters
     *
     * @return this
     */
    public FlightResultsBO closeFilters() {
        if (flightResultsPO.areFiltersDisplayed()) {
            flightResultsPO.clickFiltersToggle();
            flightResultsPO.waitFiltersToClose();
        }
        return this;
    }


}
