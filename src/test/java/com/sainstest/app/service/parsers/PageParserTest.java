package com.sainstest.app.service.parsers;

import com.sainstest.app.service.parsers.constants.HtmlElements;
import org.json.JSONException;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PageParserTest {

    private PageParser pageParser;

    private final static String EXPECTED_LIST_OF_PRICES_PER_UNIT = "[ £1.75,  £1.75,  £1.75,  £1.50,  £3.25,  £2.00,  £2.50,  £2.50,  £1.50,  £2.50,  £1.50,  £3.50,  £2.75,  £2.50,  £2.50,  £1.75,  £4.00]";
    private final static String EXPECTED_LIST_OF_PRODUCT_TITLES = "[ Sainsbury's Strawberries 400g ,  Sainsbury's Blueberries 200g ,  Sainsbury's Raspberries 225g ,  Sainsbury's Blackberries, Sweet 150g ,  Sainsbury's Blueberries 400g ,  Sainsbury's Blueberries, SO Organic 150g ,  Sainsbury's Raspberries, Taste the Difference 150g ,  Sainsbury's Cherries 400g ,  Sainsbury's Blackberries, Tangy 150g ,  Sainsbury's Strawberries, Taste the Difference 300g ,  Sainsbury's Cherry Punnet 200g ,  Sainsbury's Mixed Berries 300g ,  Sainsbury's Mixed Berry Twin Pack 200g ,  Sainsbury's Redcurrants 150g ,  Sainsbury's Cherry Punnet, Taste the Difference 200g ,  Sainsbury's Blackcurrants 150g ,  Sainsbury's British Cherry & Strawberry Pack 600g ]";
    private final static String EXPECTED_LIST_OF_KCAL_PER_100G = "[33, 45, 32, 32, 45, 45, 32, 52, 32, 0, 33, 27, 52, , , 71, 48]";

    @Before
    public void setup() throws IOException {
        pageParser = PageParser.getPageParser("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html");
    }

    @Test
    public void shouldReturnNullPageParserForNullUrlString() throws IOException{
        Assert.assertNull(PageParser.getPageParser(null));
    }

    @Test(expected = IOException.class)
    public void shouldThrowIOExceptionForNonExistingUrl() throws IOException {
        PageParser.getPageParser("https://jsainsburyplc.github.io/serverside-test/abcd");
    }

    @Test
    public void shouldReturnCorrectListOfPricesPerUnit() throws JSONException {
        List pricesPerUnitList = pageParser.getPricesPerUnit();
        JSONAssert.assertEquals(EXPECTED_LIST_OF_PRICES_PER_UNIT, pricesPerUnitList.toString(), true);
    }

    @Test
    public void shouldReturnEmptyListOfPricesPerUnitForMissingHTMLTag() {
        Document doc = pageParser.getDocument();
        doc.select(HtmlElements.P_PRICE_PER_UNIT).remove();

        List pricesPerUnitList = pageParser.getPricesPerUnit();
        Assert.assertTrue(pricesPerUnitList.isEmpty());
    }

    @Test
    public void shouldReturnCorrectProductTitlesList() {
        List titlesList = pageParser.getProductTitles();
        Assert.assertEquals(EXPECTED_LIST_OF_PRODUCT_TITLES, titlesList.toString());
    }

    @Test
    public void shouldReturnEmptyListOfProductTitlesForMissingProductTitleHTMLTag() {
        Document doc = pageParser.getDocument();
        doc.select(HtmlElements.DIV_PRODUCT_TITLE).remove();

        List titlesList = pageParser.getProductTitles();
        Assert.assertTrue(titlesList.isEmpty());
    }

    @Test
    public void shouldReturnEmptyListOfProductTitlesForMissingALinkTag() {
        Document doc = pageParser.getDocument();
        doc.select(HtmlElements.DIV_PRODUCT_TITLE).select(HtmlElements.H3_HEADING_DIRECT_A_LINK).remove();

        List titlesList = pageParser.getProductTitles();
        Assert.assertTrue(titlesList.isEmpty());
    }

    @Test
    public void shouldReturnCorrectProductDescriptionListForProducts() throws IOException {
        List productsDescriptionList = pageParser.getProductsDescription(pageParser.getProductLinksToDetails());
        Assert.assertEquals(EXPECTED_LIST_OF_PRODUCT_TITLES, productsDescriptionList.toString());
    }

    @Test
    public void shouldReturnEmptyListOfProductDescriptionForMissingAlinksList() throws IOException {
        List<String> alinks = null;

        List productDescriptionsList = pageParser.getProductsDescription(alinks);
        Assert.assertTrue(productDescriptionsList.isEmpty());
    }

    @Test(expected = IOException.class)
    public void shouldThrowIOExceptionForMalformedLinkToProductDetails() throws IOException{
        List<String> alinks = new ArrayList<String>();
        alinks.add("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/abcd");

        pageParser.getProductsDescription(alinks);
    }

    @Test
    public void shouldReturnCorrectListOfKcalPer100gList() throws IOException {
        List productKcalPer100gList = pageParser.getKcalPer100gForProducts(pageParser.getProductLinksToDetails());
        Assert.assertEquals(EXPECTED_LIST_OF_KCAL_PER_100G, productKcalPer100gList.toString());
    }
}
