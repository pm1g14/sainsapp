package com.sainstest.app.service.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProductUtilsTest {

   @Test
   public void shouldReturnCorrectLink() {
      String link = ProductsUtils.convertFromRelativeLink("../../../products/abcd", "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/");
      Assert.assertEquals("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/products/abcd", link);
   }

   @Test
   public void shouldReturnCorrectLinkForMissingChars() {
      String link = ProductsUtils.convertFromRelativeLink("products/abcd", "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/");
      Assert.assertEquals("https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/products/abcd", link);
   }

   @Test
   public void shouldReturnCorrectSumOfProductPrices() {
      List<String> prices = new ArrayList<String>();
      prices.add("1.5");
      prices.add("£1.5");
      double total = ProductsUtils.calculateTotalValueOfProducts(prices);
      Assert.assertEquals(3.00, total, 2);
   }

   @Test
   public void shouldReturnZeroTotalPriceForEmptyListOfProductPrices() {
      List<String> prices = new ArrayList<String>();
      prices.add("");
      prices.add("");
      double total = ProductsUtils.calculateTotalValueOfProducts(prices);
      Assert.assertEquals(0.00, total, 2);
   }

   @Test
   public void shouldNotAddUpEmptyPriceToTotal() {
      List<String> prices = new ArrayList<String>();
      prices.add("");
      prices.add("1");
      double total = ProductsUtils.calculateTotalValueOfProducts(prices);
      Assert.assertEquals(1.00, total, 2);
   }
}
