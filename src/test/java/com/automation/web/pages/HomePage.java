package com.automation.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Optional;

/**
 * Class for interact with the home page.
 * @author juan.montes
 */
public class HomePage extends BasePage {

    @FindBy(className = "vifp-close")
    private WebElement closePopUp;

    @FindBy(className = "vifp-sweeps-modal")
    private WebElement popUp;

    @FindBy(id = "cdc-destinations")
    private WebElement destinationButton;

    @FindBy(id = "cdc-durations")
    private WebElement durationButton;

    @FindBy(className = "cdc-filter-button")
    private List<WebElement> filterOptions;

    @FindBy(css = ".cdc-filters-tab--searchcta a")
    private WebElement searchButton;

    /**
     * Constructor.
     * @param driver WebDriver
     * @param url String
     */
    public HomePage(WebDriver driver, String url) {
        super(driver);
        driver.get(url);
    }

    /**
     * Close first pop up.
     */
    public void closeSingUpPopUp() {
        log.info("Close Pop Up");
        waitElementVisibility(popUp);
        tap(closePopUp);
    }

    /**
     * Select destination.
     * @param destination string destination port
     * @return {@link HomePage}
     */
    public HomePage setDestination(String destination) {
        log.info("Set sail to: " + destination);
        click(destinationButton);
        waitElementsVisibility(filterOptions);
        Optional<WebElement> filterOption = filterOptions.stream()
                .filter(option -> destination.equals(option.getText())).findFirst();
        filterOption.ifPresent(this::click);
        return this;
    }

    /**
     * Select duration.
     * @param duration string duration range time
     * @return {@link HomePage}
     */
    public HomePage setDuration(String duration) {
        log.info("Set Duration: " + duration);
        click(durationButton);
        waitElementsVisibility(filterOptions);
        Optional<WebElement> filterOption = filterOptions.stream()
                .filter(option -> duration.equals(option.getText())).findFirst();
        filterOption.ifPresent(this::click);
        return this;
    }

    /**
     * Search cruises.
     * @return {@link SearchPage}
     */
    public SearchPage searchCruises() {
        log.info("Click search button");
        click(searchButton);
        return new SearchPage(getDriver());
    }

}
