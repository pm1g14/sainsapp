package com.sainstest.app.service.utils;

import com.sainstest.app.service.dto.ProductDetailsDTO;

import java.util.ArrayList;
import java.util.List;

public final class TestUtils {

    private TestUtils() {

    }

    public static ProductDetailsDTO getSampleProductDetailsDTO() {
        List productTitles = new ArrayList<String>();
        productTitles.add("product name");
        List<String> productDescriptions = new ArrayList<String>();
        productDescriptions.add("some description");
        List<String> productsPricesPerUnit = new ArrayList<String>();
        productsPricesPerUnit.add("1.1");
        List<String> productsKcalPer100g = new ArrayList<String>();
        productsKcalPer100g.add("100kcal");

        return new ProductDetailsDTO(productTitles, productDescriptions, productsKcalPer100g, productsPricesPerUnit, 1);
    }
}
