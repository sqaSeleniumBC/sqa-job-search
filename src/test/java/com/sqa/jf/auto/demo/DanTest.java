package com.sqa.jf.auto.demo;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import junit.framework.Assert;
import junit.framework.ComparisonFailure;

public class DanTest {
	// Declaring variables
	public static String baseURL = "http://careers.renttesters.com/";
	public static int pagesNumInt;
	public static String pagesNumString;
	static String positionsNames[];
	public static WebDriver driver;

	public static void verifyPositions(String firstPosition, String secondPosition) {
		String[] positionsNamesExpected = { firstPosition, secondPosition };
		// Collecting the position links
		List<WebElement> linksPositions = driver.findElements(By.className("jobtitle"));
		// Looping through the position links, getting the titles
		for (int i = 0; i < linksPositions.size(); i++) {
			String positionsNamesActual = linksPositions.get(i).getText();
			// Comparing titles with the values from dataprovider
			try {
				Assert.assertEquals("Job title '" + positionsNamesActual + "' is wrong. Please check",
						positionsNamesExpected[0], positionsNamesActual);
			} catch (ComparisonFailure c) {
				Assert.assertEquals("Job title '" + positionsNamesActual + "' is wrong. Please check",
						positionsNamesExpected[1], positionsNamesActual);
			}
		}
	}

	public int attemptNum = 0;

	@AfterClass
	public void afterClass() {
		// Quit Driver
		driver.quit();
	}

	@BeforeClass
	public void beforeClass() throws InterruptedException {
		do {
			// Launch FireFox browser
			Thread.sleep(1000);
			driver = new FirefoxDriver();
			// Navigate to url
			Thread.sleep(1000);

			driver.get(baseURL);
			Thread.sleep(1000);
			// Verification if the page is correct
			if (driver.getCurrentUrl().equals(baseURL)) {
				System.out.println("<<<<<<<<>>>>>>>>");
				System.out.println("The page is verified: " + driver.getCurrentUrl());
			} else {
				// In case if the page is wrong then code will make 3 attempts
				// to get the correct page.
				System.out.println("Wrong page!");
				// Quit Driver
				driver.close();
				attemptNum++;
				System.out.println("Attempt: " + attemptNum);
			}
		} while (!(driver.getCurrentUrl().equals(baseURL)) || (attemptNum == 3));
		// Error message if the code couldn't get proper page after 3 attempts
		if (attemptNum == 3) {
			System.out.println(
					"Please verify the URL. According the requirements it should be http://careers.renttesters.com/ \n");
			System.out.println("<<<<<<<<>>>>>>>>");
			driver.quit();
		}
	}

	@DataProvider // (name = "Dan")
	public Object[][] danDP() {
		return new Object[][] { { "Rollout", 1, "SAP Business Rollout Manager", "Business Analyst" } };
	}

	@Test(dataProvider = "danDP")
	public void danTest(String searchItem, int PagesNum1, String firstPosition, String secondPosition) {
		driver.findElement(By.xpath("//*[@id='exact_words']")).sendKeys(searchItem);
		driver.findElement(By.xpath("//*[@id='content']/form/table/tbody/tr[2]/td[3]/input")).click();

		// Verify if data provided is valid: number of pages, titles of
		// positions
		paginationNum();
		Assert.assertEquals("The number of pages is wrong - ", PagesNum1, pagesNumInt);
		verifyPositions(firstPosition, secondPosition);
		System.out.println("Simple search page: parametres of dataprovider verified - PASS.");

		// Navigating to Advanced search
		driver.findElement(By.xpath("//*[@id='content']/form/table/tbody/tr[3]/td[3]/a[1]")).click();

		// Checking "With all of this words" textfield functionality
		driver.findElement(By.xpath("//*[@id='all_words']")).sendKeys(searchItem);
		driver.findElement(By.xpath("//*[@id='exact_words']")).clear();
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		paginationNum();
		Assert.assertEquals("The number of pages is wrong - ", PagesNum1, pagesNumInt);
		verifyPositions(firstPosition, secondPosition);
		driver.findElement(By.xpath("//*[@id='all_words']")).clear();
		System.out.println("<With all of this words> textfield: pagination and job positions verified  - PASS.");

		// Checking "With the exact phrase" textfield functionality
		driver.findElement(By.xpath("//*[@id='exact_words']")).sendKeys(searchItem);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		paginationNum();
		Assert.assertEquals("The number of pages is wrong - ", PagesNum1, pagesNumInt);
		verifyPositions(firstPosition, secondPosition);
		driver.findElement(By.xpath("//*[@id='exact_words']")).clear();
		System.out.println("<With the exact phrase> textfield: pagination and job positions verified  - PASS.");

		// Checking "With at least one of these words" textfield functionality
		driver.findElement(By.xpath("//*[@id='one_word']")).sendKeys(searchItem);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		paginationNum();
		Assert.assertEquals("The number of pages is wrong - ", PagesNum1, pagesNumInt);
		verifyPositions(firstPosition, secondPosition);
		driver.findElement(By.xpath("//*[@id='one_word']")).clear();
		System.out.println(
				"<With at least one of these words> textfield: pagination and job positions verified  - PASS.");
		System.out.println("<<<<<<<<>>>>>>>>");
		System.out.println("");

	}

	public int paginationNum() {
		// Getting the number of pages
		pagesNumString = driver.findElement(By.className("pagination")).getText().replaceAll("[^0-9]", "");
		return pagesNumInt = Integer.parseInt(pagesNumString);
	}
}
