package com.automation.web.pages;

import org.openqa.selenium.WebDriver;

/**
 * Class for interact with the home page.
 * @author juan.montes
 */
public class HomePage extends BasePage {

    /**
     * Constructor.
     * @param driver WebDriver
     * @param url String
     */
    public HomePage(WebDriver driver, String url) {
        super(driver);
        driver.get(url);
    }

}
