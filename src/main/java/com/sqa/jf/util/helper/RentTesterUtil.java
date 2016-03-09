/**
 *   File Name: RentTesterUtil.java<br>
 *
 *   Mallapre, Christian<br>
 *   Java Boot Camp Exercise<br>
 *   Instructor: Jean-francois Nepton<br>
 *   Created: Mar 7, 2016
 *
 */

package com.sqa.jf.util.helper;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * RentTesterUtil //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 *
 * @author Mallapre, Christian
 * @version 1.0.0
 * @since 1.0
 *
 */
public class RentTesterUtil {

	public static int RentTester(WebDriver driver, int totNumResults) {

		// Variable Declaration
		List<WebElement> searchResults = null;
		int num = 0;
		String jobTitle = null;
		// Identify the number of page results
		List<WebElement> pageCount = driver.findElements(By.xpath(".//*[@id='content']/div[3]/ul/li"));

		// Validate the results is not zero, if it is there is nothing to be
		// process for the search input
		if (pageCount.size() != 0)

		{
			for (int i = 1; i <= pageCount.size(); i++) {
				if (i > 1) {

					// Navigate to the second page of the results
					driver.findElement(By.xpath(".//*[@id='content']/div[3]/ul/li[" + i + "]/a")).click();

					// List all the Job found on the next page
					searchResults = driver.findElements(By.className("jobtitle"));

					// Accumulate the total number of Job Results
					totNumResults += searchResults.size();

					// Display all the Job Results on the current page
					for (num = 1; num <= searchResults.size(); num++) {
						jobTitle = driver
								.findElement(By.xpath(".//*[@id='content']/div[2]/div[" + num + "]/div/h2/strong/a"))
								.getText();
						System.out.println("Job Title: " + jobTitle);
					}
				} else {

					// List all the Job found on first page
					searchResults = driver.findElements(By.className("jobtitle"));

					// Save the current number of results on the first page
					totNumResults = searchResults.size();

					// List all the Job Title on the first page
					for (num = 1; num <= totNumResults; num++) {
						jobTitle = driver
								.findElement(By.xpath(".//*[@id='content']/div[2]/div[" + num + "]/div/h2/strong/a"))
								.getText();
						System.out.println("Job Title: " + jobTitle);
					}
				}
			}
		}
		return totNumResults;
	}
}
