package com.sqa.jf.auto.demo;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DanTest {
	// Declaring variables
	public static String baseURL = "http://careers.renttesters.com/";
	public static int pagesNum;
	public static String pagesNumString;
	public static WebDriver driver;
	public static int attemptNum = 0;
	// public static int simplePositionsNum = 0;
	public static int simplePositionsNum;
	private int advancedPagesNum;
	private int advancedPositionsNum;

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

	private int countPositions() {
		int localPositionsNum = 0;
		for (int k = 1; k <= pagesNum; k++) {
			// Collecting the position links
			List<WebElement> linksPositions = driver.findElements(By.className("jobtitle"));
			// Looping through the position links, getting the titles
			localPositionsNum = linksPositions.size() + localPositionsNum;
			if (pagesNum > 1 && k < pagesNum) {
				int pageNumInt = k + 1;
				String pageNumString = String.valueOf(pageNumInt);
				driver.findElement(By.linkText(pageNumString)).click();
			}

		}
		return localPositionsNum;

	}

	@DataProvider // (name = "Dan")
	public Object[][] danDP() {
		return new Object[][] { { "sdfsdfsd" }, { "Business" }, { "Rollout" }, { "Business Analyst" }, { "Analyst" },
				{ "" } };
	}

	@Test(dataProvider = "danDP")
	public void danTest(String searchItem) {

		// Setting the search value to search positions
		driver.findElement(By.xpath("//*[@id='exact_words']")).clear();
		driver.findElement(By.xpath("//*[@id='exact_words']")).sendKeys(searchItem);
		driver.findElement(By.xpath("//*[@id='content']/form/table/tbody/tr[2]/td[3]/input")).click();
		// Number of pages to be able to click on the next page
		pagesNum = pagination();
		// Number of positions on Simple search page
		simplePositionsNum = countPositions();

		// Navigating to Advanced search
		driver.findElement(By.xpath("//*[@id='content']/form/table/tbody/tr[3]/td[3]/a[1]")).click();

		// Setting the value to "With the exact phrase" textfield
		driver.findElement(By.xpath("//*[@id='exact_words']")).clear();
		driver.findElement(By.xpath("//*[@id='exact_words']")).sendKeys(searchItem);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		// Number of pages to be able to click on the next page
		pagesNum = pagination();
		// Number of positions on Advanced search page
		advancedPositionsNum = countPositions();

		// Veryifying if number of pistions on Simple search page matches with
		// the number on Advanced search page - With the exact phrase
		Assert.assertEquals(
				"The number of positions on Simple search page doesn't match with the results on \"With the exact phrase\"",
				simplePositionsNum, advancedPositionsNum);

		// Navigating to Simple search page for the next test
		driver.findElement(By.partialLinkText("simple search")).click();
		// driver.findElement(By.id("main")).click();

	}

	public int pagination() {
		int localPageNunmber;
		// Getting the number of pages
		List<WebElement> linksPagesNums = driver.findElements(By.cssSelector("ul.pagination a"));
		localPageNunmber = linksPagesNums.size();
		return localPageNunmber;
	}
}
