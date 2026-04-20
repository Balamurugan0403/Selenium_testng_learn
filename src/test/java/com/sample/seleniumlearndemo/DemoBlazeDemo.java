package com.sample.seleniumlearndemo;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class DemoBlazeDemo {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @Test
    @Parameters({"validName","validPassword"})
    public void login(String validName, String validPassword) {

        WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(20));

        driver.get().findElement(By.id("login2")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));

        driver.get().findElement(By.id("loginusername")).sendKeys(validName);
        driver.get().findElement(By.id("loginpassword")).sendKeys(validPassword);
        driver.get().findElement(By.xpath("//button[text()='Log in']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));

        Assert.assertTrue(driver.get().findElement(By.id("nameofuser")).getText().contains("Welcome"));
        System.out.println("Login successfull");
    }

    @Test(dataProvider = "testdata")
    public void loginPasswordInvalid(String name, String password) {

        driver.get().findElement(By.id("login2")).click();
        driver.get().findElement(By.id("loginusername")).sendKeys(name);
        driver.get().findElement(By.id("loginpassword")).sendKeys(password);
        driver.get().findElement(By.xpath("//button[text()='Log in']")).click();

        WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30));
        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.get().switchTo().alert();

        if(alert.getText().equals("Wrong password.")) {
        	Assert.assertEquals("Wrong password.", alert.getText());
        	System.out.println(alert.getText());
        }
        else if(alert.getText().equals("User does not exist.")) {
        	Assert.assertEquals("User does not exist.", alert.getText());
        	System.out.println(alert.getText());
        }

        alert.accept();
    }

//    @Test(dataProvider = "testdata")
//    public void loginUsernameInvalid(String invalidName, String validPassword) {
//
//        driver.get().findElement(By.id("login2")).click();
//        driver.get().findElement(By.id("loginusername")).sendKeys(invalidName);
//        driver.get().findElement(By.id("loginpassword")).sendKeys(validPassword);
//        driver.get().findElement(By.xpath("//button[text()='Log in']")).click();
//
//        WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(30));
//        wait.until(ExpectedConditions.alertIsPresent());
//
//        Alert alert = driver.get().switchTo().alert();
//
//        Assert.assertEquals(alert.getText(), "User does not exist.");
//        System.out.println(alert.getText());
//
//        alert.accept();
//    }

    @BeforeMethod
    @Parameters({"browser","url"})
    public void beforeMethod(String browser, String url) {

        if (browser.equalsIgnoreCase("chrome")) {

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");

            driver.set(new ChromeDriver(options));

            driver.get().manage().timeouts()
                    .implicitlyWait(Duration.ofSeconds(30));

            driver.get().get(url);
        }
    }

    @DataProvider(name = "testdata",parallel = true)
    public Object[][] dp() {

        return new Object[][]{
                {"anandkumar@gmail.com", "Anandkumar@1234"},
                {"aanandkumar@gmail.com", "Anandkumar@123"}
        };
    }

    @AfterMethod
    public void afterMethod() {

        driver.get().quit();
        driver.remove();
    }
}