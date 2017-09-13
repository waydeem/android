package com.somecompanydomain.producthuntapplicationtest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataSetter {
    private List<ProductCard> productCardList;
    private DataExtractor dSDataExtractor = new DataExtractor();
    
    private void processCategories(ArrayList<String> categories) {

    }

    public void setProductCardList() {
        productCardList = new ArrayList<>();
        dSDataExtractor.setCategories();
        dsDataExtractor.parseJSONPosts(dSDataExtractor.getCategories().get(0));
    }
   

    private void parseJSONPosts(String key){
        JSONArray mJSONArray = dSDataExtractor.getPostsByCategory(key);
        for(int i = 0; i < mJSONArray.length(); i++) {
           // mThumbnail = mJSONArray.getJSONObject(i).getJSONObject("thumbnail").getString("image_url");
            try {
            productCardList.add(i, new ProductCard(
                        mJSONArray.getJSONObject(i).getJSONObject("thumbnail").getString("image_url"),
                                mJSONArray.getJSONObject(i).getString("name"),
                                mJSONArray.getJSONObject(i).getString("tagline"),
                                mJSONArray.getJSONObject(i).getString("votes_count")
            ));} catch (JSONException jOE2) {
                jOE2.printStackTrace();
            }
        }
    }
}
