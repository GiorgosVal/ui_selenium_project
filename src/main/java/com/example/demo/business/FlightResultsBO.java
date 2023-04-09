package com.example.demo.business;

import com.example.demo.pages.FlightResultsPO;
import com.example.demo.validation.actual.Flight;
import com.example.demo.validation.actual.Trip;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class FlightResultsBO extends BaseBO {

    FlightResultsPO flightResultsPO;

    public FlightResultsBO() {
        flightResultsPO = new FlightResultsPO();
    }

    public List<Trip> getActualTrips() {
        List<WebElement> trips = flightResultsPO.getAllTrips();
        List<Trip> actualTrips = new ArrayList<>();
        trips.forEach(trip -> {
            List<WebElement> flights = flightResultsPO.getTripFlights(trip);
            List<Flight> actualFlights = new ArrayList<>();
            flights.forEach(flight -> {
                actualFlights.add(Flight.builder()
                        .airlineName(flightResultsPO.getFlightAirlineName(flight))
                        .departureTime(flightResultsPO.getFlightDepartureTime(flight))
                        .arrivalTime(flightResultsPO.getFlightArrivalTime(flight))
                        .flightDuration(flightResultsPO.getFlightDurationTime(flight))
                        .flightStops(flightResultsPO.getFlightStops(flight))
                        .build());
            });
            actualTrips.add(Trip.builder()
                    .standardPrice(flightResultsPO.getTripStandardPrice(trip))
                    //.flexiblePrice(flightResultsPO.getTripFlexiblePrice(trip))
                    .actualFlightList(actualFlights)
                    .build());
        });
        return actualTrips;
    }


}
