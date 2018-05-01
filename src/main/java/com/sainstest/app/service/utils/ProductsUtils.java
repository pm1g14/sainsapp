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

        return baseUrl + link.replace("../", SainsAppConstants.EMPTY_STRING);
    }


    /**
     * Calculates the total value of products given the product price per unit list.
     *
     * @param productPricesPerUnit  The products price per unit list.
     *
     * @return double.
     */
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
        return round(total, 2);
    }


    /**
     * Removes the currency char from the price string.
     *
     * @param price  The price.
     *
     * @return String.
     */
    private static String removeCurrencyChar(String price) {
        if (price == null) {
            return SainsAppConstants.EMPTY_STRING;
        }

        return price.replace(SainsAppConstants.DEFAULT_CURRENCY, SainsAppConstants.EMPTY_STRING);
    }


    /**
     * Returns the double with 2 decimal places.
     *
     * @param value   The value to be converted.
     * @param places  The decimal places.
     *
     * @return double.
     */
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
