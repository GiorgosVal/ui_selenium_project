package com.example.demo.base;

import com.example.demo.business.FiltersBO;
import com.example.demo.business.FlightResultsBO;
import com.example.demo.business.HomePageBO;
import com.example.demo.factories.BrowserFactory;
import com.example.demo.factories.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTest {

    public HomePageBO homePageBO;
    public FlightResultsBO flightResultsBO;
    public FiltersBO filtersBO;

    @Parameters({"url", "browser", "timeout", "headless"})
    @BeforeClass
    public void setup(String url, String browser, long timeout, boolean headless) {
        WebDriver webDriver = BrowserFactory.setup(browser, timeout, headless);
        webDriver.get(url);
        WebDriverFactory.setDriver(webDriver);

        homePageBO = new HomePageBO();
        flightResultsBO = new FlightResultsBO();
        filtersBO = new FiltersBO();
    }

    @AfterClass
    public void quit() {
        WebDriverFactory.quit();
    }
}
