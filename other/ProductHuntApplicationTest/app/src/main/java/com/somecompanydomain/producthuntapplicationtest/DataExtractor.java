package com.somecompanydomain.producthuntapplicationtest;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DataExtractor {

    List<String> categories;

    OkHttpClient mClient;
  //  RequestBody mRequestBody;
    Request mRequest;
  //  Call mCall;
    Response mResponse;

    JSONObject mJSONObject;// = null;
    JSONArray mJSONArrayPosts;// = null;

    public JSONArray getJSONArrayPosts(String routePiece) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    mClient = new OkHttpClient();
                    mRequest = new Request.Builder()
                            .url("https://api.producthunt.com/v1/feed")
                            .addHeader("Accept", "application/json")
                            .addHeader("Content-Type", "application/json")
                            .addHeader("Authorization", "Bearer 591f99547f569b05ba7d8777e2e0824eea16c440292cce1f8dfb3952cc9937ff")
                            .addHeader("Host", "api.producthunt.com")
                            .build();

                    mResponse = mClient.newCall(mRequest).execute();
                    String responseString = mResponse.body().string();
                    mJSONObject = new JSONObject(responseString);
                    mJSONArrayPosts = mJSONObject.getJSONArray("posts");

                    System.out.println(responseString);
                    System.out.println(mResponse.headers());
                    System.out.println("\n\n " + mJSONArrayPosts);
                    Log.d(MainActivity.LOG_TAG, "POSTS ARRAY:  " + mJSONArrayPosts);
                } catch (IOException ioe) {
                    Log.d(MainActivity.LOG_TAG, "                           IOException ioe");
                } catch (JSONException joe) {
                    Log.d(MainActivity.LOG_TAG, "                           JSONException joe");
                }

                System.out.println("YOU GOT BRAND NEW JSONOBJECT (DEFAULT CONSTRUCTOR)");

            }
        });
        return mJSONArrayPosts;
    }


    public JSONArray getJSONArrayCategories() {
        categories = new ArrayList<>();
        executeAsyncTask("categories");
        //notifyAll();
        return mJSONArrayPosts;
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
                    mJSONArrayPosts = mJSONObject.getJSONArray("categories");

                    if(routePart.equals("categories")) {

                        for (int i = 0; i < mJSONArrayPosts.length(); i++) {
                            categories.add(i, mJSONArrayPosts.getJSONObject(i).getString("name"));
                            System.out.println(categories.get(i));
                        }
                    }

                    System.out.println(mJSONArrayPosts.getJSONObject(3));
                    System.out.println(responseString);

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (JSONException joe) {
                    joe.printStackTrace();
                }

            }
        });
    }

    public List<String> getCategories() {
        return categories;
    }
    
    public void setCategoriesSpinner(Spinner spinner) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

              //  mSpinner = (Spinner) findViewById(R.id.spinner);

             //   System.out.println(mDataExtractor.getCategories() + "TATATATATTATA getCategory()");
                
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        ArrayAdapter<String> msgAdapter = (ArrayAdapter<String>) msg.obj;
                        mSpinner.setAdapter(msgAdapter);
                        }
                    };


                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, mDataExtractor.getCategories());

                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                Message msg = new Message();
                msg.obj = dataAdapter;

                mHandler.sendMessage(msg);
                
                

                System.out.println(mDataExtractor.getCategories() + "TATATATATTATA2");
            }
        });

    }
}
