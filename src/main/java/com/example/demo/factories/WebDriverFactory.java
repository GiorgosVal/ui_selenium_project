package com.example.demo.factories;

import org.openqa.selenium.WebDriver;

/**
 * Reference https://www.linkedin.com/pulse/selenium-parallel-testing-using-java-threadlocal-testng-shargo/
 */
public class WebDriverFactory {

    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    public static void setDriver(WebDriver webDriver) {
        threadLocalDriver.set(webDriver);
    }

    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    public static void quit() {
        getDriver().quit();
        threadLocalDriver.remove();
    }
}
