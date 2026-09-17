package com.example;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class LoginTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();

        if (Boolean.getBoolean("headless")) {

            options.addArguments("--headless=new");

            options.addArguments("--window-size=1920,1080");

        }

        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldLoginSuccessfully() {

        driver.get("https://seleniumbase.io/simple/login");

        driver.findElement(By.id("username"))
                .sendKeys("demo_user");

        driver.findElement(By.id("password"))
                .sendKeys("secret_pass");

        driver.findElement(By.id("log-in"))
                .click();

        String heading = driver.findElement(By.tagName("h1"))
                .getText();

        assertEquals("Welcome!", heading);
    }

    @Test
    void shouldLoginRequireUser() {
        driver.get("https://seleniumbase.io/simple/login");

        driver.findElement(By.id("log-in"))
                .click();

        String heading = driver.findElement(By.tagName("h6"))
                .getText();

        assertEquals("The Username is Required!", heading);

    }

    @Test
    void shouldLoginRequirePassword() {
        driver.get("https://seleniumbase.io/simple/login");

        WebDriverWait wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("username")
                )
        ).sendKeys("demo_user");

        driver.findElement(By.id("log-in"))
                .click();

        String heading = driver.findElement(By.tagName("h6"))
                .getText();

        assertEquals("The Password is Required!", heading);

    }

    @Test
    void shouldInvalidUsername() {
        driver.get("https://seleniumbase.io/simple/login");

        driver.findElement(By.id("username"))
                .sendKeys("demo");

        driver.findElement(By.id("log-in"))
                .click();

        String heading = driver.findElement(By.tagName("h6"))
                .getText();

        assertEquals("Invalid Username!", heading);

    }
}
