package com.somecompanydomain.producthuntapplicationtest;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class DataSetter {
    private List<ProductCard> productCardList;
    private DataExtractor dSDataExtractor = new DataExtractor();
    private void processCategories(ArrayList<String> categories) {

    }

    private void setProductCardList(String category) {
        productCardList = new ArrayList<>();
        dSDataExtractor.setCategories();
    }
}
