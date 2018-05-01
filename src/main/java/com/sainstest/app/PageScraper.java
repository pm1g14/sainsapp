package com.sainstest.app;

import com.sainstest.app.exceptions.SainsAppException;
import com.sainstest.app.exceptions.SainsAppExceptionCodes;
import com.sainstest.app.service.SainsAppService;
import com.sainstest.app.service.dto.ProductDetailsDTO;
import com.sainstest.app.service.parsers.PageParser;

import java.io.IOException;
import java.util.List;

public class PageScraper {

    public static void main(String...args) throws Exception{

        if (args == null) {
            throw new SainsAppException(SainsAppExceptionCodes.NO_URL_PARAM.getMessage());
        }
        PageParser parser = PageParser.getPageParser("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html");

        if (parser != null) {
            SainsAppService service = new SainsAppService(getProductDetailsDTO(parser));
            System.out.println("Products json: " +service.getListOfProductsAsJson());
        } else {
            throw new SainsAppException(SainsAppExceptionCodes.PARSING_FAILURE.getMessage());
        }
    }

    private static ProductDetailsDTO getProductDetailsDTO(PageParser parser) throws IOException {
        List<String> pricesPerUnit = parser.getPricesPerUnit();
        List<String> productTitles = parser.getProductTitles();
        List<String> productLinks = parser.getProductLinksToDetails();
        List<String> productDescriptions = parser.getProductsDescription(productLinks);
        List<String> kcalper100g = parser.getKcalPer100gForProducts(productLinks);

        return new ProductDetailsDTO(productTitles, productDescriptions, kcalper100g, pricesPerUnit, parser.getProducts().size());
    }
}
