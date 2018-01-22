package com.miraldemoproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.miraldemoproject.googleocrreader.OcrCaptureActivity;
import com.miraldemoproject.productslanguage.RTLActivity;
import com.miraldemoproject.textrecognition.TextRecognitionActivity;
import com.miraldemoproject.uiretrofitrx.RiderActivity;
import com.miraldemoproject.user.view.UserListActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    private void initViews() {
        findViewById(R.id.button_recognize).setOnClickListener(this);
        findViewById(R.id.button_google_ocr).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_recognize:
                startNextActivity(TextRecognitionActivity.class);
                break;

            case R.id.button_google_ocr:
                startNextActivity(OcrCaptureActivity.class);
                break;


        }

    }

    private void startNextActivity(Class className) {
        Intent intent = new Intent(MainActivity.this, className);
        startActivity(intent);
    }

}
