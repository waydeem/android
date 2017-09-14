package com.somecompanydomain.producthuntapplicationtest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataExtractor {
    private Handler mHandler;

    List<String> categories;
    HashMap<String,JSONArray> receivedData;

    public HashMap<String, ProductCard> getProductCardLists() {
        return productCardLists;
    }

    private HashMap<String, ProductCard> productCardLists;
    OkHttpClient mClient;
    Request mRequest;
    Response mResponse;
    JSONObject mJSONObject;
    JSONArray mJSONArray;



    public void setCategories() {
        categories = new ArrayList<>();
        receivedData = new HashMap<>();
        productCardLists = new HashMap<>();
        executeAsyncTask("categories");
    }

    public void setPostsForCategory(String category) {
        executeAsyncTask("categories/" + category.toLowerCase() + "/posts");
    }

    public JSONArray getPostsByCategory(String category) {
        if(receivedData.get(category) == null) {
            setPostsForCategory(category);   
        }
        return receivedData.get(category);
    }

    private void executeAsyncTask(final String routePart) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    mClient = new OkHttpClient();
                    mRequest = new Request.Builder()
                            .url("https://api.producthunt.com/v1/" + routePart)
                            .addHeader("Accept", "application/json")
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Authorization", "Bearer 591f99547f569b05ba7d8777e2e0824eea16c440292cce1f8dfb3952cc9937ff")
                            .addHeader("Host", "api.producthunt.com")
                            .build();

                    mResponse = mClient.newCall(mRequest).execute();
                    String responseString = mResponse.body().string();
                    mJSONObject = new JSONObject(responseString);


                    if(routePart.equals("categories")) {
                        mJSONArray = mJSONObject.getJSONArray(routePart);
                        for (int i = 0; i < mJSONArray.length(); i++) {
                            categories.add(i, mJSONArray.getJSONObject(i).getString("name"));
                            productCardLists.put(categories.get(i), null);
                            receivedData.put(categories.get(i), null);
                            System.out.println(categories.get(i));
                        }
                    } else {
                        mJSONArray = mJSONObject.getJSONArray("posts");
                        for(int i = 0; i < mJSONArray.length(); i++) {
                            // mThumbnail = mJSONArray.getJSONObject(i).getJSONObject("thumbnail").getString("image_url");
                            try {
                                productCardLists.put(routePart, new ProductCard(
                                        mJSONArray.getJSONObject(i).getJSONObject("thumbnail").getString("image_url"),
                                        mJSONArray.getJSONObject(i).getString("name"),
                                        mJSONArray.getJSONObject(i).getString("tagline"),
                                        mJSONArray.getJSONObject(i).getString("votes_count"))
                                );} catch (JSONException jOE2) {
                                jOE2.printStackTrace();
                            }
                        }
                    }
                    System.out.println(responseString);

                } catch (IOException iOE) {
                    iOE.printStackTrace();
                } catch (JSONException jOE) {
                    jOE.printStackTrace();
                }
            }
        });
    }

    public List<String> getCategories() {
        return categories;
    }
    
    public Message getMessageForHandler(final Activity mainActivity) {
        final Message msg = new Message();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

              //  mSpinner = (Spinner) findViewById(R.id.spinner);

             //   System.out.println(mDataExtractor.getCategories() + "TATATATATTATA getCategory()");
                
//                mHandler = new Handler() {
//                    @Override
//                    public void handleMessage(Message msg) {
//                        ArrayAdapter<String> msgAdapter = (ArrayAdapter<String>) msg.obj;
//                        spinner.setAdapter(msgAdapter);
//                        }
//                    };
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mainActivity,
                        android.R.layout.simple_spinner_item, categories);

                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                msg.obj = dataAdapter;

            }
        });
        return msg;

    }

    public HashMap<String, JSONArray> getReceivedData() {
        return receivedData;
    }

//    private void executeAsyncTask(final String routePart) {
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//
//                try {
//                    mClient = new OkHttpClient();
//                    mRequest = new Request.Builder()
//                            .url("https://api.producthunt.com/v1/" + routePart)
//                            .addHeader("Accept", "application/json")
//                            .addHeader("Content-Type", "application/json")
//                            .addHeader("Authorization", "Bearer 591f99547f569b05ba7d8777e2e0824eea16c440292cce1f8dfb3952cc9937ff")
//                            .addHeader("Host", "api.producthunt.com")
//                            .build();
//
//                    mResponse = mClient.newCall(mRequest).execute();
//                    String responseString = mResponse.body().string();
//                    mJSONObject = new JSONObject(responseString);
//
//
//                    if(routePart.equals("categories")) {
//                        mJSONArray = mJSONObject.getJSONArray(routePart);
//                        for (int i = 0; i < mJSONArray.length(); i++) {
//                            categories.add(i, mJSONArray.getJSONObject(i).getString("name"));
//                            receivedData.put(categories.get(i), null);
//                            System.out.println(categories.get(i));
//                        }
//                    } else {
//                        mJSONArray = mJSONObject.getJSONArray("posts");
//                        receivedData.put(routePart, mJSONObject.getJSONArray("posts"));
//
//                        System.out.println(routePart + " " + mJSONObject.getJSONArray("posts"));
//                    }
//                    System.out.println(responseString);
//
//                } catch (IOException iOE) {
//                    iOE.printStackTrace();
//                } catch (JSONException jOE) {
//                    jOE.printStackTrace();
//                }
//            }
//        });
//    }
}
