package com.core.base_test;

import com.core.driver.DriverHolder;
import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

@Slf4j
public class TestListener extends TestListenerAdapter {

    @Override
    public void onTestStart(ITestResult iTestResult) {
        super.onTestStart(iTestResult);
        log.info("STARTED: {}", iTestResult.getName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        super.onTestSuccess(iTestResult);
        log.info("Finished successfully: {}", iTestResult.getName());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        super.onTestSkipped(iTestResult);
        log.info("Skipped: {}", iTestResult.getName());
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        super.onTestFailure(testResult);
        log.error("FAILED: {} WITH: {}", testResult.getName(), testResult.getThrowable().toString());
        attachScreenShot();
    }

    @Attachment(value = "Screen shot", type = "image/png", fileExtension = ".png")
    private byte[] attachScreenShot() {
        try {
            return ((TakesScreenshot) DriverHolder.getDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (WebDriverException e) {
            log.error("Selenium screenshot capture failed: {}", e.getMessage());
            e.printStackTrace();
        }
        return new byte[0];
    }
}
