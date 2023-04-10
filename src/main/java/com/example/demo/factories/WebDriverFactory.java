package com.example.demo.factories;

import org.openqa.selenium.WebDriver;

public class WebDriverFactory {

    private WebDriverFactory() {
    }

    /**
     * Returns a thread-safe {@link WebDriver}, to enable parallelization of tests. With the use of the {@link ThreadLocal} class
     * a generic/ThreadLocal type of object is created, which can only be read and written (via its get/set method) by the same thread,
     * so if two threads are trying to read and write a ThreadLocal object concurrently, one thread would not see the modification
     * of the ThreadLocal object done by the other thread, thus, making the thread-local object thread-safe.
     * <p>
     * References:
     * https://www.linkedin.com/pulse/selenium-parallel-testing-using-java-threadlocal-testng-shargo/
     * https://subscription.packtpub.com/book/web-development/9781788473576/1/ch01lvl1sec11/the-singleton-driver-class
     * http://www.eliasnogueira.com/the-best-way-to-create-browser-instances-using-the-factory-pattern-java-and-selenium-webdriver/
     */
    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();


    /**
     * Sets a thread-safe {@link WebDriver}
     */
    public static void setDriver(WebDriver webDriver) {
        threadLocalDriver.set(webDriver);
    }

    /**
     * Gets a thread-safe {@link WebDriver}
     *
     * @return - a {@link WebDriver}
     */
    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    /**
     * Quites the web driver and removes the thread-local value for the current thread
     */
    public static void quit() {
        getDriver().quit();
        threadLocalDriver.remove();
    }
}
