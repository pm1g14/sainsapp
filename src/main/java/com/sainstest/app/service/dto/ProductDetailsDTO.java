package com.sainstest.app.service.dto;

import java.util.List;

public final class ProductDetailsDTO {

    private List<String> productTitles;
    private List<String> productDescriptions;
    private List<String> productsKcalPer100g;
    private List<String> productsPricePerUnit;
    private int productsCount;

    public ProductDetailsDTO(List<String> productTitles, List<String> productDescriptions, List<String> productsKcalPer100g, List<String> productsPricePerUnit, int productsCount) {
        this.productTitles = productTitles;
        this.productDescriptions = productDescriptions;
        this.productsKcalPer100g = productsKcalPer100g;
        this.productsPricePerUnit = productsPricePerUnit;
        this.productsCount = productsCount;
    }

    public List<String> getProductTitles() {
        return productTitles;
    }

    public List<String> getProductDescriptions() {
        return productDescriptions;
    }

    public List<String> getProductsKcalPer100g() {
        return productsKcalPer100g;
    }

    public List<String> getProductsPricePerUnit() {
        return productsPricePerUnit;
    }

    public int getProductsCount() {
        return productsCount;
    }
}
