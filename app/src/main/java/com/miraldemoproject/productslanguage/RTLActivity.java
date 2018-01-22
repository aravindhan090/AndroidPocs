package com.miraldemoproject.productslanguage;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.miraldemoproject.R;
import com.miraldemoproject.productslanguage.model.ProductDetails;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;


public class RTLActivity extends AppCompatActivity {

    private AsyncTask mAsyncTask;
    private ImageView mProdImage;
    RatingBar mRating;
    private HttpURLConnection mHttpURLConnection;
    private URL mUrl;
    private TextView mProdName, mProdDesc, mProdPrice, mProdRev;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_lang);
        initiateProdViews();
        getProductDetails();

    }

    /*
    Initializing view components
     */
    private void initiateProdViews() {
        mProdName = (TextView) findViewById(R.id.product_name);
        mRating = (RatingBar) findViewById(R.id.ratingsbar);
        mProdImage = (ImageView) findViewById(R.id.product_image);
        mProdDesc = (TextView) findViewById(R.id.product_desc);
        mProdPrice = (TextView) findViewById(R.id.product_price);
        mProdRev = (TextView) findViewById(R.id.product_reviews);
    }

    /*
    Retrieving product details from server and setting them to the view components
     */
    private void getProductDetails() {
        mAsyncTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                try {
                    mUrl = new URL("http://192.168.56.1:4000/promos");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected Object doInBackground(Object[] params) {
                StringBuilder sb = null;
                Gson gson = null;
                try {
                    mHttpURLConnection = (HttpURLConnection) mUrl.openConnection();
                    Locale current = getResources().getConfiguration().locale;
                    mHttpURLConnection.setRequestProperty("locale", current.getLanguage());
                    mHttpURLConnection.connect();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(mHttpURLConnection.getInputStream()));
                    sb = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
                    bufferedReader.close();
                    //                     gson = new GsonBuilder().registerTypeAdapter(Promotion.class,null).create();
                    gson = new Gson();
                    return gson.fromJson(sb.toString(), ProductDetails[].class);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                ProductDetails[] productDetails = (ProductDetails[]) o;
                mProdName.setText(productDetails[0].getProduct_name());
                mRating.setRating(productDetails[0].getProduct_stars());
                mProdDesc.setText(productDetails[0].getDescription());
                mProdPrice.setText(productDetails[0].getProduct_price());
                int image_id = RTLActivity.this.getResources().getIdentifier(productDetails[0].getImage_ID(), "drawable",
                        RTLActivity.this.getPackageName());
                mProdImage.setImageResource(image_id);
                mProdRev.setText(productDetails[0].getReviews());
            }
        };
        mAsyncTask.execute();
    }
}
