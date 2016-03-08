package com.sqa.jf.auto.demo;
//
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.firefox.FirefoxDriver;
//
// import org.testng.annotations.AfterClass;
// import org.testng.annotations.BeforeClass;import
// org.testng.annotations.DataProvider;
// import org.testng.annotations.Test;
//
// import com.sqa.jf.auto.DriverFactory;
//
// public class BasicTestWD extends DriverFactory {
//
// public String BASE_URL = "http://careers.renttesters.com/advancedSearch";
//
// private WebDriver driver;
//
// // @Test
// public void googleCheeseExample() throws Exception {
// exampleGoogleTest("Cheese");
// }
//
// // @Test
// public void googleMilkExample() throws Exception {
// exampleGoogleTest("Milk");
// }
//
// private void exampleGoogleTest(final String searchString) throws Exception {
// WebDriver driver = DriverFactory.getDriver();
// driver.get("http://sfbay.craigslist.com");
// WebElement searchField = driver.findElement(By.id("query"));
// searchField.clear();
// searchField.sendKeys(searchString);
// System.out.println("Page Title:" + driver.getTitle());
// searchField.submit();
// Thread.sleep(5000);
// }
//
// @Test(dataProvider = "AdvanceAllJobSearchAllFieldData")
// public void AdvanceAllJobSearchAllFieldTest(Integer n, String s) {
// }
//
// @DataProvider
// public Object[][] AdvanceAllJobSearchAllFieldTestData() {
// this.driver = new FirefoxDriver();
// this.driver.get(this.BASE_URL + "/");
// return new Object[][] { new Object[] { "Objective Of Test: Search", "" },
// new Object[] { 2, "b" }, };
// // this.driver.quit();
// }
// }


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BasicTestWD {


	public String BASE_URL = "http://careers.renttesters.com/advancedSearch";

	private WebDriver driver;

	private boolean acceptNextAlert = true;

	private StringBuffer verificationErrors = new StringBuffer();

	/**
	 * Will perform a search using one or more words and verify that the word
	 * existing within the description of the job.
	 * 
	 * @param allWords
	 *            words that will be used to search which will be obtained from
	 *            the data provide.
	 */
	@Test(dataProvider = "AdvanceAllJobSearchAllFieldData")
	public void AdvanceAllJobSearchAllFieldTest(String allWords, boolean expectingResults) {
		String description = "";
		this.driver.findElement(By.xpath(".//*[@id='all_words']")).clear();
		this.driver.findElement(By.xpath(".//*[@id='all_words']")).sendKeys(allWords);
		this.driver.findElement(By.xpath(".//input[@value='Search']")).click();
		if (this.driver.findElements(By.xpath("//div[@id='content']/div[2]/center/strong"))
				.size() > 0) {
			if (!expectingResults) {
				System.out.println("Pass");
			}
			System.out.println(this.driver
					.findElement(By.xpath("//div[@id='content']/div[2]/center/strong")).getText());
		} else {
			if (this.driver.findElement(By.cssSelector("a[href^='/view/position']")).isDisplayed()
					&& this.driver.findElement(By.cssSelector("a[href^='/view/position']"))
							.isDisplayed()) {
				List<WebElement> elements =
						this.driver.findElements(By.cssSelector("a[href^='/view/position']"));
				printResultsFromAllWordsField(elements);
				if (expectingResults) {
					System.out.println("Pass");
				}
				this.driver.findElement(By.id("content")).getText();
				//
				// TODO: Need to get all text in an string then break it to
				// array to search for the string....
				// for (WebElement webElement : elements) {
				// System.out.println("Webelement output *********** \n\t" +
				// webElement.getText());
				// System.out.println(
				// "Webelement output *********** \n\t" +
				// webElement.getAttribute("href"));
				// this.driver.get(webElement.getAttribute("href"));
				// String description =
				// this.driver.findElement(By.id("content")).getText();
				// if (description.contains(allWords)) {
				// System.out.println("String Was Found");
				// } else {
				// System.out.println("String not found");
				// }
				// System.out.println("***********Description
				// *************\n"
				// + this.driver.findElement(By.id("content")).getText());
				// this.driver.navigate().back();
				// }
			}
		}
		// + "************Out Put *****************");
	}

	/**
	 * @param elements
	 */
	private void printResultsFromAllWordsField(List<WebElement> elements) {
		System.out.println("*********** This are the Results for your search ***********");
		for (WebElement webElement : elements) {
			System.out.println("Job Title: " + webElement.getText() + " \n\tLink: "
					+ webElement.getAttribute("href"));
		}
	}

	@DataProvider
	public Object[][] AdvanceAllJobSearchAllFieldData() {
		return new Object[][] { new Object[] { "QA", true }, new Object[] { "SQA", true },
				new Object[] { "QA, Test", true },
				new Object[] { "No, Search, With, This Key Words", false } };
	}

	@BeforeClass
	public void beforeClass() {
		this.driver = new FirefoxDriver();
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		this.driver.get(this.BASE_URL + "/");
	}

	@AfterClass
	public void afterClass() {
		// this.driver.quit();
	}

	private boolean isElementPresent(By by) {
		try {
			this.driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			this.driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = this.driver.switchTo().alert();
			String alertText = alert.getText();
			if (this.acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			this.acceptNextAlert = true;
		}
	}
}