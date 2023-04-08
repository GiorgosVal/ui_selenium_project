package com.example.demo.filters;

import com.example.demo.base.BaseTest;
import com.example.demo.dataloaders.FlightDetailsLoader;
import com.example.demo.dto.FlightDetails;
import com.example.demo.enums.QuickSortButton;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class FiltersTest extends BaseTest {

    FlightDetails flightDetails;

    @BeforeClass
    void loadData() {
        flightDetails = FlightDetailsLoader.loadMultiCityFlight();
    }

    @BeforeClass(dependsOnMethods = "loadData")
    void init() {
        homePageBO.searchFlight(flightDetails);
    }

    @Test
    void contextLoads() {
        System.out.println(flightResultsPO.getFlightsResultsCount());
        flightResultsPO.clickQuickSortButton(QuickSortButton.CHEAPEST);
        List<WebElement> trips = flightResultsPO.getAllTrips();
        Map<Integer, List<WebElement>> flightsPerType = flightResultsPO.tripsToFlightTypesMap(trips);
        System.out.println(trips);


    }


}
