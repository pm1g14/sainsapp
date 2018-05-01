package com.sainstest.app.service.adapters.impl;

import com.sainstest.app.service.adapters.Adapter;
import com.sainstest.app.service.constants.SainsAppConstants;
import com.sainstest.app.service.domain.ListOfProducts;
import com.sainstest.app.service.domain.Product;
import com.sainstest.app.service.dto.ProductDetailsDTO;
import com.sainstest.app.service.utils.ProductsUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter implements Adapter<ListOfProducts, ProductDetailsDTO>{

    public ListOfProducts fromDTO(ProductDetailsDTO dto) {
        if (dto == null) {
            return null;
        }
        List<Product> results = new ArrayList<Product>();

        for (int i = 0; i < dto.getProductsCount() ;++i) {
            String productTitle = SainsAppConstants.EMPTY_STRING;
            String productDescription = SainsAppConstants.EMPTY_STRING;
            String productPricePerUnit = SainsAppConstants.EMPTY_STRING;
            String productKcalPer100g = SainsAppConstants.EMPTY_STRING;

            if (dto.getProductTitles() != null) {
                productTitle = dto.getProductTitles().get(i);
            }
            if (dto.getProductDescriptions() != null) {
                productDescription = dto.getProductDescriptions().get(i);
            }
            if (dto.getProductsPricePerUnit() != null) {
                productPricePerUnit = dto.getProductsPricePerUnit().get(i);
            }
            if (dto.getProductsKcalPer100g() != null) {
                productKcalPer100g = dto.getProductsKcalPer100g().get(i);
            }
            Product product = new Product(productTitle, productKcalPer100g, productPricePerUnit, productDescription);
            results.add(product);
        }
        return new ListOfProducts(results, ProductsUtils.calculateTotalValueOfProducts(dto.getProductsPricePerUnit()));
    }
}
