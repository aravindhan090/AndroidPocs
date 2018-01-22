package com.miraldemoproject.textrecognition;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.LanguageCodes;
import com.microsoft.projectoxford.vision.contract.Line;
import com.microsoft.projectoxford.vision.contract.OCR;
import com.microsoft.projectoxford.vision.contract.Region;
import com.microsoft.projectoxford.vision.contract.Word;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;
import com.miraldemoproject.R;
import com.miraldemoproject.application.MiralDemoApplication;
import com.miraldemoproject.helper.AppUtil;
import com.miraldemoproject.helper.ImageHelper;
import com.miraldemoproject.helper.SelectImageActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TextRecognitionActivity extends AppCompatActivity {

    private static final int REQUEST_SELECT_IMAGE = 0;

    private Button mButtonSelectImage;

    private Uri mImageUri;

    private Bitmap mBitmap;

    private EditText mEditText;

    private VisionServiceRestClient client;
    private String MORNING = "MORNING";
    private String AFTERNOON = "AFTERNOON";
    private String EVENING = "EVENING";
    private String HELLO = "HELLO";
    private String OFFICE = "OFFICE";
    private String BURGER = "BURGER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);

        //resolveDependency();

        AppUtil.checkSubscriptionKey(TextRecognitionActivity.this);
        AppUtil.verifyStoragePermissions(TextRecognitionActivity.this);
        initviews();
    }

    private void initviews() {
        client = ((MiralDemoApplication) getApplication()).getVisionServiceRestClient();
        mButtonSelectImage = (Button) findViewById(R.id.buttonSelectImageRec);
        mButtonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v);
            }
        });
        mEditText = (EditText) findViewById(R.id.editTextResult);
    }

    public void selectImage(View view) {
        mEditText.setText("");

        Intent intent;
        intent = new Intent(TextRecognitionActivity.this, SelectImageActivity.class);
        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("AnalyzeActivity", "onActivityResult");
        switch (requestCode) {
            case REQUEST_SELECT_IMAGE:
                if (resultCode == RESULT_OK) {

                    mImageUri = data.getData();

                    mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
                            mImageUri, getContentResolver());

                    if (mBitmap != null) {

                        ImageView imageView = (ImageView) findViewById(R.id.selectedImage);
                        imageView.setImageBitmap(mBitmap);

                        Log.d("AnalyzeActivity",
                                "Image: " + mImageUri + " resized to " + mBitmap.getWidth()
                                        + "x" + mBitmap.getHeight());

                        doRecognize();
                    }
                }
                break;
            default:
                break;
        }
    }

    public void doRecognize() {
        mButtonSelectImage.setEnabled(false);
        mEditText.setText("Analyzing");
        try {
            new doRequest().execute();
        } catch (Exception e) {
            mEditText.setText("Error encountered. Exception is: " + e.toString());
        }
    }

    private String process() throws VisionServiceException, IOException {
        Gson gson = new Gson();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

        OCR ocr;
        ocr = this.client.recognizeText(inputStream, LanguageCodes.AutoDetect, true);

        String result = gson.toJson(ocr);
        Log.d("result", result);

        return result;
    }

    private class doRequest extends AsyncTask<String, String, String> {

        private Exception e = null;

        public doRequest() {
        }

        @Override
        protected String doInBackground(String... args) {
            try {
                return process();
            } catch (Exception e) {
                this.e = e;    // Store error
            }

            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            // Display based on error existence

            if (e != null) {
                mEditText.setText("Error: " + e.getMessage());
                this.e = null;
            } else {
                Gson gson = new Gson();
                OCR r = gson.fromJson(data, OCR.class);

                String result = "";
                for (Region reg : r.regions) {
                    for (Line line : reg.lines) {
                        for (Word word : line.words) {
                            result += word.text + " ";
                        }
                        result += "\n";
                    }
                    result += "\n\n";
                }
                result = result.trim();
                if (result.equals(MORNING)) {
                    result = getResources().getString(R.string.eng_morning);
                }
                if (result.equals(AFTERNOON)) {
                    result = getResources().getString(R.string.eng_evening);
                }
                if (result.equals(EVENING)) {
                    result = getResources().getString(R.string.eng_evening);
                }
                if (result.equals(HELLO)) {

                    result = getResources().getString(R.string.eng_hello);
                }
                if (result.equals(OFFICE)) {
                    result = getResources().getString(R.string.eng_office);
                }
                if (result.equals(BURGER)) {
                    result = getResources().getString(R.string.eng_burger);
                }
                mEditText.setText(result);
            }
            mButtonSelectImage.setEnabled(true);
        }
    }
}
