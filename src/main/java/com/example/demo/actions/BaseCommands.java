package com.example.demo.actions;

import com.example.demo.factories.WebDriverFactory;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@Slf4j
public class BaseCommands {

    int timeoutSeconds = 20;    //TODO maybe this can be dynamically passed in BaseTest setup
    int retryMaxAttempts = 2;

    protected WebElement waitUntilElementIsVisible(By locator) {
        //waitUntilDocumentIsReady();
        WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return WebDriverFactory.getDriver().findElement(locator);
    }

    protected void waitUntilElementIsInvisible(By locator) {
        WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected List<WebElement> waitUntilElementsAreVisible(By locator) {
        //waitUntilDocumentIsReady();
        WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        return WebDriverFactory.getDriver().findElements(locator);
    }

    protected WebElement waitUntilElementIsPresent(By locator) {
        //waitUntilDocumentIsReady();
        WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        return WebDriverFactory.getDriver().findElement(locator);
    }

    protected List<WebElement> waitUntilElementsArePresent(By locator) {
        //waitUntilDocumentIsReady();
        WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        return WebDriverFactory.getDriver().findElements(locator);
    }

    protected List<WebElement> waitUntilElementsAreVisibleWithRetry(By locator) {
        int attempts = 0;
        while (attempts < retryMaxAttempts) {
            try {
                return waitUntilElementsAreVisible(locator);
            } catch (StaleElementReferenceException e) {
                System.out.println("StaleElementReferenceException!!!! retying...");    //TODO log
            }
            attempts++;
        }
        return null;
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

    protected boolean elementExistsNoWait(By locator) {
        //waitUntilDocumentIsReady();
        WebDriverWait wait = new WebDriverWait(WebDriverFactory.getDriver(), Duration.ofSeconds(0));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
    }

    /**
     * Checks if an element exists inside a web element, without waiting for timeout
     *
     * @param webElement - The web element inside which the existence of the element will be evaluated
     * @param locator    - The locator for the element to check
     * @return - true if the element exists, false otherwise
     */
    protected boolean elementExistsNoWait(WebElement webElement, By locator) {
        //waitUntilDocumentIsReady();
        Duration oldTimeout = WebDriverFactory.getDriver().manage().timeouts().getImplicitWaitTimeout();
        WebDriverFactory.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        boolean isDisplayed = false;
        try {
            isDisplayed = webElement.findElement(locator).isDisplayed();
        } catch (Exception e) {
            System.out.println(">>>>>");//TODO
        } finally {
            WebDriverFactory.getDriver().manage().timeouts().implicitlyWait(oldTimeout);
        }
        return isDisplayed;
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

    protected void waitUntilDocumentIsReady() {
        int waited = 0;
        int secondsToWaitEach = 5;
        String js = "return document.readyState";
        System.out.println("before synchronized");
        log.info("Wait until document is ready.");
        synchronized (WebDriverFactory.getDriver()) {
            System.out.println("inside synchronized");
            while (!((JavascriptExecutor) WebDriverFactory.getDriver()).executeScript(js).toString().equalsIgnoreCase("complete") && waited < timeoutSeconds) {
                System.out.println("in while");
                try {
                    WebDriverFactory.getDriver().wait(secondsToWaitEach * 1000L);
                    waited = waited + secondsToWaitEach;
                    System.out.println("Waited " + waited + " for document to be ready");
                    log.info("Waited {} for document to be ready", waited);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void clickElementWithJs(By locator) {
        String js = "";
        if (locator.getClass().equals(By.ByCssSelector.class)) {
            js = "document.querySelector(\"" + ((By.ByCssSelector) locator).getRemoteParameters().value() + "\").click();";
        } else if (locator.getClass().equals(By.ByXPath.class)) {
            js = "document.evaluate(\"" + ((By.ByXPath) locator).getRemoteParameters().value() + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click();";
        }
        ((JavascriptExecutor) WebDriverFactory.getDriver()).executeScript(js);
    }

    protected void slideElement(By locator, int x, int y) {
        WebElement sliderHandle = waitUntilElementIsClickable(locator);
        Actions actions = new Actions(WebDriverFactory.getDriver());
        actions.dragAndDropBy(sliderHandle, x, y).perform();
    }
}
