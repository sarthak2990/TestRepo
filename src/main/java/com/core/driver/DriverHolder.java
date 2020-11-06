package com.core.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class DriverHolder {

    protected static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public static void setDriver() {
        //log.debug("Creating local webDriver for browser = {}...", browserName);
        /*
         * Replace ifelse with switch to make it more easier to support other condition without mistakes
         * */
        String browser = System.getProperty("browser");
        String browserName = (browser != null) ? browser : "chrome";
        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                webDriver.set(new ChromeDriver());
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                webDriver.set(new FirefoxDriver());
                break;
            default: {
                WebDriverManager.chromedriver().setup();
                webDriver.set(new ChromeDriver());
            }
        }
        webDriver.get().manage().window().maximize();
        webDriver.get().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    public static WebDriver getDriver() {
        return webDriver.get();
    }

    public static void quitDriver() {
        if (nonNull(webDriver.get())) {
            webDriver.get().quit();
        }
        webDriver.remove();
    }
}
