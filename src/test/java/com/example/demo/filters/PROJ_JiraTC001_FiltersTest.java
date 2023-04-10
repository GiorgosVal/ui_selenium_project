package com.example.demo.filters;

import com.example.demo.base.BaseTest;
import com.example.demo.business.FiltersBO;
import com.example.demo.dataloaders.FlightDetailsLoader;
import com.example.demo.dto.FlightDetails;
import com.example.demo.validation.Assertor;
import com.example.demo.validation.actual.ActualTrip;
import com.example.demo.validation.expected.ExpectedTrip;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class PROJ_JiraTC001_FiltersTest extends BaseTest {

    FlightDetails flightDetails;
    ExpectedTrip initialExpectedTrip;
    ExpectedTrip expectedTrip;
    List<ActualTrip> actualTrips;
    int initialFlightsCount;
    Assertor assertor;

    @BeforeClass
    void loadData() {
        actualTrips = new ArrayList<>();
        flightDetails = FlightDetailsLoader.loadMultiCityFlight();
    }

    @BeforeClass(dependsOnMethods = "loadData")
    void init() {
        homePageBO.searchFlight(flightDetails);
        initialFlightsCount = flightResultsBO.getFlightResultsCount();
        flightResultsBO.openFilters();
        initialExpectedTrip = filtersBO.getExpectedTrip(flightDetails);
    }

    @Test(description = "Verify max one stop filter, price, departure/arrival and travel time sliders work")
    void test1() {
        preconditionStep1();
        int actualFlightsCount = flightResultsBO.getFlightResultsCount();
        assertor.softAssertLessThan(actualFlightsCount, initialFlightsCount, assertor.failLess("Flights count", actualFlightsCount, initialFlightsCount))
                .softAssertTrips(expectedTrip, flightResultsBO.getActualTrips())
                .softAssertAll();
    }

    @Test(description = "Verify the clear all filters button works, and that the number of flights is not impacted", dependsOnMethods = "test1")
    void test2() {
        preconditionStep2();
        int actualFlightsCount = flightResultsBO.getFlightResultsCount();
        assertor
                .softAssertEquals(initialFlightsCount, actualFlightsCount, assertor.failMessage("Flights count", initialFlightsCount, actualFlightsCount))
                .softAssertGreaterThan(100, actualFlightsCount, assertor.failGreater("Flights count", 100, actualFlightsCount))
                .softAssertTrips(initialExpectedTrip, flightResultsBO.getActualTrips())
                .softAssertAll();
    }

    @Test(description = "Verify non stop and airlines filter work", dependsOnMethods = "test2")
    void test3() {
        preconditionStep3();
        assertor.softAssertTrips(expectedTrip, flightResultsBO.getActualTrips())
                .softAssertAll();
    }

    @Test(description = "Verify none flight is found after too many applied filters", dependsOnMethods = "test3")
    void test4() {
        preconditionStep4();
        int actualFlightsCount = flightResultsBO.getFlightResultsCount();
        assertor.softAssertEquals(0, actualFlightsCount, assertor.failMessage("Flights count", 0, actualFlightsCount))
                .softAssertTrue(flightResultsBO.areTripsEmpty(), assertor.failMessage("Flights list", "list to be empty", "a non empty list"))
                .softAssertAll();
    }

    @Test(description = "Verify the reset button of each specific filter works", dependsOnMethods = "test4")
    void test5() {
        preconditionStep5();
        int actualFlightsCount = flightResultsBO.getFlightResultsCount();
        assertor
                .softAssertEquals(initialFlightsCount, actualFlightsCount, assertor.failMessage("Flights count", initialFlightsCount, actualFlightsCount))
                .softAssertGreaterThan(100, actualFlightsCount, assertor.failGreater("Flights count", 100, actualFlightsCount))
                .softAssertTrips(initialExpectedTrip, flightResultsBO.getActualTrips())
                .softAssertAll();
    }

    private void preconditionStep1() {
        assertor = new Assertor();
        flightResultsBO.openFilters();
        filterResults();
        expectedTrip = filtersBO.getExpectedTrip(flightDetails);
        filtersBO.applyFilters();
    }

    private void preconditionStep2() {
        assertor = new Assertor();
        flightResultsBO.openFilters();
        filtersBO.clearAllFilters().applyFilters();
    }

    private void preconditionStep3() {
        assertor = new Assertor();
        flightResultsBO.openFilters();
        filtersBO.selectMaximumStops(FiltersBO.FlightStops.NON_STOP)
                .selectAirlinesUpToRange(8);
        expectedTrip = filtersBO.getExpectedTrip(flightDetails);
        filtersBO.applyFilters();
    }

    private void preconditionStep4() {
        assertor = new Assertor();
        flightResultsBO.openFilters();
        filterResults();
        filtersBO.selectAirlinesUpToRange(0).applyFilters();
    }

    private void preconditionStep5() {
        assertor = new Assertor();
        flightResultsBO.openFilters();
        filtersBO.resetSpecificFilter(FiltersBO.Filters.FLIGHT_STOPS, 0)
                .resetSpecificFilter(FiltersBO.Filters.PRICE, 0)
                .resetSpecificFilter(FiltersBO.Filters.AIRLINES, 0)
                .resetSpecificFilter(FiltersBO.Filters.DEPARTURE_ARRIVAL, 0)
                .resetSpecificFilter(FiltersBO.Filters.DEPARTURE_ARRIVAL, 1)
                .resetSpecificFilter(FiltersBO.Filters.TRAVEL_TIME, 0)
                .applyFilters();
    }

    private void filterResults() {
        filtersBO.selectMaximumStops(FiltersBO.FlightStops.MAX_ONE_STOP)
                .selectPriceRange(30, -300)
                .selectDeparture(0, 90, -180)
                .selectArrival(1, 180, -70)
                .selectTravelTime(-500);
    }


}
