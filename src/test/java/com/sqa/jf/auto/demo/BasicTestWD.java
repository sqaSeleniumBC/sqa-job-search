package com.sqa.jf.auto.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.sqa.jf.auto.DriverFactory;

public class BasicTestWD extends DriverFactory {

	private void exampleGoogleTest(final String searchString) throws Exception {
		WebDriver driver = DriverFactory.getDriver();
		driver.get("http://sfbay.craigslist.com");
		WebElement searchField = driver.findElement(By.id("query"));
		searchField.clear();
		searchField.sendKeys(searchString);
		System.out.println("Page Title:" + driver.getTitle());
		searchField.submit();
		Thread.sleep(5000);
	}

	@Test
	public void googleCheeseExample() throws Exception {
		exampleGoogleTest("Cheese");
	}

	@Test
	public void googleMilkExample() throws Exception {
		exampleGoogleTest("Milk");

	}
}