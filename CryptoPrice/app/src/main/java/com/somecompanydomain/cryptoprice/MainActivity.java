package com.somecompanydomain.cryptoprice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    //объявляем объекты, необходимые для работы программы
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/";

    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;

    FrameLayout progressBarHolder;

    TextView mBtcAskPriceTextView;
    TextView mEthAskPriceTextView;
    TextView mLtcAskPriceTextView;
    TextView mBtcBidPriceTextView;
    TextView mEthBidPriceTextView;
    TextView mLtcBidPriceTextView;
    TextView mCurrentTime;

    SimpleDateFormat sDF = new SimpleDateFormat("HH:mm:ss dd/MM/YYYY");

    //объявление и инициализация счётчика, нужного для вывода времени
    int counter = 0;

    //инициализируем необходимые для работы программы объекты и вызываем нужные функции
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);

        mBtcAskPriceTextView = (TextView) findViewById(R.id.btcPriceAsk);
        mEthAskPriceTextView = (TextView) findViewById(R.id.ethPriceAsk);
        mLtcAskPriceTextView = (TextView) findViewById(R.id.ltcPriceAsk);
        mBtcBidPriceTextView = (TextView) findViewById(R.id.btcPriceBid);
        mEthBidPriceTextView = (TextView) findViewById(R.id.ethPriceBid);
        mLtcBidPriceTextView = (TextView) findViewById(R.id.ltcPriceBid);
        mCurrentTime = (TextView) findViewById(R.id.currentTime);
        final Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        sDF.setTimeZone(TimeZone.getDefault());
        setAdapterForSpinnerAndDataForApplication(spinner);
    }

    //описываем функцию, которая устанавливает адаптер для спиннера со списком валют,
    //а также запрашивает и устанавливает значения цен на криптовалюты для выбранной валюты
    private void setAdapterForSpinnerAndDataForApplication(final Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(9);

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
                Log.d("CRYPTO PRICE", "Nothing was selected");
            }
        });
    }

    //описываем функцию, проводяющую асинхронный запрос с заголовком,
    //необходимым для получения данных от API сервиса,
    //предоставляющего актуальные цены на криптовалюты
    private void getDataFromTheInternetsAndSetPrices(final String url){

        final AsyncHttpClient client = new AsyncHttpClient();
        try {
            client.addHeader("X-signature",
                    getSignature(getResources().getString(R.string.secret_key), getResources().getString(R.string.public_key)));
        } catch (NoSuchAlgorithmException nsaE) {
            nsaE.printStackTrace();
        } catch (InvalidKeyException ikE) {
            ikE.printStackTrace();
        }

        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                turnAnimationOn();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObjResponse) {
                try {
                    if(url.contains("BTC")) {
                        mBtcAskPriceTextView.setText(jsonObjResponse.getString("ask"));
                        mBtcBidPriceTextView.setText(jsonObjResponse.getString("bid"));
                        counter++;
                    } else if(url.contains("ETH")) {
                        mEthAskPriceTextView.setText(jsonObjResponse.getString("ask"));
                        mEthBidPriceTextView.setText(jsonObjResponse.getString("bid"));
                        counter++;
                    } else {
                        mLtcAskPriceTextView.setText(jsonObjResponse.getString("ask"));
                        mLtcBidPriceTextView.setText(jsonObjResponse.getString("bid"));
                        counter++;
                    }
                    if(counter == 3) {
                        mCurrentTime.setText(mCurrentTime.getText().toString().replaceAll("[\\d+:+/+ ]", "") + " " +
                                sDF.format(new Date(Long.parseLong(jsonObjResponse.getString("timestamp"))*1000L)));
                        counter = 0;
                    }
                } catch (JSONException joE) {
                    joE.printStackTrace();
                }
                turnAnimationOff();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
                    Log.d("CRYPTO PRICE", "BAD RESPONSE " + url + " " + response);
                    turnAnimationOff();
                }
        });
    }

    //описываем анимацию загрузки данных
    public void turnAnimationOn() {
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBarHolder.setVisibility(View.VISIBLE);
    }

    //описываем закрытие анимации
    public void turnAnimationOff() {
        outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBarHolder.setVisibility(View.GONE);
    }

    //описание функции, приводящей массив байтов к строке с шестнадцатеричными значениями,
    //применение которой является необходимым для получения корректного заголовка для API
    public String byteArrayToHexString(byte[] array) {
        StringBuffer hexString = new StringBuffer();
        for (byte b : array) {
            int intVal = b & 0xff;
            if (intVal < 0x10)
                hexString.append("0");
            hexString.append(Integer.toHexString(intVal));
        }
        return hexString.toString();
    }

     //описание функции, генерирующий нужный заголовок для запроса согласно рекомендациям по работе с API,
     //которые можно посмотреть по адресу https://apiv2.bitcoinaverage.com/#signing
     String getSignature(String secretKey, String publicKey) throws NoSuchAlgorithmException, InvalidKeyException {
         long timestamp = System.currentTimeMillis() / 1000L;
         String payload = timestamp + "." + publicKey;

         Mac sha256_Mac = Mac.getInstance("HmacSHA256");
         SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
         sha256_Mac.init(secretKeySpec);
         byte[] digest = sha256_Mac.doFinal(payload.getBytes());
         String signature = payload + "." + byteArrayToHexString(digest);
         return signature;
     }
}