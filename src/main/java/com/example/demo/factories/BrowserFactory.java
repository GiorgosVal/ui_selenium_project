package com.example.demo.factories;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

public class BrowserFactory {

    private BrowserFactory() {
    }

    /**
     * Returns a {@link WebDriver}
     *
     * @param browser  - the browser type (e.g. chrome, firefox, etc)
     * @param timeout  - the implicit wait of the driver
     * @param headless - whether to run headless or not
     * @return - the web driver
     */
    public static WebDriver setup(String browser, long timeout, boolean headless) {
        WebDriver webDriver = null;
        if ("chrome".equalsIgnoreCase(browser)) {
            webDriver = setUpChrome(timeout, headless);
        } else if ("firefox".equalsIgnoreCase(browser)) {
            webDriver = setUpFirefox(timeout, headless);
        }
        return webDriver;
    }

    /**
     * Returns a local Chrome {@link WebDriver}
     *
     * @param timeout  - the implicit wait of the driver
     * @param headless - whether to run headless or not
     * @return - the Chrome web driver
     */
    private static WebDriver setUpChrome(long timeout, boolean headless) {
        // handle web driver installation
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments(headless ? "--headless" : "--headed");
        options.addArguments("--remote-allow-origins=*");
        WebDriver webDriver = new ChromeDriver(options);
        // set window size for headless mode to avoid not finding elements
        // https://stackoverflow.com/a/54801726
        if (headless) {
            webDriver.manage().window().setSize(new Dimension(1200, 1100));
        } else {
            webDriver.manage().window().maximize();
        }
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));

        return webDriver;
    }

    /**
     * Returns a local Firefox {@link WebDriver}
     *
     * @param timeout  - the implicit wait of the driver
     * @param headless - whether to run headless or not
     * @return - the Firefox web driver
     */
    private static WebDriver setUpFirefox(long timeout, boolean headless) {
        // handle web driver installation
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("--headless");
        }

        WebDriver webDriver = new FirefoxDriver(options);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        return webDriver;
    }
}
