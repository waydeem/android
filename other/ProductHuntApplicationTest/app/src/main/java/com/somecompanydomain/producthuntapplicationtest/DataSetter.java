package com.somecompanydomain.producthuntapplicationtest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataSetter {
    private List<ProductCard> productCardList;
    //private HashMap<String, ProductCard> productCardLists;
    private DataExtractor dSDataExtractor = new DataExtractor();
    
    private void processCategories(ArrayList<String> categories) {

    }

    public void setProductCardList() {
        productCardList = new ArrayList<>();
        dSDataExtractor.setCategories();
        dSDataExtractor.setPostsForCategory(dSDataExtractor.getCategories().get(0));
    }

    public void setProductCardList(String category) {

        dSDataExtractor.setPostsForCategory(category);
    }
   
        private void parseJSONPosts(String key){
        JSONArray mJSONArray = dSDataExtractor.getPostsByCategory(key);
        for(int i = 0; i < mJSONArray.length(); i++) {
           // mThumbnail = mJSONArray.getJSONObject(i).getJSONObject("thumbnail").getString("image_url");
            try {
            productCardList.add(i, new ProductCard( //вот тут доделать помещение карточек в объект по ключику
                        mJSONArray.getJSONObject(i).getJSONObject("thumbnail").getString("image_url"),
                                mJSONArray.getJSONObject(i).getString("name"),
                                mJSONArray.getJSONObject(i).getString("tagline"),
                                mJSONArray.getJSONObject(i).getString("votes_count")
            ));} catch (JSONException jOE2) {
                jOE2.printStackTrace();
            }
        }
    }

 /*   private void parseJSONPosts(String key){
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
    } */
}
