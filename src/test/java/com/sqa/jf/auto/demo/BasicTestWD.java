package com.sqa.jf.auto.demo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BasicTestWD {

	public String BASE_URL = "http://careers.renttesters.com";

	private WebDriver driver;

	private boolean acceptNextAlert = true;

	private StringBuffer verificationErrors = new StringBuffer();

	@DataProvider
	public Object[][] AdvanceAllJobSearchAllFieldData() {
		return new Object[][] { new Object[] { "QA", true } };
	}

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
	}

	@AfterClass
	public void afterClass() {
		// this.driver.quit();
	}

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(BASE_URL + "/");
	}
}
