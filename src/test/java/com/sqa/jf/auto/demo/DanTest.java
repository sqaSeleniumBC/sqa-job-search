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

public class DanTest {
	// Declaring variables
	public static String baseURL = "http://careers.renttesters.com/";
	public static int verifyPagesNumInt;
	public static String verifyPagesNumString;
	private WebDriver driver;
	public int attemptNum = 0;

	@AfterClass
	public void afterClass() {
		// Quit Driver
		// driver.quit();
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

	@Test(dataProvider = "danDP") // (testName = "Dan")
	public void danTest(String searchItem, int PagesNum1, String firstPosition, String secondPosition) {
		String positions[] = { firstPosition, secondPosition };
		driver.findElement(By.xpath("//*[@id='exact_words']")).sendKeys(searchItem);
		driver.findElement(By.xpath("//*[@id='content']/form/table/tbody/tr[2]/td[3]/input")).click();
		// Verify if data provided is valid: number of pages, titles of
		// positions
		verifyPagesNumString = driver.findElement(By.className("pagination")).getText().replaceAll("[^0-9]", "");
		verifyPagesNumInt = Integer.parseInt(verifyPagesNumString);
		Assert.assertEquals("The number of pages is wrong - ", PagesNum1, verifyPagesNumInt);
		List<WebElement> verifyLinksPositions = driver.findElements(By.className("jobtitle"));
		for (int i = 0; i < verifyLinksPositions.size(); i++) {
			String positionName = verifyLinksPositions.get(i).getText();
			Assert.assertEquals("Job title '" + positionName + "' is wrong. Please check", positions[i], positionName);
		}
		driver.findElement(By.xpath("//*[@id='content']/form/table/tbody/tr[3]/td[3]/a[1]")).click();

	}
}
