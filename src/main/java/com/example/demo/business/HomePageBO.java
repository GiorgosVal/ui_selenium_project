package com.example.demo.business;

import com.example.demo.dto.FlightDetails;
import com.example.demo.pages.HomePagePO;

import java.util.stream.IntStream;

public class HomePageBO extends BaseBO {

    HomePagePO homePagePO;

    public HomePageBO() {
        homePagePO = new HomePagePO();
    }

    /**
     * Searches for a flight based on the {@link FlightDetails} object given
     *
     * @param flightDetails - the DTO object that holds the information about the flight to search
     * @return - this
     */
    public HomePageBO searchFlight(FlightDetails flightDetails) {
        homePagePO.clickAcceptCookies();
        switch (flightDetails.getFlightType()) {
            case RETURN:
                searchReturnFlight(flightDetails);
                break;
            case ONE_WAY:
                searchOneWayFlight(flightDetails);
                break;
            case MULTI_CITY:
                searchMultiCityFlight(flightDetails);
                break;
        }
        return this;
    }

    /**
     * Flow to search for a return flight
     *
     * @param flightDetails - the DTO object that holds the information about the flight to search
     */
    private void searchReturnFlight(FlightDetails flightDetails) {
        homePagePO.clickReturnRadioButton()
                .writeFromAirportInput(flightDetails.getSimpleFlights().get(0).getFromInput())
                .selectElementFromDropdown(flightDetails.getSimpleFlights().get(0).getFromAirport())
                .writeToAirportInput(flightDetails.getSimpleFlights().get(0).getToInput())
                .selectElementFromDropdown(flightDetails.getSimpleFlights().get(0).getToAirport())
                .selectDepartureDate(flightDetails.getSimpleFlights().get(0).getDepartureDate())
                .selectReturnDate(flightDetails.getReturnDate())
                .clickPassengersDropdown();
        flightDetails.getPassengerTypeList().forEach(passengerType -> homePagePO.increasePassengerSeats(passengerType));
        homePagePO.clickCabinClassDropdown()
                .selectElementFromDropdown(flightDetails.getCabinClass().getCabin())
                .clickSearchFlightsButton();
        if (flightDetails.isNonStopFlight()) {
            homePagePO.clickNonStopFlightsCheckbox();
        }
    }

    /**
     * Flow to search for a one way flight
     *
     * @param flightDetails - the DTO object that holds the information about the flight to search
     */
    private void searchOneWayFlight(FlightDetails flightDetails) {
        homePagePO.clickOneWayRadioButton()
                .writeFromAirportInput(flightDetails.getSimpleFlights().get(0).getFromInput())
                .selectElementFromDropdown(flightDetails.getSimpleFlights().get(0).getFromAirport())
                .writeToAirportInput(flightDetails.getSimpleFlights().get(0).getToInput())
                .selectElementFromDropdown(flightDetails.getSimpleFlights().get(0).getToAirport())
                .selectDepartureDate(flightDetails.getSimpleFlights().get(0).getDepartureDate())
                .clickPassengersDropdown();
        flightDetails.getPassengerTypeList().forEach(passengerType -> homePagePO.increasePassengerSeats(passengerType));
        homePagePO.clickCabinClassDropdown()
                .selectElementFromDropdown(flightDetails.getCabinClass().getCabin())
                .clickSearchFlightsButton();
        if (flightDetails.isNonStopFlight()) {
            homePagePO.clickNonStopFlightsCheckbox();
        }
    }

    /**
     * Flow to search for a multi city flight
     *
     * @param flightDetails - the DTO object that holds the information about the flight to search
     */
    private void searchMultiCityFlight(FlightDetails flightDetails) {
        homePagePO.clickMultiCityRadioButton();
        IntStream.range(0, flightDetails.getSimpleFlights().size()).forEach(flightIndex -> {
            if (flightIndex > 1) {
                homePagePO.clickAddTripButton();
            }
            homePagePO.writeFromAirportInputMultiBound(flightIndex, flightDetails.getSimpleFlights().get(flightIndex).getFromInput())
                    .selectElementFromDropdown(flightDetails.getSimpleFlights().get(flightIndex).getFromAirport())
                    .writeToAirportInputMultiBound(flightIndex, flightDetails.getSimpleFlights().get(flightIndex).getToInput())
                    .selectElementFromDropdown(flightDetails.getSimpleFlights().get(flightIndex).getToAirport())
                    .selectDepartureDateMulti(flightIndex, flightDetails.getSimpleFlights().get(flightIndex).getDepartureDate());
        });
        homePagePO
                .clickPassengersDropdown();
        flightDetails.getPassengerTypeList().forEach(passengerType -> homePagePO.increasePassengerSeats(passengerType));
        homePagePO.clickCabinClassDropdown()
                .selectElementFromDropdown(flightDetails.getCabinClass().getCabin())
                .clickSearchFlightsButton();
        if (flightDetails.isNonStopFlight()) {
            homePagePO.clickNonStopFlightsCheckbox();
        }
    }

}
