package com.sample.seleniumlearndemo;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class demoblaze_ram {

    public WebDriver driver;

    @Parameters({"browser", "url"})
    @BeforeMethod
    public void setup(String browser, String Url) {

        if (browser.equalsIgnoreCase("Chrome")) {

            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

            driver = new ChromeDriver(options);
        }

        driver.manage().window().maximize();
        driver.get(Url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    @Parameters({"username", "password"})
    @Test(priority = 1)
    public void loginTest(String userName, String password) {

        driver.findElement(By.id("login2")).click();
        driver.findElement(By.id("loginusername")).sendKeys(userName);
        driver.findElement(By.id("loginpassword")).sendKeys(password);
        driver.findElement(By.xpath("//button[text()='Log in']")).click();
    }
    @Test(priority = 2)
    @Parameters({"username1", "password1"})
    public void invalidTest(String userName, String password) {

        driver.findElement(By.id("login2")).click();
        driver.findElement(By.id("loginusername")).sendKeys(userName);
        driver.findElement(By.id("loginpassword")).sendKeys(password);
        driver.findElement(By.xpath("//button[normalize-space()='Log in']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        String actual = alert.getText();
        String expected = "User does'nt exist.";

        Assert.assertEquals(actual, expected, "Actual msg: " + actual);
        alert.accept();
    }
    @Test(priority = 3)
    @Parameters({"username2", "password2"})
    public void invalidTest2(String userName, String password) {

        driver.findElement(By.id("login2")).click();
        driver.findElement(By.id("loginusername")).sendKeys(userName);
        driver.findElement(By.id("loginpassword")).sendKeys(password);
        driver.findElement(By.xpath("//button[normalize-space()='Log in']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        String actual = alert.getText();
        String expected = "Wrong password.";

        Assert.assertEquals(actual, expected);
        alert.accept();
    }

    @AfterMethod
    public void afterTest() {
        driver.quit();
    }
}
