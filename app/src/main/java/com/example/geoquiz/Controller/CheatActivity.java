package com.example.geoquiz.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.geoquiz.R;

public class CheatActivity extends AppCompatActivity {

    public static final String EXTRA_IS_CHEAT = "com.example.geoActivity.isCheat";
    public static final String TEXT_VIEW_ANSWER = "TextViewAnswer";
    public static final String M_IS_CHEATED = "mIsCheated";
    public static final String M_IS_ANSWER_TRUE = "mIsAnswerTrue";

    private TextView mTextViewAnswer;
    private Button mButtonShowAnswer;

    private boolean mIsAnswerTrue;
    private boolean mIsCheated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        findViews();

        if (savedInstanceState != null) {
            mTextViewAnswer.setText(savedInstanceState.getString(TEXT_VIEW_ANSWER));
            mIsAnswerTrue = getIntent().getBooleanExtra(GeoActivity.EXTRA_QUESTION_ANSWER, false);
            mIsCheated = getIntent().getBooleanExtra(M_IS_CHEATED, false);
        }

        setListeners();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_VIEW_ANSWER,  mTextViewAnswer.getText().toString());
        outState.putBoolean(M_IS_CHEATED, mIsCheated);
        outState.putBoolean(M_IS_ANSWER_TRUE, mIsAnswerTrue);
    }

    private void setListeners() {
        mButtonShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mIsAnswerTrue) {
                    mTextViewAnswer.setText(R.string.button_true);
                }
                else {
                    mTextViewAnswer.setText(R.string.button_false);
                }


                mIsCheated = true;
            }
        });

        setShownAnswerResult(true);
    }


    private void setShownAnswerResult(boolean isCheat) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IS_CHEAT, isCheat);
        setResult(RESULT_OK, intent);

    }

    private void findViews() {
        mTextViewAnswer = findViewById(R.id.txt_true_answer);
        mButtonShowAnswer = findViewById(R.id.btn_show_cheat);
    }
}