package com.somecompanydomain.producthuntapplicationtest;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;

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
    private List<String> categoryList;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDataExtractor = new DataExtractor();
        categoryList = new ArrayList<>();

        super.onCreate(savedInstanceState);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                ArrayAdapter<String> msgAdapter = (ArrayAdapter<String>) msg.obj;
                mSpinner.setAdapter(msgAdapter);
            }
        };


        setContentView(R.layout.activity_main);
        mSpinner = (Spinner) findViewById(R.id.spinner);

        //setCategoryList();
        mDataExtractor.getJSONArrayCategories();
        CustomAddValues();


//        System.out.println(categoryList + "TATATATATTATA3");

//        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                System.out.print("BRYYA BTYA");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                System.out.print("BRYYA");
//            }
//        });


        // mSpinner.setOnItemSelectedListener();
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_my);
//        setSupportActionBar(myToolbar);


        //  mDataExtractor = new DataExtractor();

        //mDataExtractor.getJSONArrayPosts();

    }


    public void CustomAddValues() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                mSpinner = (Spinner) findViewById(R.id.spinner);

                System.out.println(mDataExtractor.getCategories() + "TATATATATTATA getCategory()");


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






