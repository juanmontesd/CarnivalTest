package com.automation.web.tests;

import com.automation.web.data.Data;
import com.automation.web.data.Options;
import com.automation.web.pages.ItineraryPage;
import com.automation.web.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test(description = "TC-004 Verify itinerary detail",
            dataProviderClass = Data.class,
            dataProvider = "searchOptions")
    public void testCaseItineraryDetail(Options options) {
        log.info("Precondition previous search for the cruises");
        SearchPage searchPage = getHomePage().setDestination(options.getDestination())
                .setDuration(options.getDuration())
                .searchCruises();
        log.info("See more info, itinerary detail of first cruise");
        ItineraryPage itineraryPage = searchPage.selectFirstCruise();
        Assert.assertTrue(itineraryPage.bookButtonIsDisplayed(), "BOOK BUTTON IS NOT DISPLAYED");
        Assert.assertTrue(itineraryPage.titleIsDisplayed(), "TITLE IS NOT DISPLAYED");
        Assert.assertTrue(itineraryPage.durationIsDisplayed(), "DURATION IS NOT DISPLAYED");
        Assert.assertTrue(itineraryPage.dateRangeIsDisplayed(), "DATE IS NOT DISPLAYED");
        Assert.assertTrue(itineraryPage.dayCardsAndInfoIsDisplayed(), "DAY CARDS INFO NOT DISPLAYED");
        Assert.assertTrue(itineraryPage.verifyDurationAndNumberOfDays(),
                "NUMBER OF DAYS ARE NOT CORRECT WITH DURATION");
        Assert.assertTrue(itineraryPage.typeInfoIsDisplayed(), "TYPE INFO IS NOT DISPLAYED");
    }

}
