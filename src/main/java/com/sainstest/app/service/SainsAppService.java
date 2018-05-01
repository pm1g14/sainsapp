package com.sainstest.app.service;

import com.google.gson.Gson;
import com.sainstest.app.service.adapters.Adapter;
import com.sainstest.app.service.adapters.impl.ProductListAdapter;
import com.sainstest.app.service.constants.SainsAppConstants;
import com.sainstest.app.service.domain.ListOfProducts;
import com.sainstest.app.service.dto.ProductDetailsDTO;

/**
 * The service class.
 */
public class SainsAppService {


    private ProductDetailsDTO dto;


    /**
     * Constructor.
     *
     * @param dto  The product details DTO.
     */
    public SainsAppService(ProductDetailsDTO dto) {
        this.dto = dto;
    }


    /**
     * Gets the list of products in JSON format.
     *
     * @return String.
     */
    public String getListOfProductsAsJson() {
        Adapter<ListOfProducts, ProductDetailsDTO> adapter = new ProductListAdapter();
        return (dto != null) ? new Gson().toJson(adapter.fromDTO(dto)) : SainsAppConstants.EMPTY_JSON_VALUE;
    }
}
