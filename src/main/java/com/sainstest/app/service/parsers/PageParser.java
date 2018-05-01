package com.sainstest.app.service.parsers;

import com.sainstest.app.service.constants.SainsAppConstants;
import com.sainstest.app.service.parsers.constants.HtmlSelectors;
import com.sainstest.app.service.utils.ProductsUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PageParser {

    private Document doc;
    private String pageUrl;
    private static final String REGEX_ESCAPED_CHARS = "[;ampltg&quos/dkc]";


    /**
     * Gets the page parser.
     *
     * @param url  The url to the html file to parse.
     * @return PageParser.
     *
     * @throws IOException
     */
    public static PageParser getPageParser(String url) throws IOException {
        return (StringUtil.isBlank(url)) ? null : new PageParser(url);
    }


    /**
     * Private constructor.
     *
     * @param url  The url to the html file to parse.
     *
     * @throws IOException
     */
    private PageParser(String url) throws IOException {
        this.pageUrl = url;
        this.doc = Jsoup.connect(pageUrl).timeout(0).get();
    }


    /**
     * Gets the html page as a Document.
     *
     * @return Document.
     */
    public Document getDocument() {
        return this.doc;
    }


    /**
     * Gets the products list from the html page after parsing.
     *
     * @return Elements.
     */
    public Elements getProducts(){
        Elements products = doc.select(HtmlSelectors.DIV_PRODUCT);
        return (products != null) ? products : new Elements();
    }


    /**
     * Retrieves and returns the prices per unit for the products.
     *
     * @return List.
     */
    public List<String> getPricesPerUnit() {
        Elements prices = doc.select(HtmlSelectors.P_PRICE_PER_UNIT);
        List<String> pricesList = new ArrayList<String>();

        if (prices == null) {
            return pricesList;
        }

        for (Element element : prices) {

            if (hasChildren(element)) {
                String pricePerUnit = element.childNodes().iterator().next().toString();
                pricesList.add(pricePerUnit);
            } else {
                pricesList.add(SainsAppConstants.EMPTY_STRING);
            }


        }
        return pricesList;
    }


    /**
     * Retrieves and returns the product titles.
     *
     * @return List.
     */
    public List<String> getProductTitles() {
        List<String> titlesList = new ArrayList<String>();
        Elements headings = doc.select(HtmlSelectors.DIV_PRODUCT_TITLE);

        if (headings == null) {
            return titlesList;
        }
        Elements alinks = headings.select(HtmlSelectors.H3_HEADING_DIRECT_A_LINK);

        for (Element element : alinks) {

            if (hasChildren(element)) {
                String productTitle = element.childNodes().iterator().next().toString();
                productTitle = StringEscapeUtils.unescapeHtml(productTitle);
                titlesList.add(productTitle);
            } else {
                titlesList.add(SainsAppConstants.EMPTY_STRING);
            }
        }
        return titlesList;
    }


    /**
     * Retrieves and returns the products description.
     *
     * @param alinks  The links to the product details page for each product.
     *
     * @return List.
     *
     * @throws IOException
     */
    public List<String> getProductsDescription(List<String> alinks) throws IOException {
        List<String> productDescriptions = new ArrayList<String>();

        if (alinks == null) {
            return productDescriptions;
        }

        Document doc;
        for (String alink : alinks) {
            alink = ProductsUtils.convertFromRelativeLink(alink, SainsAppConstants.BASE_URL);

            if (!StringUtil.isBlank(alink)) {
                doc = Jsoup.connect(alink).get();

                if (doc == null) {
                    return productDescriptions;
                }

                Elements descriptions = getDescriptionElements(doc);

                if (descriptions == null) {
                    return productDescriptions;
                }
                for (Element description : descriptions) {

                    if (hasChildren(description)) {
                        List<Node> nodesList = description.childNodes();

                        if (nodesList == null) {
                            return null;
                        }
                        int i = 0;
                        for (Node node : nodesList) {

                            if (node != null) {

                                if (node.toString().contains(SainsAppConstants.DESCRIPTION) || StringUtil.isBlank(node.toString())) {
                                    ++i;
                                    continue;
                                }
                                node = description.childNodes().get(i);
                                productDescriptions.add(node.toString());
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
        return productDescriptions;
    }


    /**
     * Gets the product links for the product details page.
     *
     * @return List.
     */
    public List<String> getProductLinksToDetails() {
        List<String> alinksList = new ArrayList<String>();
        Elements headings = doc.select(HtmlSelectors.DIV_PRODUCT_TITLE);

        if (headings != null) {
            Elements alinks = headings.select(HtmlSelectors.H3_HEADING_DIRECT_A_LINK);

            for (Element element : alinks) {

                if (element != null && element.attributes() != null && element.attributes().iterator().hasNext() && element.attributes().iterator().next() != null) {
                    String alink = element.attributes().iterator().next().getValue();
                    alinksList.add(alink);
                }
            }
        }
        return alinksList;
    }


    /**
     * Retrieves and returns the kcal per 100g entry for the products.
     *
     * @param alinks  The links to the product details for each product.
     *
     * @return List.
     *
     * @throws IOException
     */
    public List<String> getKcalPer100gForProducts(List<String> alinks) throws IOException{
        List<String> kcalPer100gList = new ArrayList<String>();

        if (alinks == null) {
            return kcalPer100gList;
        }

        Document doc;
        for (String alink : alinks) {
            alink = ProductsUtils.convertFromRelativeLink(alink, "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/");

            if (!StringUtil.isBlank(alink)) {
                doc = Jsoup.connect(alink).get();

                if (doc == null) {
                    return kcalPer100gList;
                }
                Elements nutritionTable = getNutritionTable(doc);
                addKcalEntryToList(kcalPer100gList, nutritionTable);
            }
        }
        return kcalPer100gList;
    }


    /**
     * Adds the kcal entry to the list.
     *
     * @param kcalPer100gList  The kcal list to populate.
     * @param nutritionTable   The nutrition table object from which to retrieve the kcal entry.
     */
    private void addKcalEntryToList(List<String> kcalPer100gList, Elements nutritionTable) {
        if (nutritionTable == null) {
            if (kcalPer100gList.size() < getProducts().size()) {
                kcalPer100gList.add(SainsAppConstants.EMPTY_STRING);
            }
            return;
        }

        for (Element tableElement : nutritionTable) {

            if (hasChildren(tableElement)) {
                addKcalPer100gEntryToList(kcalPer100gList, tableElement);
            }
        }
    }


    /**
     * Gets the nutrition table elements.
     *
     * @param doc  The document to parse.
     *
     * @return Elements.
     */
    private Elements getNutritionTable(Document doc) {
        Elements nutritionTable = null;
        if (hasHtmlElement(doc, HtmlSelectors.NUTRITION_TABLE)) {
            nutritionTable = doc.select(HtmlSelectors.NUTRITION_TABLE);

        } else if (hasHtmlElement(doc, HtmlSelectors.NUTRITION_TABLE_WITHOUT_TABLEROW_ELEMENT)) {
            nutritionTable = doc.select(HtmlSelectors.NUTRITION_TABLE_WITHOUT_TABLEROW_ELEMENT);
        }
        return nutritionTable;
    }


    /**
     * Checks if the given element has children.
     *
     * @param element  The element to check.
     *
     * @return boolean.
     */
    private boolean hasChildren(Element element) {
        return (element != null && element.childNodes() != null &&
                element.childNodes().iterator().hasNext() &&
                element.childNodes().iterator().next() != null);
    }


    /**
     * Checks if the given htmlElement is in the parsed Document.
     *
     * @param doc          The document object to check for the html element.
     * @param htmlElement  The html element to check.
     *
     * @return boolean.
     */
    private boolean hasHtmlElement(Document doc, String htmlElement) {
        return (doc.select(htmlElement) != null && doc.select(htmlElement).size() > 0);
    }


    /**
     * Gets the description elements.
     *
     * @param doc  The parsed document from which to retrieve the description elements.
     *
     * @return Elements.
     */
    private Elements getDescriptionElements(Document doc) {
        Elements descriptions = null;
        if (hasHtmlElement(doc, HtmlSelectors.P_PRODUCT_DESCRIPTION)) {
            descriptions = doc.select(HtmlSelectors.P_PRODUCT_DESCRIPTION);
        } else if (hasHtmlElement(doc, HtmlSelectors.P_PRODUCT_DESCRIPTION_WITH_ITEM_TYPE_GROUP)) {
            descriptions = doc.select(HtmlSelectors.P_PRODUCT_DESCRIPTION_WITH_ITEM_TYPE_GROUP);
        }
        return descriptions;
    }


//    private void addProductDescriptionToList(List<String> productDescriptions, Element description) {
//        Node node = description.childNodes().get(0);
//
//        if (node != null && !StringUtil.isBlank(node.toString())) {
//            if (SainsAppConstants.DESCRIPTION.equalsIgnoreCase(node.toString())) {
//                continue;
//            }
//            productDescriptions.add(node.toString());
//            break;
//        }
//    }


    private void addKcalPer100gEntryToList(List<String> kcalPer100gList, Element tableElement) {
        boolean hasEnergyKcalCell = false;
        List<Node> nodesList = tableElement.childNodes();

        if (nodesList == null) {
            return;
        }

        for (Node node: nodesList) {

            if (node == null) {
                continue;
            }

            String cellContent = node.toString();

            if (cellContent.contains(SainsAppConstants.ENERGY_KCAL)) {
                hasEnergyKcalCell = true;

            } else if (cellContent.contains(SainsAppConstants.KCAL) || hasEnergyKcalCell) {
                cellContent = StringEscapeUtils.escapeHtml(cellContent).replaceAll(REGEX_ESCAPED_CHARS, SainsAppConstants.EMPTY_STRING);
                kcalPer100gList.add(cellContent);
                break;
            }
        }
    }
}
