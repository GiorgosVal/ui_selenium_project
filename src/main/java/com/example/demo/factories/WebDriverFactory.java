package com.example.demo.factories;

import org.openqa.selenium.WebDriver;

/**
 * References
 * https://www.linkedin.com/pulse/selenium-parallel-testing-using-java-threadlocal-testng-shargo/
 * https://subscription.packtpub.com/book/web-development/9781788473576/1/ch01lvl1sec11/the-singleton-driver-class
 * http://www.eliasnogueira.com/the-best-way-to-create-browser-instances-using-the-factory-pattern-java-and-selenium-webdriver/
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
