package com.automation.web.data;

import org.testng.annotations.DataProvider;

/**
 * Data provider class.
 * @author juan.montes
 */
public class Data {

    @DataProvider(name = "countries")
    public Object[][] inputData() {
        return new Object[][] {{""}};
    }
}
