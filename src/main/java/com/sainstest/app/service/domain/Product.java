package com.sainstest.app.service.domain;

public class Product {

    private final String title;
    private final String kcal_per_100g;
    private final double unit_price;
    private final String description;

    public Product(String title, String kcal_per_100g, double unit_price, String description) {
        this.title = title;
        this.kcal_per_100g = kcal_per_100g;
        this.unit_price = unit_price;
        this.description = description;
    }


    public String getTitle() {
        return title;
    }

    public String getKcal_per_100g() {
        return kcal_per_100g;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public String getDescription() {
        return description;
    }
}
