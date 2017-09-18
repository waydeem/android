package com.somecompanydomain.cryptoprice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/";

    TextView mBtcPriceTextView;
    TextView mEthPriceTextView;
    TextView mLtcPriceTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtcPriceTextView = (TextView) findViewById(R.id.btcPrice);
        mEthPriceTextView = (TextView) findViewById(R.id.ethPrice);
        mLtcPriceTextView = (TextView) findViewById(R.id.ltcPrice);
        final Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        //TODO: вывести в отдельную функцию всё нижестоящее безобразие

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
                getDataFromTheInternets(BASE_URL + "BTC" + spinner.getSelectedItem().toString());
                getDataFromTheInternets(BASE_URL + "ETH" + spinner.getSelectedItem().toString());
                getDataFromTheInternets(BASE_URL + "LTC" + spinner.getSelectedItem().toString());
                Log.d("CRYPTO PRICE", spinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("CRYPTO PRICE", "Nuthin' selected");
            }
        });

    }


    private void getDataFromTheInternets(final String url){

        final AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObjResponse) {
                try {
                    if(url.contains("BTC")) {
                        mBtcPriceTextView.setText(jsonObjResponse.getString("ask"));
                    } else if(url.contains("ETH")) {
                        mEthPriceTextView.setText(jsonObjResponse.getString("ask"));
                    } else {
                        mLtcPriceTextView.setText(jsonObjResponse.getString("ask"));
                    }
                } catch (JSONException joE) {
                    joE.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                    Log.d("CRYPTO PRICE", "BAD RESPONCE " + url);
                }
        });
    }
}

