package com.sample.seleniumlearndemo;

import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

public class demolearn {
	private static final ThreadLocal <WebDriver> driver = new ThreadLocal <WebDriver>();
  @Test(dataProvider = "testdata")
  public void search(String s) {
	  WebElement txtBox = driver.get().findElement(By.id("sb_form_q"));
	  txtBox.sendKeys(s);
	  System.out.println("keyword entered is : "+s);
	  txtBox.sendKeys(Keys.ENTER);
	  System.out.println("Search result is displayed");
  }

  @DataProvider(name="testdata", parallel=true)
  public Object[][] dp() {
    return new Object[][] {
    	{"Selenium"},{"TestNG"}};
    };
  
  @BeforeMethod
  public void setUp() {
	  System.out.println("Start the test");
	  driver.set(new ChromeDriver());
	  driver.get().get("https://www.bing.com/");
	  driver.get().manage().window().maximize();
	  
  }
  
  @AfterMethod
  public void tearDown() {
	  driver.get().quit();
	  
  }
}

