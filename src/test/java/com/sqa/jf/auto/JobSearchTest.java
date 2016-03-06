package com.sqa.jf.auto;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class JobSearchTest {

	public String BASE_URL = "https://www.amazon.com";

	private WebDriver driver;

	@Test(dataProvider = "AdvanceAllJobSearchAllFieldData")
	public void AdvanceAllJobSearchAllFieldTest(Integer n, String s) {
	}

	@DataProvider
	public Object[][] AdvanceAllJobSearchAllFieldTestData() {
		return new Object[][] { new Object[] { 1, "a" }, new Object[] { 2, "b" }, };
	}

	@BeforeClass
	public void beforeClass() {
		this.driver = new FirefoxDriver();
	}

	@AfterClass
	public void afterClass() {
	}
}
