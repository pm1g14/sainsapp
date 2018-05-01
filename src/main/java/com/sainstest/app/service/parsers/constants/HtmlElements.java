package com.sainstest.app.service.parsers.constants;

public final class HtmlElements {

    private HtmlElements() {
    }

    public static final String DIV_PRODUCT = "div.product";
    public static final String DIV_PRODUCT_TITLE = "div.productNameAndPromotions";

    public static final String P_PRICE_PER_UNIT = "div.pricingAndTrolleyOptions > div.priceTab > div.pricing > p.pricePerUnit";
    public static final String H3_HEADING_DIRECT_A_LINK = "h3 > a";
    public static final String P_PRODUCT_DESCRIPTION = "div.productText > p";
    public static final String P_PRODUCT_DESCRIPTION_WITH_ITEM_TYPE_GROUP = "div.itemTypeGroupContainer";
    public static final String NUTRITION_TABLE = "table.nutritionTable > tbody > tr.tableRow0 >td";
    public static final String NUTRITION_TABLE_WITHOUT_TABLEROW_ELEMENT = "table.nutritionTable > tbody > tr";
}
