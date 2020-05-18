package com.automation.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Class for interact with the search page.
 * @author juan.montes
 */
public class SearchPage extends BasePage {

    @FindBy(className = "ccs-search-results")
    private WebElement resultsContainer;

    @FindBy(css = "ccl-view-result-grid")
    private WebElement gridContainer;

    @FindBy(css = "ccl-view-result-grid-item")
    private List<WebElement> cruisesInfo;

    @FindBy(className = "cgc-cruise-glance__main-text")
    private List<WebElement> cruisesTitle;

    @FindBy(className = "cgc-cruise-glance__days")
    private List<WebElement> cruisesDuration;

    @FindBy(className = "vrgf-price-box__price-value")
    private List<WebElement> cruisesPrice;

    @FindBy(className = "vrgf-learn-more__text")
    private List<WebElement> cruisesLearnMoreButton;

    /**
     * Constructor.
     * @param driver WebDriver
     */
    public SearchPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Validate if teh result is showing in a grid.
     * @return true if is displayed de grid
     */
    public boolean verifyIsDisplayedInGrid() {
        log.info("Validate if teh result is showing in a grid");
        waitElementVisibility(resultsContainer);
        return gridContainer.isDisplayed();
    }

    /**
     * Validate if the grid present two columns.
     * @return true if the grid have two columns
     */
    public boolean verifyTwoColumnsGrid() {
        log.info("Validate if the grid present two columns");
        waitElementVisibility(gridContainer);
        waitElementsVisibility(cruisesInfo);
        int firstColumn = 0;
        int secondColumn = 0;
        int y = 0;
        for (int i = 0; i < cruisesInfo.size(); i++) {
            if (i == 0) firstColumn = cruisesInfo.get(i).getLocation().x;
            if (i == 1) secondColumn = cruisesInfo.get(i).getLocation().x;
            if (i % 2 == 0) {
                y  = cruisesInfo.get(i).getLocation().y;
                if (firstColumn != cruisesInfo.get(i).getLocation().x)
                    return false;
            } else if (y != cruisesInfo.get(i).getLocation().y ||
                    secondColumn != cruisesInfo.get(i).getLocation().x)
                return false;
        }
        return firstColumn < secondColumn || firstColumn !=0 && secondColumn == 0;
    }

    /**
     * Validate if all the cruise cards are displayed with all the info.
     * @return true if all the card are displayed with the info
     */
    public boolean allInfoAreDisplayed() {
        log.info("Validate cards and info are displayed");
        waitElementVisibility(gridContainer);
        return cruisesInfo.stream().allMatch(WebElement::isDisplayed) &&
                cruisesTitle.stream().allMatch(WebElement::isDisplayed) &&
                cruisesDuration.stream().allMatch(WebElement::isDisplayed) &&
                cruisesPrice.stream().allMatch(WebElement::isDisplayed) &&
                cruisesLearnMoreButton.stream().allMatch(WebElement::isDisplayed) &&
                cruisesInfo.size() == cruisesDuration.size() &&
                cruisesInfo.size() == cruisesPrice.size() &&
                cruisesInfo.size() == cruisesTitle.size() &&
                cruisesInfo.size() == cruisesLearnMoreButton.size();
    }

    /**
     * Validate if the result are between the duration expected.
     * @param duration string durations range time
     * @return true if all the cruises are in the range
     */
    public boolean verifyTimeIsBetweenExpected(String duration) {
        if (duration.contains("-")) {
            int min = Integer.parseInt(duration.split(" ")[0]);
            int max = Integer.parseInt(duration.split(" ")[2]);
            log.info("Validate duration range: " + min + " - " + max + " Days");
            waitElementsVisibility(cruisesDuration);
            return cruisesDuration.stream().allMatch(cruiseDuration ->
                    min <= Integer.parseInt(cruiseDuration.getText()) &&
                    max >= Integer.parseInt(cruiseDuration.getText()));
        } else {
            int min = Integer.parseInt(duration.split("\"ROM")[0]);
            log.info("Validate duration range: " + min + "+ Days");
            waitElementsVisibility(cruisesDuration);
            return cruisesDuration.stream().allMatch(cruiseDuration ->
                    min <= Integer.parseInt(cruiseDuration.getText()));
        }
    }

    /**
     * Validate if all the cruise go to the destination port.
     * @param destination string expected destination
     * @return true if all the cruise go to the destination
     */
    public boolean verifyDestination(String destination) {
        waitElementsVisibility(cruisesTitle);
        return cruisesTitle.stream().allMatch(cruiseTitle -> cruiseTitle.getText().contains(destination.toUpperCase()));
    }

    /**
     * Verify if the pricing order are correct.
     * @param flag true (Cheapest to Expensive) and false (Expensive to Cheapest)
     * @return true if the order is correct
     */
    public boolean verifyOrderByPricing(boolean flag) {
        if (flag) log.info("Validate order Cheapest to Expensive");
        else log.info("Validate order Expensive to Cheapest");
        waitElementsVisibility(cruisesPrice);
        for (int i = 0; i < cruisesPrice.size() - 1; i++) {
            if((flag && Integer.parseInt(cruisesPrice.get(i).getText())
                    > Integer.parseInt(cruisesPrice.get(i + 1).getText()))
                    || (!flag && Integer.parseInt(cruisesPrice.get(i).getText())
                    > Integer.parseInt(cruisesPrice.get(i + 1).getText()))) return false;
        }
        return true;
    }

}
