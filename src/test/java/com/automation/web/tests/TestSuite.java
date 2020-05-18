package com.automation.web.tests;

import com.automation.web.data.Data;
import com.automation.web.data.Options;
import com.automation.web.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Class for the carnival test.
 * @author juan.montes
 */
public class TestSuite extends BaseTest {

    @AfterMethod
    @Parameters({"url"})
    public void launchHomePage(String url) {
        driver.getDriver().get(url);
    }

   @Test(description = "TC-001: Search cruise default view as grid and order cheapest first",
           dataProviderClass = Data.class,
           dataProvider = "searchOptions")
   public void testCaseSearch(Options options) {
        log.info("Search for the cruises");
        SearchPage searchPage = getHomePage().setDestination(options.getDestination())
                .setDuration(options.getDuration())
                .searchCruises();
        log.info("Verify search page");
        Assert.assertTrue(searchPage.verifyIsDisplayedInGrid(), "NOT IN A GRID");
        Assert.assertTrue(searchPage.verifyTwoColumnsGrid(), "NOT TWO COLUMNS GRID");
        Assert.assertTrue(searchPage.allInfoAreDisplayed(), "NOT ALL INFO IS DISPLAYED");
        Assert.assertTrue(searchPage.verifyDestination(options.getDestination()),
                "NOT ALL THE CRUISE GO TO THE DESTINATION");
        Assert.assertTrue(searchPage.verifyTimeIsBetweenExpected(options.getDuration()),
                "NOT ALL THE CRUISE ARE IN THE DURATION RANGE");
        Assert.assertTrue(searchPage.verifyOrderByPricing(true), "NOT ORDER FROM CHEAPEST TO EXPENSIVE");
    }

    @Test(description = "TC-002 Filter the result of the search by pricing",
            dataProviderClass = Data.class,
            dataProvider = "searchOptions")
    public void testFilterSearchResult(Options options) {
        log.info("Precondition previous search for the cruises");
        SearchPage searchPage = getHomePage().setDestination(options.getDestination())
                .setDuration(options.getDuration())
                .searchCruises();
        log.info("Verify filter menu");
        Assert.assertTrue(searchPage.filterMenuIsDisplayed(), "FILTER MENU NOT DISPLAYED");
        Assert.assertTrue(searchPage.filterPricingIsDisplayed(), "FILTER PRICE BUTTON NIT DISPLAYED");
        log.info("Open filter pricing options");
        searchPage.clickPricingFilter();
        Assert.assertTrue(searchPage.pricingSlideBarIsDisplayed(), "SLIDE BAR NOT DISPLAYED");
        log.info("Decrease maximum value");
        searchPage.changeMaxValue(100);
        Assert.assertTrue(searchPage.verifyPriceRange(), "PRICES NOT IN THE EXPECTED RANGE");
        log.info("Reset range price");
        searchPage.clickResetPriceFilter();
        Assert.assertTrue(searchPage.verifyPriceRange(), "PRICES NOT IN THE EXPECTED RANGE");
        log.info("Increase minimum value");
        searchPage.changeMinValue(100);
        Assert.assertTrue(searchPage.verifyPriceRange(), "PRICES NOT IN THE EXPECTED RANGE");
        log.info("Decrease maximum value");
        searchPage.changeMaxValue(100);
        Assert.assertTrue(searchPage.verifyPriceRange(), "PRICES NOT IN THE EXPECTED RANGE");
    }

}
