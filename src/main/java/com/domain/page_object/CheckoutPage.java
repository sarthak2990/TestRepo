package com.domain.page_object;

import com.core.driver.DriverHolder;
import com.domain.helpers.WebLibrary;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.util.Properties;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;

public class CheckoutPage {

    WebDriver driver = DriverHolder.getDriver();
    WebLibrary webLibrary = new WebLibrary(driver);
    private WebDriverWait webDriverWait;
    String slash = File.separator;
    private Properties loginPageOptions = webLibrary.readProperty("src" + slash + "main" + slash + "java" + slash + "com" + slash + "domain" + slash + "page_object" + slash + "CheckoutPage.properties");

    public CheckoutPage() {
        webDriverWait = new WebDriverWait(DriverHolder.getDriver(), 60, 30);
    }

    public boolean isOrderSucess() throws InterruptedException {
        checkBuyNow();
        checkCheckOut();
        checkOrderSummary();
        selectCreditCard();
        fillAndVerifyCreditCard("4811 1111 1111 1114", "123", "02/21");
        return performPayment();
    }

    public boolean isOrderfail() throws InterruptedException {
        checkBuyNow();
        checkCheckOut();
        checkOrderSummary();
        selectCreditCard();
        fillAndVerifyCreditCard("4911 1111 1111 1113", "123", "02/21");
        return performDeclinePayment();
    }

    @Step
    public void checkBuyNow() {
        webLibrary.click(loginPageOptions.getProperty("buynow"));
    }

    @Step
    public void checkCheckOut() {
        webLibrary.click(loginPageOptions.getProperty("checkout"));
    }

    @Step
    public void checkOrderSummary() {
        driver.switchTo().frame("snap-midtrans");
        Assert.assertEquals(webLibrary.getText(loginPageOptions.getProperty("orderTitle")), "COCO STORE");
        Assert.assertEquals(webLibrary.getText(loginPageOptions.getProperty("amount")), "20,000");
        Assert.assertEquals(webLibrary.getText(loginPageOptions.getProperty("item-name")), "Midtrans Pillow");
        WebElement element = webLibrary.getWebElement(loginPageOptions.getProperty("continue"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.click();
        actions.build().perform();
    }

    @Step
    public void selectCreditCard() {
        webLibrary.click(loginPageOptions.getProperty("credit"));
    }

    @Step
    void fillAndVerifyCreditCard(String cardNo, String cvv, String date) throws InterruptedException {
        webLibrary.fill(loginPageOptions.getProperty("cardNo"), cardNo);
        webLibrary.fill(loginPageOptions.getProperty("cvv"), cvv);
        webLibrary.fill(loginPageOptions.getProperty("date"), date);
        //Verify Price after Discount
        WebElement element = webLibrary.getWebElement(loginPageOptions.getProperty("payNow"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.click();
        actions.build().perform();
    }

    boolean performPayment() throws InterruptedException {
        Thread.sleep(10000);
        driver.switchTo().frame(0);
        webLibrary.enterTextUsingAction(loginPageOptions.getProperty("password"), "112233");
        // Clicking OK button thru Action class
        WebElement ok = webLibrary.getWebElement(loginPageOptions.getProperty("ok"));
        Actions ok2 = new Actions(driver);
        ok2.moveToElement(ok);
        ok2.click();
        ok2.build().perform();
        return webLibrary.isTextPresentOnElement(loginPageOptions.getProperty("result"), "Thank you for your purchase");
    }

    boolean performDeclinePayment() throws InterruptedException {
        Thread.sleep(10000);
        driver.switchTo().frame(0);
        webLibrary.enterTextUsingAction(loginPageOptions.getProperty("password"), "112233");
        // Clicking OK button thru Action class
        WebElement ok = webLibrary.getWebElement(loginPageOptions.getProperty("ok"));
        Actions ok2 = new Actions(driver);
        ok2.moveToElement(ok);
        ok2.click();
        ok2.build().perform();
        Thread.sleep(2000);
        driver.switchTo().frame(0);
        return webLibrary.isTextPresentOnElement(loginPageOptions.getProperty("invalid"), "Your card got declined by the bank");
    }
}
