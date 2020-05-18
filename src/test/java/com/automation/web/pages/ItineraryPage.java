package com.automation.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Class for interact with the itinerary page.
 * @author juan.montes
 */
public class ItineraryPage extends BasePage {

    @FindBy(css = "booking-button")
    private WebElement bookButton;

    @FindBy(id = "section-itinerary")
    private WebElement sectionItinerary;

    @FindBy(id = "cruiseDescrGlance")
    private WebElement title;

    @FindBy(css = "#section-itinerary .duration-title span")
    private WebElement duration;

    @FindBy(css = "#section-itinerary .date-range")
    private WebElement dateRange;

    @FindBy(xpath = "//*[@class='day']/../..")
    private List<WebElement> daysContainer;

    @FindBy(className = "day")
    private List<WebElement> days;

    @FindBy(className = "departure-location")
    private List<WebElement> locations;

    @FindBy(className = "departure-time")
    private List<WebElement> times;

    @FindBy(css = ".departure-time ~ .about-cta")
    private List<WebElement> learnMoreButtons;

    @FindBy(css = ".tile[title*='Carnival']")
    private WebElement typeContainer;

    @FindBy(css = ".tile[title*='Carnival'] h3")
    private WebElement titleType;

    @FindBy(css = ".tile[title*='Carnival'] .about-cta")
    private WebElement learnMoreType;

    /**
     * Constructor.
     * @param driver WebDriver
     */
    public ItineraryPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Verify book is displayed.
     * @return true if is displayed
     */
    public boolean bookButtonIsDisplayed() {
        waitElementVisibility(sectionItinerary);
        waitElementsVisibility(daysContainer);
        return bookButton.isDisplayed();
    }

    /**
     * Verify title is displayed.
     * @return true if is displayed
     */
    public boolean titleIsDisplayed() {
        waitElementVisibility(sectionItinerary);
        return title.isDisplayed();
    }

    /**
     * Verify duration is displayed.
     * @return true if is displayed
     */
    public boolean durationIsDisplayed() {
        waitElementVisibility(sectionItinerary);
        return duration.isDisplayed();
    }

    /**
     * Verify date range is displayed.
     * @return true if is displayed
     */
    public boolean dateRangeIsDisplayed() {
        waitElementVisibility(sectionItinerary);
        return dateRange.isDisplayed();
    }

    /**
     * Verify day cards and info is displayed.
     * @return true if is displayed
     */
    public boolean dayCardsAndInfoIsDisplayed() {
        waitElementsVisibility(daysContainer);
        int daysOnPorts = (int) locations.stream().filter(location -> !location.getText().equals("Fun Day At Sea"))
                .count();
        return daysContainer.stream().allMatch(WebElement::isDisplayed) &&
                days.stream().allMatch(WebElement::isDisplayed) &&
                locations.stream().allMatch(WebElement::isDisplayed) &&
                times.stream().allMatch(WebElement::isDisplayed) &&
                learnMoreButtons.stream().allMatch(WebElement::isDisplayed) &&
                daysContainer.size() == days.size() &&
                daysContainer.size() == locations.size() &&
                daysContainer.size() == learnMoreButtons.size() &&
                daysOnPorts == times.size();
    }

    /**
     * Verify type cruise card and info is displayed.
     * @return true if is displayed
     */
    public boolean typeInfoIsDisplayed() {
        waitElementVisibility(typeContainer);
        return titleType.isDisplayed() && learnMoreType.isDisplayed() && typeContainer.isDisplayed();
    }

    /**
     * Verify if the number of days is equal to de duration time plus 1.
     * @return true if the number of days match with the duration time
     */
    public boolean verifyDurationAndNumberOfDays() {
        waitElementVisibility(duration);
        waitElementsVisibility(daysContainer);
        return daysContainer.size() == Integer.parseInt(getText(duration)) + 1;
    }

}
