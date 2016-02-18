package com.sqa.jf.auto.demo;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.*;

public class BasicTest {

	private WebDriver driver;

	@Test
	public void googleCheeseExample() {
		exampleGoogleTest("Cheese");
	}

	@Test
	public void googleMilkExample() {
		exampleGoogleTest("Milk");
	}

	@BeforeClass
	public void setupDriver() {
		// DesiredCapabilities dc = DesiredCapabilities.firefox();
		this.driver = new FirefoxDriver();
	}

	@AfterClass
	public void tearDownDriver() {
		this.driver.close();
	}

	private void exampleGoogleTest(final String searchString) {
		this.driver.get("http://google.com");
		WebElement searchField = this.driver.findElement(By.name("q"));
		searchField.clear();
		searchField.sendKeys(searchString);
		System.out.println("Page Title:" + this.driver.getTitle());
		searchField.submit();
		new WebDriverWait(this.driver, 10).until(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver driverObject) {
				return driverObject.getTitle().toLowerCase().startsWith(searchString.toLowerCase());
			}
		});
	}
}
