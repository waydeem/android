package com.somecompanydomain.producthuntapplicationtest;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ca.mimic.oauth2library.OAuth2Client;
import ca.mimic.oauth2library.OAuthError;
import ca.mimic.oauth2library.OAuthResponse;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {

    public static String LOG_TAG = "my_log";
    private Spinner mSpinner;
    private DataExtractor mDataExtractor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDataExtractor = new DataExtractor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         mSpinner = (Spinner) findViewById(R.id.spinner);
        CustomAddValues();

        // mSpinner.setOnItemSelectedListener();
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_my);
//        setSupportActionBar(myToolbar);

        mDataExtractor = new DataExtractor();

        //mDataExtractor.getJSONArrayPosts();
        mDataExtractor.getJSONArrayCategories();
    }

    public void CustomAddValues() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mSpinner = (Spinner) findViewById(R.id.spinner);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.this,
                        android.R.layout.simple_spinner_item, mDataExtractor.getCategories());
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(dataAdapter);
            }
        });
        mSpinner.setSelection(0);
//    public void addListenerOnButton() {
    }
}


