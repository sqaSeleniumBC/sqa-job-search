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

import junit.framework.Assert;

public class searchPhraseTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();
	String[] resultArray;

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
		int totNumResults = 0;
		int num = 0;
		String jobTitle = null;
		// Navigating to "http://careers.renttesters.com/advancedSearch
		this.driver.get(this.baseUrl + "/advancedSearch/");
		this.driver.findElement(By.id("exact_words")).clear();
		this.driver.findElement(By.id("exact_words")).sendKeys(searchString);
		this.driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// Identify the number of page results
		List<WebElement> pageCount = this.driver.findElements(By.xpath(".//*[@id='content']/div[3]/ul/li"));

		if (pageCount.size() != 0) {
			for (int i = 1; i <= pageCount.size(); i++) {
				if (i > 1) {
					// Navigate to the second page of the results
					this.driver.findElement(By.xpath(".//*[@id='content']/div[3]/ul/li[" + i + "]/a")).click();
					// List all the Job found on the next page
					searchResults = this.driver.findElements(By.className("jobtitle"));
					// Accumulate the total number of Job Results
					totNumResults += searchResults.size();
					// Display all the Job Results on the current page
					for (num = 1; num <= searchResults.size(); num++) {
						jobTitle = this.driver
								.findElement(By.xpath(".//*[@id='content']/div[2]/div[" + num + "]/div/h2/strong/a"))
								.getText();
						System.out.println("Job Title: " + jobTitle);
					}
				} else {
					// List all the Job found on first page
					searchResults = this.driver.findElements(By.className("jobtitle"));
					// Save the current number of results on the first page
					totNumResults = searchResults.size();
					// List all the Job Title on the first page
					for (num = 1; num <= totNumResults; num++) {
						jobTitle = this.driver
								.findElement(By.xpath(".//*[@id='content']/div[2]/div[" + num + "]/div/h2/strong/a"))
								.getText();
						System.out.println("Job Title: " + jobTitle);
					}
				}
			}
		}
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
