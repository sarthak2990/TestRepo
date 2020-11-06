package com.domain.page_object;

import com.core.driver.DriverHolder;
import com.domain.helpers.AppParametersHelper;
import com.domain.helpers.WebLibrary;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

@Slf4j
public class HomePage {

    private WebDriverWait webDriverWait;
    public HomePage() {
        webDriverWait = new WebDriverWait(DriverHolder.getDriver(), 60, 30);
    }
    WebLibrary webLibrary = new WebLibrary(DriverHolder.getDriver());

    public void openAndValidate() {
        String url = AppParametersHelper.AutomationPractice.getBaseUrl();
        DriverHolder.getDriver().get(url);
        Assert.assertEquals(webLibrary.verifyTitle("Sample Store"), true);
    }

}
