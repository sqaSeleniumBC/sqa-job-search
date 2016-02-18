package com.sqa.jf.auto.demo;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;

public class BasicTest {

	@Test
	public void googleCheeseExample() {
		exampleGoogleTest("Cheese");
	}

	@Test
	public void googleMilkExample() {
		exampleGoogleTest("Milk");
	}

	private void exampleGoogleTest(final String searchString) {
		WebDriver driver = new FirefoxDriver();
		driver.get("http://google.com");
		WebElement searchField = driver.findElement(By.name("q"));
		searchField.clear();
		searchField.sendKeys(searchString);
		System.out.println("Page Title:" + driver.getTitle());
		searchField.submit();
		new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driverObject) {
				return driverObject.getTitle().toLowerCase().startsWith(searchString.toLowerCase());
			}
		});
	}
}
