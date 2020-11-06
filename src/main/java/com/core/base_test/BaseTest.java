package com.core.base_test;

import com.core.driver.DriverHolder;
import com.domain.page_object.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class BaseTest {

    protected CheckoutPage checkoutPage;
    protected HomePage homePage;

    public void setDriver() {
        /*
         * In this method I want to read browser from mvn commandline
         * So making a option to read it from cmdline if not then use default
         * */
        DriverHolder.setDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownDriver() {
        DriverHolder.quitDriver();
    }
}
