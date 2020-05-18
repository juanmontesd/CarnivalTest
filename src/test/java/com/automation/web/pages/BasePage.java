package com.automation.web.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Parent of the other classes of pages.
 * @author juan.montes
 */
public class BasePage {
	
	private WebDriver driver;
	private WebDriverWait wait;
	protected Logger log = Logger.getLogger(BasePage.class);
	
	/**
	 * Constructor.
	 * @param pDriver WebDriver
	 */
	public BasePage (WebDriver pDriver) {
		PageFactory.initElements(pDriver, this);
		wait = new WebDriverWait(pDriver, 20);
		driver = pDriver;
	}

	/**
	 * Get the web driver wait.
	 * @return {@link WebDriverWait}
	 */
	protected WebDriverWait getWait() {
		return wait;
	}

	/**
	 * Get the  web driver.
	 * @return {@link WebDriver}
	 */
	protected WebDriver getDriver() {
		return driver;
	}
	
	/**
	 * Close the web driver.
	 */
	public void dispose() {
		if (driver != null) {
			driver.quit();
		}
	}

	/**
	 * Wait element to be visible.
	 * @param element WebElement
	 */
	protected void waitElementVisibility(WebElement element) {
		getWait().until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Wait for the element by clickable.
	 * @param element WebElement
	 */
	protected void waitToBeClickable(WebElement element) {
		getWait().until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * Wait and click on the element.
	 * @param element WebElement
	 */
	protected void click(WebElement element) {
		waitToBeClickable(element);
		element.click();
	}

	protected void tap(WebElement element) {
		Actions action = new Actions(getDriver());
		action.moveToElement(element).click().build().perform();
	}

	/**
	 * Wait element to be visible.
	 * @param elements list WebElement
	 */
	protected void waitElementsVisibility(List<WebElement> elements) {
		getWait().until(ExpectedConditions.visibilityOfAllElements(elements));
	}

	/**
	 * Wait to refresh and visible of a element.
	 * @param elements List WebElement
	 */
	public void waitVisibilityRefreshed(List<WebElement> elements) {
		getWait().until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(elements)));
	}

	/**
	 * Change slide bar value.
	 * @param element WebElement
	 * @param xOffset int x offset
	 * @param yOffset int y offset
	 */
	protected void changeSlideBarValue(WebElement element, int xOffset, int yOffset) {
		waitElementVisibility(element);
		Actions action = new Actions(getDriver());
		action.dragAndDropBy(element, xOffset, yOffset).build().perform();
	}

	/**
	 * Get attribute value of an element.
	 * @param element WebElement
	 * @param attribute string attribute
	 * @return string value
	 */
	protected String getAttribute(WebElement element, String attribute) {
		waitElementVisibility(element);
		return element.getAttribute(attribute);
	}

	/**
	 * Wait and get text.
	 * @param element WebElement
	 * @return string text
	 */
	protected String getText(WebElement element) {
		waitElementVisibility(element);
		return element.getText();
	}
	
}
