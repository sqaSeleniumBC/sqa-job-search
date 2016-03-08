/**
 *   File Name: SelUtil.java<br>
 *
 *   Mallapre, Christian<br>
 *   Java Boot Camp Exercise<br>
 *   Instructor: Jean-francois Nepton<br>
 *   Created: Feb 20, 2016
 *
 */

package com.sqa.jf.util.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * SelUtil //ADDD (description of class)
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
public class SelUtil {

	public enum STRATEGY {
		XPATH, CSS, ID, NAME, CLASSNAME, TAG, TEXT, PTEXT
	}

	public static void gotoAndClick(WebDriver driver, String idLocation) {
		System.out.println("DEFAULT STRATEGY:");
		gotoAndClick(driver, idLocation, STRATEGY.CSS);
	}

	public static void gotoAndClick(WebDriver driver, String idLocation, STRATEGY strategy) {
		System.out.println("STRATEGY:" + strategy);
		switch (strategy) {
		case ID:
			driver.findElement(By.id(idLocation)).click();
			break;
		case XPATH:
			driver.findElement(By.xpath(idLocation)).click();
			break;
		case CSS:
			driver.findElement(By.cssSelector(idLocation)).click();
			break;
		case NAME:
			driver.findElement(By.name(idLocation)).click();
			break;
		case CLASSNAME:
			driver.findElement(By.className(idLocation)).click();
			break;
		case TAG:
			driver.findElement(By.tagName(idLocation)).click();
			break;
		case TEXT:
			driver.findElement(By.linkText(idLocation)).click();
			break;
		case PTEXT:
			driver.findElement(By.partialLinkText(idLocation)).click();
			break;
		}
	}

	public static void superClick(WebDriver driver, String[] locations, STRATEGY[] strategies) {
		for (int i = 0; i < locations.length; i++) {
			gotoAndClick(driver, locations[i], strategies[i]);
		}
	}
}