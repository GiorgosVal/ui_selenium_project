package com.example.demo.base;

import com.example.demo.business.HomePageBO;
import com.example.demo.factories.BrowserFactory;
import com.example.demo.factories.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTest {

    public HomePageBO homePageBO;

    @Parameters({"url", "browser", "local", "timeout", "headless"})
    @BeforeClass
    public void setup(String url, String browser, boolean local, long timeout, boolean headless) {
        WebDriver webDriver = BrowserFactory.setup(browser, local, timeout, headless);
        webDriver.get(url);
        WebDriverFactory.setDriver(webDriver);

        homePageBO = new HomePageBO();
    }

    @AfterClass
    public void quit() {
        WebDriverFactory.quit();
    }
}
