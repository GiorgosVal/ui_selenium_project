package com.example.demo.factories;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class BrowserFactory {

    public static WebDriver setup(String browser, boolean local, long timeout, boolean headless) {
        WebDriver webDriver = null;
        if ("chrome".equalsIgnoreCase(browser) && local) {
            webDriver = setUpChromeLocal(timeout, headless);
        }
        return webDriver;
    }

    private static WebDriver setUpChromeLocal(long timeout, boolean headless) {
        // handle web driver installation
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments(headless ? "--headless": "--headed");
        options.addArguments("--remote-allow-origins=*");

        WebDriver webDriver = new ChromeDriver(options);
        webDriver.manage().window().maximize();

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));

        return webDriver;
    }
}
