package com.automation.web.tests;

import com.automation.web.pages.HomePage;
import org.testng.annotations.Test;

/**
 * Class for the carnival test.
 * @author juan.montes
 */
public class TestSuite extends BaseTest {

   @Test
    public void test() {
        log.info("Get Home Page");
        HomePage homePage = getHomePage();
    }

}
