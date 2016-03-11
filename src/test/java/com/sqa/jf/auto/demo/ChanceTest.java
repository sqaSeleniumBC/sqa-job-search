package com.sqa.jf.auto.demo;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class ChanceTest {

	private String base_url =
			"http://careers.renttesters.com/search/?all_words=&exact_words=&one_word=&none_words=&position_type=all&location_distance=5&formatted_location=&location=&location_lat=&location_lng=&fromage=any&items_per_page=10&sort=relevance&current_page=1&advanced=1";

	private WebDriver driver = new FirefoxDriver();

	private WebDriverWait wait = new WebDriverWait(driver, 10);

	@AfterClass
	public void afterClass() {
		driver.close();
	}

	@BeforeClass
	public void beforeClass() {
	}

	@DataProvider
	public Object[][] keywordData() {
		return new Object[][] { new Object[] { "are" }, new Object[] { "Analyst" }, new Object[] { "QA" } };
	}

	@Test(dataProvider = "keywordData")
	public void verifyKeywordTest(String keyword) {
		boolean keywordPresent = true;
		String[][] pagesResults;
		int currentPage = 1;
		int totalPages = 1;
		List<WebElement> links;
		// load the website
		driver.get(base_url);
		// When the text field is displayed enter the keyword
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#one_word"))).sendKeys(keyword);
		// Click submit
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		// Add all pagination elements to list
		List<WebElement> pages = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".pagination>li>a[href]")));
		// Initialize pageLinks array to the number of page results that are
		// displayed
		String[] pageLinks = new String[pages.size()];
		// Store the number of page results
		totalPages = pages.size();
		// Initialize the first dimension to the number of page results
		pagesResults = new String[totalPages][];
		// Store the pagination links
		for (int i = 0; i < pages.size(); i++) {
			pageLinks[i] = pages.get(i).getAttribute("href");
		}
		// Iterate through pages and store job postings
		do {
			// Store all job listing elements by anchor
			links = wait.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".jobtitle>strong>a[href]")));
			// Initialize second dimension of String array
			pagesResults[currentPage - 1] = new String[links.size()];
			// Store all job posting links in pageResults array
			for (int i = 0; i < links.size(); i++) {
				pagesResults[currentPage - 1][i] = links.get(i).getAttribute("href");
			}
			// Clear the links list so that it can be initialized again
			links.clear();
			// If there are more pages change the page
			if (currentPage < totalPages) {
				driver.get(pageLinks[currentPage]);
			}
			// Increment the current page
			currentPage++;
			// Continue loop if there are more pages
		} while (currentPage <= totalPages);
		// Iterate through job links and search for keyword inclusion
		for (int i = 0; i < pagesResults.length; i++) {
			for (int j = 0; j < pagesResults[i].length; j++) {
				// if the keyword was not found fail the test
				if (keywordPresent == false) {
					break;
				}
				// Display job posting
				driver.get(pagesResults[i][j]);
				// Store the description elements
				List<WebElement> contents = wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.cssSelector(".app_wrapper>div:nth-child(1)")));
				// Iterate through the description elements and check for
				// presence of keyword
				for (int n = 0; n < contents.size(); n++) {
					keywordPresent = contents.get(n).getText().toLowerCase().contains(keyword.toLowerCase());
				}
			}
		}
		Assert.assertEquals(true, keywordPresent);
	}
}
