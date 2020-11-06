package com.challenge;

import com.core.base_test.BaseTest;
import com.domain.page_object.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTests extends BaseTest {

    @BeforeMethod
    public void setUp() {
        super.setDriver();
        checkoutPage = new CheckoutPage();
        homePage = new HomePage();
    }

    @Test
    public void passCheckout() throws InterruptedException {
        homePage.openAndValidate();
        Assert.assertEquals(checkoutPage.isOrderSucess(), true);
    }

    @Test
    public void failCheckout() throws InterruptedException {
        homePage.openAndValidate();
        Assert.assertEquals(checkoutPage.isOrderfail(), true);
    }

}
