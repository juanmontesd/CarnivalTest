package com.automation.web.tests;

import com.automation.web.driver.Driver;
import com.automation.web.pages.HomePage;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

/**
 * Parent of the other classes of test.
 * @author juan.montes
 */
public class BaseTest {
	
	Driver driver;
	
	private HomePage home;
	protected Logger log = Logger.getLogger(BaseTest.class);
	
	
	@BeforeTest(alwaysRun=true)
	@Parameters({"browser", "url"})
	public void beforeTest(String browser, String url) {
		driver = new Driver(browser);
		driver.getDriver().manage().window().maximize();
		home= new HomePage(driver.getDriver(), url);
		home.closeSingUpPopUp();
	}

	@AfterTest(alwaysRun=true)
	public void afterTest() {
		home.dispose();
	}
	
	/**
	 * Get the home page.
	 * @return {@link HomePage}
	 */
	public HomePage getHomePage() {
		return home;
	}

}
