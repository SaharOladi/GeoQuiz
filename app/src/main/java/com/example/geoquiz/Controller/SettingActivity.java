package com.example.geoquiz.Controller;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

    }

    private void setListener(){
        mButtonSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mButtonMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mButtonLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void findViews(){
        mButtonSmall = findViewById(R.id.radio_btn_small);
        mButtonMedium = findViewById(R.id.radio_btn_medium);
        mButtonLarge = findViewById(R.id.radio_btn_large);
    }
}