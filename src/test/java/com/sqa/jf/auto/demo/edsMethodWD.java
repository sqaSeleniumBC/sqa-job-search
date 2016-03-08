/**
 * File Name: edsMethodWD.java<br>
 * Mora, Eduardo<br>
 * Java Boot Camp Exercise<br>
 * Instructor: Jean-francois Nepton<br>
 * Created: Mar 7, 2016
 */
package com.sqa.jf.auto.demo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * edsMethodWD //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 * 
 * @author Mora, Eduardo
 * @version 1.0.0
 * @since 1.0
 */
public class edsMethodWD {

	public String BASE_URL = "http://careers.renttesters.com/advancedSearch";

	private WebDriver driver;

	private boolean acceptNextAlert = true;

	private StringBuffer verificationErrors = new StringBuffer();

	/**
	 * Will perform a search using one or more words and assert if expected
	 * results were returned. In order to perform a better test would need
	 * access to the DB to verify count matches.
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
			Assert.assertFalse(expectingResults);
			System.out.println(this.driver
					.findElement(By.xpath("//div[@id='content']/div[2]/center/strong")).getText());
		} else {
			if (this.driver.findElement(By.cssSelector("a[href^='/view/position']")).isDisplayed()
					&& this.driver.findElement(By.cssSelector("a[href^='/view/position']"))
							.isDisplayed()) {
				List<WebElement> elements =
						this.driver.findElements(By.cssSelector("a[href^='/view/position']"));
				printResultsFromAllWordsField(elements, allWords);
				Assert.assertTrue(expectingResults);
			}
		}
	}

	/**
	 * @param elements
	 */
	private void printResultsFromAllWordsField(List<WebElement> elements, String searched) {
		System.out.println("*********** This are the Results from the 1st page using the string "
				+ searched + " ***********");
		System.out.println("Count is " + elements.size() + " and below are the titles: ");
		for (WebElement webElement : elements) {
			System.out.println("\t" + webElement.getText());// + " \n\tLink:
															// "+webElement.getAttribute("href"));
		}
	}

	@DataProvider
	public Object[][] AdvanceAllJobSearchAllFieldData() {
		return new Object[][] { new Object[] { "QA", true }, new Object[] { "SQA", true },
				new Object[] { "QA, Test", true }, new Object[] { "QA, Test, Engineer", true },
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
