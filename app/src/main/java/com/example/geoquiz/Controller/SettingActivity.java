package com.example.geoquiz.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.geoquiz.R;

import java.util.PrimitiveIterator;

public class SettingActivity extends AppCompatActivity {

    private RadioButton mButtonSmall;
    private RadioButton mButtonMedium;
    private RadioButton mButtonLarge;

    private int textSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        findViews();
        setListener();

    }

    private void setListener(){
        mButtonSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSize = 14;
            }
        });

        mButtonMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSize = 18;
            }
        });

        mButtonLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textSize = 22;
            }
        });

        setResults(textSize);

    }

    private void findViews(){
        mButtonSmall = findViewById(R.id.radio_btn_small);
        mButtonMedium = findViewById(R.id.radio_btn_medium);
        mButtonLarge = findViewById(R.id.radio_btn_large);
    }

    private void setResults(int textSize){
        Intent intent = new Intent();
        intent.putExtra("EXTRA_TEXT_SIZE",textSize);
        setResult(RESULT_OK, intent);
    }
}