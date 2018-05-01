package com.sainstest.app.service;

import com.sainstest.app.service.constants.SainsAppConstants;
import com.sainstest.app.service.utils.TestUtils;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class SainsAppServiceTest {

    private SainsAppService service;

    private static final String EXPECTED_PRODUCTS_JSON = "{\"results\":[{\"title\":\"product name\",\"kcal_per_100g\":\"100kcal\",\"unit_price\":\"1.10\",\"description\":\"some description\"}],\"total\":1.10}";

    @Before
    public void setup() {
        service = new SainsAppService(TestUtils.getSampleProductDetailsDTO());
    }

    @Test
    public void shouldReturnExpectedJsonOfProducts() throws JSONException {
        String actualJson = service.getListOfProductsAsJson();
        JSONAssert.assertEquals(EXPECTED_PRODUCTS_JSON, actualJson, true);
    }

    @Test
    public void shouldReturnEmptyJsonForNullDTO() throws JSONException {
        String actualJson = new SainsAppService(null).getListOfProductsAsJson();
        JSONAssert.assertEquals(SainsAppConstants.EMPTY_JSON_VALUE, actualJson, true);
    }
}
