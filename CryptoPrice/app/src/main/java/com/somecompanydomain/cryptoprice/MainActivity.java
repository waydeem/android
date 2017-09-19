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
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/";

    TextView mBtcAskPriceTextView;
    TextView mEthAskPriceTextView;
    TextView mLtcAskPriceTextView;
    TextView mBtcBidPriceTextView;
    TextView mEthBidPriceTextView;
    TextView mLtcBidPriceTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtcAskPriceTextView = (TextView) findViewById(R.id.btcPriceAsk);
        mEthAskPriceTextView = (TextView) findViewById(R.id.ethPriceAsk);
        mLtcAskPriceTextView = (TextView) findViewById(R.id.ltcPriceAsk);
        mBtcBidPriceTextView = (TextView) findViewById(R.id.btcPriceBid);
        mEthBidPriceTextView = (TextView) findViewById(R.id.ethPriceBid);
        mLtcBidPriceTextView = (TextView) findViewById(R.id.ltcPriceBid);
        final Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);
        setAdapterAndDataForSpinner(spinner);

    }

    private void setAdapterAndDataForSpinner(final Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner.setSelection(position);
                getDataFromTheInternetsAndSetPrices(BASE_URL + "BTC" + spinner.getSelectedItem().toString());
                getDataFromTheInternetsAndSetPrices(BASE_URL + "ETH" + spinner.getSelectedItem().toString());
                getDataFromTheInternetsAndSetPrices(BASE_URL + "LTC" + spinner.getSelectedItem().toString());
                Log.d("CRYPTO PRICE", spinner.getSelectedItem().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("CRYPTO PRICE", "Nuthin' was selected");
            }
        });
    }

    private void getDataFromTheInternetsAndSetPrices(final String url){

        final AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObjResponse) {
                try {
                    if(url.contains("BTC")) {
                        mBtcAskPriceTextView.setText(jsonObjResponse.getString("ask"));
                        mBtcBidPriceTextView.setText(jsonObjResponse.getString("bid"));
                    } else if(url.contains("ETH")) {
                        mEthAskPriceTextView.setText(jsonObjResponse.getString("ask"));
                        mEthBidPriceTextView.setText(jsonObjResponse.getString("bid"));
                    } else {
                        mLtcAskPriceTextView.setText(jsonObjResponse.getString("ask"));
                        mLtcBidPriceTextView.setText(jsonObjResponse.getString("bid"));
                    }
                } catch (JSONException joE) {
                    joE.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                    Log.d("CRYPTO PRICE", "BAD RESPONCE " + url + " " + response);
                }
        });
    }
}

