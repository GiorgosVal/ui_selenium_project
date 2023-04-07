package com.example.demo.actions;

import com.example.demo.factories.WebDriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Actions {

    int timeoutSeconds = 20;    //TODO maybe this can be dynamically passed in BaseTest setup

    protected WebElement waitUntilElementIsVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return WebDriverFactory.getDriver().findElement(locator);
    }

    protected WebElement waitUntilElementIsClickable(By locator) {
        waitUntilElementIsVisible(locator);
        scrollToElement(locator);
        WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        return WebDriverFactory.getDriver().findElement(locator);
    }

    protected List<WebElement> waitUntilElementsAreVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        return WebDriverFactory.getDriver().findElements(locator);
    }

    protected void scrollToElement(By locator) {
        ((JavascriptExecutor) WebDriverFactory.getDriver()).executeScript("document.querySelector(\"" + ((By.ByCssSelector) locator).getRemoteParameters().value() + "\").scrollIntoView({behavior: \"auto\", block: \"center\", inline: \"center\"});");
    }
}
