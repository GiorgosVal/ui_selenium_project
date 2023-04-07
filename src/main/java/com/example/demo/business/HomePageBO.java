package com.example.demo.business;

import com.example.demo.enums.CabinClass;
import com.example.demo.dto.FlightDetails;
import com.example.demo.pages.homepage.HomePagePO;
import com.example.demo.enums.PassengerType;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

public class HomePageBO extends BaseBO {

    HomePagePO homePagePO;

    public HomePageBO() {
        homePagePO = new HomePagePO();
    }

    public HomePageBO searchOneWayReturnFlight(FlightDetails flightDetails) {
        homePagePO
                .clickAcceptCookies()
                .clickReturnRadioButton()
                .writeFromAirportInput("Athens")
                .selectElementFromDropdown("Athens, Greece")
                .writeToAirportInput("Istanbul")
                .selectElementFromDropdown("Istanbul (All airports), Turkey")
                .selectDepartureDate(LocalDateTime.now().plusDays(40))
                .selectReturnDate(LocalDateTime.now().plusDays(90))
                .clickPassengersDropdown()
                .increasePassengerSeats(PassengerType.ADULTS)
                .increasePassengerSeats(PassengerType.CHILDREN)
                .clickCabinClassDropdown()
                .selectElementFromDropdown(CabinClass.FIRST.getCabinClass())
                .clickSearchFlightsButton();
        return this;
    }

    public HomePageBO searchFlight(FlightDetails flightDetails) {
        homePagePO.clickAcceptCookies();
        switch (flightDetails.getFlightType()) {
            case RETURN:
                setupReturnFlight(flightDetails);
                break;
            case ONE_WAY:
                setupOneWayFlight(flightDetails);
                break;
            case MULTI_CITY:
                setupMultiCityFlight(flightDetails);
                break;
        }
        return this;
    }

    private void setupReturnFlight(FlightDetails flightDetails) {
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
                .selectElementFromDropdown(flightDetails.getCabinClass().getCabinClass())
                .clickSearchFlightsButton();
        if (flightDetails.isNonStopFlight()) {
            homePagePO.clickNonStopFlightsCheckbox();
        }
    }

    private void setupOneWayFlight(FlightDetails flightDetails) {
        homePagePO.clickOneWayRadioButton()
                .writeFromAirportInput(flightDetails.getSimpleFlights().get(0).getFromInput())
                .selectElementFromDropdown(flightDetails.getSimpleFlights().get(0).getFromAirport())
                .writeToAirportInput(flightDetails.getSimpleFlights().get(0).getToInput())
                .selectElementFromDropdown(flightDetails.getSimpleFlights().get(0).getToAirport())
                .selectDepartureDate(flightDetails.getSimpleFlights().get(0).getDepartureDate())
                .clickPassengersDropdown();
        flightDetails.getPassengerTypeList().forEach(passengerType -> homePagePO.increasePassengerSeats(passengerType));
        homePagePO.clickCabinClassDropdown()
                .selectElementFromDropdown(flightDetails.getCabinClass().getCabinClass())
                .clickSearchFlightsButton();
        if (flightDetails.isNonStopFlight()) {
            homePagePO.clickNonStopFlightsCheckbox();
        }
    }

    private void setupMultiCityFlight(FlightDetails flightDetails) {
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
                .selectElementFromDropdown(flightDetails.getCabinClass().getCabinClass())
                .clickSearchFlightsButton();
        if (flightDetails.isNonStopFlight()) {
            homePagePO.clickNonStopFlightsCheckbox();
        }
    }

}
