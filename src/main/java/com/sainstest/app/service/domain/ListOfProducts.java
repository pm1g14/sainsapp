package com.sainstest.app.service.domain;

import java.util.List;

public final class ListOfProducts {

    private final List<Product> results;
    private final double total;

    public ListOfProducts(List<Product> results, double total) {
        this.results = results;
        this.total = total;
    }

    public List<Product> getResults() {
        return results;
    }

    public double getTotal() {
        return total;
    }
}
