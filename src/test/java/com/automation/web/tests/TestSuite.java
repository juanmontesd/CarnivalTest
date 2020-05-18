package com.automation.web.tests;

import com.automation.web.data.Data;
import com.automation.web.data.Options;
import com.automation.web.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Class for the carnival test.
 * @author juan.montes
 */
public class TestSuite extends BaseTest {

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

}
