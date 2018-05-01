package com.sainstest.app.service.utils;

import com.sainstest.app.service.constants.SainsAppConstants;
import org.jsoup.helper.StringUtil;

import java.util.List;

public final class ProductsUtils {

    private ProductsUtils() {}


    public static String convertFromRelativeLink(String link, String baseUrl) {
        if (StringUtil.isBlank(link) || StringUtil.isBlank(baseUrl)) {
            return null;
        }

        return baseUrl + link.replace("../", "");
    }

    public static double calculateTotalValueOfProducts(List<String> productPricesPerUnit) {
        if (productPricesPerUnit == null) {
            return 0.0;
        }
        double total = 0.0;

        for (String productPrice : productPricesPerUnit) {
            productPrice = removeCurrencyChar(productPrice);
            double priceAsDouble = (!StringUtil.isBlank(productPrice)) ? Double.valueOf(productPrice) : 0.0;
            total += priceAsDouble;
        }
        return total;
    }

    private static String removeCurrencyChar(String price) {
        if (price == null) {
            return SainsAppConstants.EMPTY_STRING;
        }

        return price.replace(SainsAppConstants.DEFAULT_CURRENCY, SainsAppConstants.EMPTY_STRING);
    }
}
