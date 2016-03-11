package com.sqa.jf.search;

import static org.testng.Assert.fail;

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

import com.sqa.jf.util.helper.RentTesterUtil;

import junit.framework.Assert;

/**
 *
 * @author Mallapre, Christian
 * @version 1.0.0
 * @since 1.0
 *
 */
public class SearchPhraseTest {

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@DataProvider
	public Object[][] searchPhraseData() {
		return new Object[][] {
				// TODO Modify Data
				new Object[] { "Quality Assurance", 2 }, new Object[] { "Engineer", 16 },
				new Object[] { "Automation Tester", 0 }, new Object[] { "Data Analyst", 2 },
				new Object[] { "Local Candidates", 1 }, new Object[] { "QA Engineer", 6 } };
	}

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		this.driver = new FirefoxDriver();
		this.baseUrl = "http://careers.renttesters.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	@Test(dataProvider = "searchPhraseData")
	public void testRentTester(String searchString, int expectedResultsNum) throws Exception {

		// Variable Declaration
		List<WebElement> searchResults = null;
		int num = 0;
		String jobTitle = null;
		int totNumResults = 0;

		// Navigating to "http://careers.renttesters.com/advancedSearch
		this.driver.get(this.baseUrl + "/advancedSearch/");
		this.driver.findElement(By.id("exact_words")).clear();
		this.driver.findElement(By.id("exact_words")).sendKeys(searchString);
		this.driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// Display all the results and get the total number of results
		totNumResults = RentTesterUtil.RentTester(this.driver, totNumResults);

		// Display the total number of job results
		System.out.println("Total Number of Job Results for " + searchString + ": " + totNumResults);

		// Validate that the results match the expected
		Assert.assertEquals(expectedResultsNum, totNumResults);
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

	private boolean isAlertPresent() {
		try {
			this.driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private boolean isElementPresent(By by) {
		try {
			this.driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
