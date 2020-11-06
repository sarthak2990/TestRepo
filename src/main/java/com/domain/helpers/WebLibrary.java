package com.domain.helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class WebLibrary {

    private WebDriver driver;
    ElementLocator elementlocator = new ElementLocator();
    private static FileInputStream fileInputStream;
    private static Properties properties;

    public WebLibrary(WebDriver driver) {
        this.driver = driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
    }

    public void enterTextUsingAction(String element, String text) {
        isElementPresent(element);
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(elementlocator.elements(element)));
        actions.click();
        actions.sendKeys(text);
        actions.sendKeys(Keys.TAB);
        actions.build().perform();
    }

    public void click(String element) {
        try {
            new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(elementlocator.elements(element))).click();
            Reporter.log("*****Performed click operation on element->" + element + "*****", true);
        } catch (ElementNotVisibleException env) {
            driver.findElement(elementlocator.elements(element)).click();
            Reporter.log(env.getMessage(), true);
            Reporter.log("*****Element is present in DOM but not visible on the page*****" + env.getMessage(), true);
        } catch (NoSuchElementException ne) {
            Reporter.log(ne.getMessage(), true);
            Reporter.log("*****The element could not be located on the page.*****" + ne.getMessage(), true);
        } catch (StaleElementReferenceException se) {
            Reporter.log(se.getMessage(), true);
            Reporter.log(
                    "*****Either the element has been deleted entirely or the element is no longer attached to DOM.*****"
                            + se.getMessage(), true);
        } catch (Exception e) {
            e.printStackTrace();
            Reporter.log("Could not perform click for first time, trying again", true);
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(elementlocator.elements(element))).click();
            Reporter.log("*****Could not perform click. Please check!!! *****", true);
        }
    }

    public void fill(String element, String inputdata) {
        try {
            WebElement field = new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(elementlocator.elements(element)));
            field.clear();
            field.sendKeys(inputdata);
          //  pause(600);
            field.sendKeys(Keys.TAB);
            Reporter.log("*****Performed fill operation on locator->" + element + "*****", true);
        } catch (InvalidElementStateException ie) {
            Reporter.log(ie.getMessage(), true);
            Reporter.log("*****Element is either hidden or disabled*****", true);
        } catch (NoSuchElementException ne) {
            Reporter.log(ne.getMessage(), true);
            Reporter.log("*****The element could not be located on the page.*****" + ne.getMessage(), true);
        } catch (StaleElementReferenceException se) {
            Reporter.log(
                    "*****Either the element has been deleted entirely or the element is no longer attached to DOM.*****"
                            + se.getMessage(), true);
        } catch (Exception e) {
            e.printStackTrace();
            Reporter.log("*****Could not perform fill operation. Please check!!! *****" + e.getMessage(), true);
        }
    }

    public String getText(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, 90);
        WebElement element = wait
                .until(ExpectedConditions.visibilityOfElementLocated(elementlocator.elements(locator)));
        return element.getText();
    }

    public boolean isElementPresent(String locator) {
        boolean result = false;
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(elementlocator.elements(locator)));
            result = true;
        } catch (TimeoutException e) {
            Reporter.log(e.getStackTrace().toString(), true);
        }
        return result;
    }

    public WebElement getWebElement(String element)
    {
        isElementPresent(element);
        return driver.findElement(elementlocator.elements(element));
    }

    public boolean isTextPresentOnElement(String locator, String expectedText) {
        boolean result = false;
        scrollToWebElement(locator);
        String actual = getText(locator);
        result = isStringcontains(actual, expectedText);
        return result;
    }

    boolean isStringcontains(String string1, String string2) {
        boolean result = false;
        if (string1.toLowerCase().contains(string2.toLowerCase())) {
            result = true;
        }
        return result;
    }

    public boolean verifyTitle(String title) {
        Reporter.log(">>>>" + driver.getTitle(), true);
        return isStringcontains(driver.getTitle(), title);
    }

    public void scrollToWebElement(String locator) {
        try {
            (new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOfElementLocated(elementlocator.elements(locator)));
            WebElement element = driver.findElement(elementlocator.elements(locator));
            Point point = element.getLocation();
            JavascriptExecutor jsExecutor = ((JavascriptExecutor) driver);
            int y = point.getY() - 200;
            jsExecutor.executeScript("window.scrollTo(" + point.getX() + "," + y + ");", "");
        } catch (Exception e) {
            Reporter.log(e.getStackTrace().toString(), true);
        }
    }

    public Properties readProperty(String filePath) {
        try {
            fileInputStream = new FileInputStream(filePath);
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException f) {
            Reporter.log(f.getMessage());
        }
        return properties;
    }
}
