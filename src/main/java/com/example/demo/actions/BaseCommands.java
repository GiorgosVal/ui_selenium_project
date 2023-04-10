package com.example.demo.actions;

import com.example.demo.factories.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BaseCommands {

    int timeoutSeconds = 20;
    int retryMaxAttempts = 3;

    /**
     * Returns a new {@link WebDriverWait} with the default {@link #timeoutSeconds} duration
     *
     * @return - a new {@link WebDriverWait}
     */
    private WebDriverWait driverWait() {
        return driverWait(timeoutSeconds);
    }

    /**
     * Returns a new {@link WebDriverWait} with the timeout duration given
     *
     * @param timeoutSeconds - the duration of timeout in seconds
     * @return - a new {@link WebDriverWait}
     */
    private WebDriverWait driverWait(int timeoutSeconds) {
        return new WebDriverWait(WebDriverFactory.getDriver(), Duration.ofSeconds(timeoutSeconds));
    }

    /**
     * Returns the javascript script part to select an element based on the locator instance
     * {@link org.openqa.selenium.By.ByCssSelector} or {@link org.openqa.selenium.By.ByXPath}
     *
     * @param locator - the locator of the element
     * @return - the javascript to select the element
     */
    private String jsSelector(By locator) {
        String js = "";
        if (locator.getClass().equals(By.ByCssSelector.class)) {
            js = "document.querySelector(\"" + ((By.ByCssSelector) locator).getRemoteParameters().value() + "\")";
        } else if (locator.getClass().equals(By.ByXPath.class)) {
            js = "document.evaluate(\"" + ((By.ByXPath) locator).getRemoteParameters().value() + "\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue";
        }
        return js;
    }

    /*
     * ------------------------------------------------------------------------------------------------------
     * ----------------------------------------- WAIT COMMANDS ----------------------------------------------
     * ------------------------------------------------------------------------------------------------------
     */

    /**
     * Wait for an element to be visible
     *
     * @param locator - the locator of the element
     * @return - the element
     */
    public WebElement waitUntilElementIsVisible(By locator) {
        return driverWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for all elements to be invisible
     *
     * @param locator - the locator of the elements
     * @return the elements
     */
    public List<WebElement> waitUntilElementsAreVisible(By locator) {
        return driverWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Wait for all elements to be invisible
     *
     * @param locator - the locator of the elements
     * @return the elements
     */
    public List<WebElement> waitUntilElementsAreVisibleWithRetry(By locator) {
        int attempts = 0;
        while (attempts < retryMaxAttempts) {
            try {
                return waitUntilElementsAreVisible(locator);
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
        return null;
    }

    /**
     * Wait for an element to be invisible
     *
     * @param locator - the locator of the element
     * @return true if the element is invisible, false otherwise
     */
    public boolean waitUntilElementIsInvisible(By locator) {
        return driverWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait for an element to be present
     *
     * @param locator - the locator of the element
     * @return - the element
     */
    public WebElement waitUntilElementIsPresent(By locator) {
        return driverWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Wait for all elements to be present
     *
     * @param locator - the locator of the elements
     * @return - the elements
     */
    public List<WebElement> waitUntilElementsArePresent(By locator) {
        return driverWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    /**
     * Wait for an element to be clickable
     *
     * @param locator - the locator of the element
     * @return - the element
     */
    public WebElement waitUntilElementIsClickable(By locator) {
        waitUntilElementIsVisible(locator);
        scrollToElement(locator);
        return driverWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits until the document is ready
     */
    public void waitUntilDocumentIsReady() {
        int waited = 0;
        int secondsToWaitEach = 5;
        String js = "return document.readyState";
        synchronized (WebDriverFactory.getDriver()) {
            while (!executeJavaScript(js).toString().equalsIgnoreCase("complete") && waited < timeoutSeconds) {
                try {
                    WebDriverFactory.getDriver().wait(secondsToWaitEach * 1000L);
                    waited = waited + secondsToWaitEach;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Checks if an element exists inside a web element, without waiting for timeout
     *
     * @param webElement - The web element inside which the existence of the element will be evaluated
     * @param locator    - The locator for the element to check
     * @return - true if the element exists, false otherwise
     */
    public boolean elementNotExistsNoWait(WebElement webElement, By locator) {
        Duration oldTimeout = WebDriverFactory.getDriver().manage().timeouts().getImplicitWaitTimeout();
        WebDriverFactory.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        boolean isDisplayed = false;
        try {
            isDisplayed = webElement.findElement(locator).isDisplayed();
        } catch (Exception e) {
        } finally {
            WebDriverFactory.getDriver().manage().timeouts().implicitlyWait(oldTimeout);
        }
        return isDisplayed;
    }

    /**
     * Checks if an element exists, without waiting for timeout
     *
     * @param locator - The locator of the element
     * @return - true if the element exists, false otherwise
     */
    public boolean elementNotExistsNoWait(By locator) {
        Duration oldTimeout = WebDriverFactory.getDriver().manage().timeouts().getImplicitWaitTimeout();
        WebDriverFactory.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        boolean isDisplayed = false;
        try {
            isDisplayed = WebDriverFactory.getDriver().findElement(locator).isDisplayed();
        } catch (Exception e) {
        } finally {
            WebDriverFactory.getDriver().manage().timeouts().implicitlyWait(oldTimeout);
        }
        return isDisplayed;
    }

    /*
     * ------------------------------------------------------------------------------------------------------
     * ----------------------------------------- ACTION COMMANDS --------------------------------------------
     * ------------------------------------------------------------------------------------------------------
     */

    /**
     * Executes the javascript script given
     *
     * @param script - the javascript to execute
     * @return - the outcome of the javascript
     */
    public Object executeJavaScript(String script) {
        return ((JavascriptExecutor) WebDriverFactory.getDriver()).executeScript(script);
    }

    /**
     * Scrolls the window to the element specified by the locator.
     * <p>
     * Note: Works only with {@link org.openqa.selenium.By.ByCssSelector} and {@link org.openqa.selenium.By.ByXPath} locators
     *
     * @param locator - the locator to use to scroll to an element
     */
    public void scrollToElement(By locator) {
        executeJavaScript(jsSelector(locator) + ".scrollIntoView({behavior: \"auto\", block: \"center\", inline: \"center\"});");
    }

    /**
     * Clicks an element using javascript
     *
     * @param locator - the locator of the element
     */
    public void clickElementWithJs(By locator) {
        executeJavaScript(jsSelector(locator) + ".click();");
    }

    /**
     * Checks if the checkbox element is checked
     *
     * @param locator - the locator of the element
     * @return - true if the element is checked, false otherwise
     */
    public boolean isElementCheckedJs(By locator) {
        return (Boolean) executeJavaScript("return " + jsSelector(locator) + ".checked;");
    }

    /**
     * Slides an element by x pixels horizontally, and y pixels vertically
     *
     * @param locator - the locator of the element
     * @param x       - the pixels to slide horizontally (positive values for right, negative for left)
     * @param y       - the pixels to slide vertically (positive values for up, negative for down)
     */
    public void slideElement(By locator, int x, int y) {
        WebElement sliderHandle = waitUntilElementIsClickable(locator);
        Actions actions = new Actions(WebDriverFactory.getDriver());
        actions.dragAndDropBy(sliderHandle, x, y).perform();
    }
}
