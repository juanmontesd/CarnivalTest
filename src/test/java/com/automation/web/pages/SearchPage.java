package com.automation.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @FindBy(className = "sfn-nav__filter-title")
    private WebElement filterMenuTitle;

    @FindBy(id = "sfn-nav-pricing")
    private WebElement filterPricingButton;

    @FindBy(className = "rzslider")
    private WebElement sliderBar;

    @FindBy(className = "rz-pointer-min")
    private WebElement minPrice;

    @FindBy(className = "rz-pointer-max")
    private WebElement maxPrice;

    @FindBy(className = "sfp-reset__button")
    private WebElement resetPricingFilter;

    @FindBy(className = "sfn-nav__sort-title")
    private WebElement sortMenuTitle;

    @FindBy(id = "sfn-nav-sort-pricing")
    private WebElement sortPricingButton;

    @FindBy(className = "sfn-nav__sort-pricing-options")
    private List<WebElement> sortPricingOptions;

    @FindBy(className = "sbsc-container__reset-selections")
    private WebElement resetSearch;

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
                    min <= Integer.parseInt(getText(cruiseDuration)) &&
                    max >= Integer.parseInt(getText(cruiseDuration)));
        } else {
            int min = Integer.parseInt(duration.split("\"ROM")[0]);
            log.info("Validate duration range: " + min + "+ Days");
            waitElementsVisibility(cruisesDuration);
            return cruisesDuration.stream().allMatch(cruiseDuration ->
                    min <= Integer.parseInt(getText(cruiseDuration)));
        }
    }

    /**
     * Validate if all the cruise go to the destination port.
     * @param destination string expected destination
     * @return true if all the cruise go to the destination
     */
    public boolean verifyDestination(String destination) {
        waitElementsVisibility(cruisesTitle);
        return cruisesTitle.stream().allMatch(cruiseTitle -> getText(cruiseTitle).contains(destination.toUpperCase()));
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
            if((flag && Integer.parseInt(getText(cruisesPrice.get(i)))
                    > Integer.parseInt(getText(cruisesPrice.get(i + 1))))
                    || (!flag && Integer.parseInt(getText(cruisesPrice.get(i)))
                    > Integer.parseInt(getText(cruisesPrice.get(i + 1))))) return false;
        }
        return true;
    }

    /**
     * Validate filter menu is displayed;
     * @return true if the menu is displayed
     */
    public boolean filterMenuIsDisplayed() {
        log.info("Validate filter menu is displayed");
        waitElementVisibility(resultsContainer);
        return filterMenuTitle.isDisplayed() && getText(filterMenuTitle).equals("Filter By:");
    }

    /**
     * Validate filter pricing option is displayed;
     * @return true if is displayed
     */
    public boolean filterPricingIsDisplayed() {
        log.info("Filter pricing is displayed");
        waitElementVisibility(resultsContainer);
        return filterPricingButton.isDisplayed();
    }

    /**
     * Click in filter pricing option
     */
    public void clickPricingFilter() {
        log.info("Click pricing filter option");
        click(filterPricingButton);
    }

    /**
     * Validate pricing slide bar is displayed.
     * @return true if is displayed
     */
    public boolean pricingSlideBarIsDisplayed() {
        log.info("Slide var is displayed");
        waitElementVisibility(sliderBar);
        return minPrice.isDisplayed() && maxPrice.isDisplayed() && sliderBar.isDisplayed();
    }

    /**
     * Change minimum price filter value.
     * @param addValue int increase value more than 10
     */
    public void changeMinValue(int addValue) {
        log.info("Change minimum price");
        int newPrice = Integer.parseInt(getAttribute(minPrice, "aria-valuenow")) + addValue;
        while (Integer.parseInt(getAttribute(minPrice, "aria-valuenow")) < newPrice) {
            changeSlideBarValue(minPrice, 20, minPrice.getLocation().y);
        }
    }

    /**
     * Change maximum price filter value.
     * @param minusValue int decrease value more than 10
     */
    public void changeMaxValue(int minusValue) {
        log.info("Change maximum price");
        int newPrice = Integer.parseInt(getAttribute(maxPrice, "aria-valuenow")) - minusValue;
        while (Integer.parseInt(getAttribute(maxPrice, "aria-valuenow")) > newPrice) {
            changeSlideBarValue(maxPrice, -20, minPrice.getLocation().y);
        }
    }

    /**
     * Click on reset pricing filter.
     */
    public void clickResetPriceFilter() {
        log.info("Click reset pricing filter");
        click(resetPricingFilter);
    }

    /**
     * Verify that the cruises options being in to the range of the filter.
     * @return true if price are in the range.
     */
    public boolean verifyPriceRange() {
        waitVisibilityRefreshed(cruisesInfo);
        int min = Integer.parseInt(getAttribute(minPrice, "aria-valuenow"));
        int max = Integer.parseInt(getAttribute(maxPrice, "aria-valuenow"));
        log.info("Verify price range: " + min + " - " + max);
        waitElementVisibility(resetSearch);
        waitElementsVisibility(cruisesPrice);
        return cruisesPrice.stream().allMatch(price -> Integer.parseInt(getText(price)) >= min
                && Integer.parseInt(getText(price)) <= max);
    }

    /**
     * Validate sort menu is displayed;
     * @return true if the menu is displayed
     */
    public boolean sortMenuIsDisplayed() {
        log.info("Validate sort menu is displayed");
        waitElementVisibility(resultsContainer);
        return sortMenuTitle.isDisplayed() && getText(sortMenuTitle).equals("Sort By:");
    }

    /**
     * Validate sort pricing option is displayed;
     * @return true if is displayed
     */
    public boolean sortPricingIsDisplayed() {
        log.info("sort pricing is displayed");
        waitElementVisibility(resultsContainer);
        return sortPricingButton.isDisplayed();
    }

    /**
     * Click in sort pricing option
     */
    public void clickPricingSort() {
        log.info("Click pricing sort option");
        click(sortPricingButton);
    }

    /**
     * Validate order option is selected.
     * @param option string sort option
     * @return true if is selected
     */
    public boolean sortPricingOptionIsSelected(String option) {
        log.info("Validate sort price selected option");
        waitElementsVisibility(sortPricingOptions);
        Optional<WebElement> value = sortPricingOptions.stream()
                .filter(sortOption -> getText(sortOption).equals(option)).findFirst();
        return value.map(WebElement::isSelected).orElse(false);
    }

    /**
     * Verify sort options.
     * @param options List of sort options
     * @return true if the options are the expected
     */
    public boolean verifySortPricingOptions(List<String> options) {
        log.info("Verify sort price options");
        waitElementsVisibility(sortPricingOptions);
        List<String> sortOptions = new ArrayList<>();
        sortPricingOptions.forEach(option -> sortOptions.add(getText(option)));
        return sortOptions.stream().sorted().equals(options.stream().sorted());
    }

    /**
     * Select order pricing option.
     * @param option string sort option
     */
    public void selectSortPricingOption(String option) {
        log.info("Select sort price option");
        waitElementsVisibility(sortPricingOptions);
        Optional<WebElement> value = sortPricingOptions.stream()
                .filter(sortOption -> getText(sortOption).equals(option)).findFirst();
        value.ifPresent(this::click);
    }

    /**
     * Select first cruise.
     * @return {@link ItineraryPage}
     */
    public ItineraryPage selectFirstCruise() {
        log.info("Select first cruise");
        waitElementVisibility(resultsContainer);
        waitElementsVisibility(cruisesLearnMoreButton);
        click(cruisesLearnMoreButton.get(0));
        return new ItineraryPage(getDriver());
    }

}
