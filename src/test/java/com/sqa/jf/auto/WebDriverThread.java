/**
 * File Name: WedDriverThread.java<br>
 * Nepton, Jean-francois<br>
 * Java Boot Camp Exercise<br>
 * Instructor: Jean-francois Nepton<br>
 * Created: Feb 18, 2016
 */
package com.sqa.jf.auto.config;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.remote.*;

/**
 * WedDriverThread //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 *
 * @author Nepton, Jean-francois
 * @version 1.0.0
 * @since 1.0
 */
public class WebDriverThread {

	private WebDriver webdriver;

	private final String operatingSystem = System.getProperty("os.name");

	private final String systemArchitecture = System.getProperty("os.arch");

	public WebDriver getDriver() throws Exception {
		if (this.webdriver == null) {
			System.out.println(" ");
			System.out.println("Current Operating System: " + this.operatingSystem);
			System.out.println("Current Architecture System: " + this.systemArchitecture);
			System.out.println("Current Browser Selection: Firefox");
			System.out.println(" ");
			this.webdriver = new FirefoxDriver(DesiredCapabilities.firefox());
		}
		return this.webdriver;
	}

	public void quitDriver() {
		if (this.webdriver != null) {
			this.webdriver.quit();
			this.webdriver = null;
		}
	}
}
