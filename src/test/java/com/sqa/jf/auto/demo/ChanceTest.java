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
		return new Object[][] { new Object[] { "QA" }, new Object[] { "Analyst" } };
	}

	@Test(dataProvider = "keywordData")
	public void verifyKeywordTest(String keyword) {
		boolean keywordPresent = true;
		driver.get(base_url);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#one_word"))).sendKeys(keyword);
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		List<WebElement> links = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".jobtitle>strong>a[href]")));
		System.out.println(links.size());
		String[] searchResults = new String[links.size()];
		for (int i = 0; i < links.size(); i++) {
			searchResults[i] = links.get(i).getAttribute("href");
		}
		for (String list : searchResults) {
			if (keywordPresent == false) {
				break;
			}
			driver.get(list);
			List<WebElement> contents = wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.cssSelector(".app_wrapper>div:nth-child(1)")));
			for (int n = 0; n < contents.size(); n++) {
				keywordPresent = contents.get(n).getText().toLowerCase().contains(keyword.toLowerCase());
			}
		}
		Assert.assertEquals(true, keywordPresent);
	}
}
