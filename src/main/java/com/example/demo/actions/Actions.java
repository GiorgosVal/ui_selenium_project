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

    protected List<WebElement> waitUntilElementsAreVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        return WebDriverFactory.getDriver().findElements(locator);
    }

    protected WebElement waitUntilElementIsClickable(By locator) {
        waitUntilElementIsVisible(locator);
        scrollToElement(locator);
        WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        return WebDriverFactory.getDriver().findElement(locator);   //TODO return the element found by wait
    }

    protected List<WebElement> waitUntilElementsAreClickable(By locator) {
        List<WebElement> webElements = waitUntilElementsAreVisible(locator);
        scrollToElement(locator);
        WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
        webElements.forEach(webElement -> waitUntilElementIsClickable(locator));
        return webElements;
    }

    /**
     * Scrolls the window to the element specified by the locator.
     * <p>
     * Note: Works only with {@link org.openqa.selenium.By.ByCssSelector} and {@link org.openqa.selenium.By.ByXPath} locators
     *
     * @param locator - the locator to use to scroll to an element
     */
    protected void scrollToElement(By locator) {
        String js = "";
        if (locator.getClass().equals(By.ByCssSelector.class)) {
            js = "document.querySelector(\"" + ((By.ByCssSelector) locator).getRemoteParameters().value() + "\").scrollIntoView({behavior: \"auto\", block: \"center\", inline: \"center\"});";
        } else if (locator.getClass().equals(By.ByXPath.class)) {
            js = "document.evaluate(\"" + ((By.ByXPath) locator).getRemoteParameters().value() + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.scrollIntoView({behavior: \"auto\", block: \"center\", inline: \"center\"});";
        }
        ((JavascriptExecutor) WebDriverFactory.getDriver()).executeScript(js);
    }
}
