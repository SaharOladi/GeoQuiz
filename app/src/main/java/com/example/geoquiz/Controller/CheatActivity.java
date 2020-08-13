package com.example.geoquiz.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geoquiz.R;

public class CheatActivity extends AppCompatActivity {

    public static final String EXTRA_IS_CHEAT = "com.example.geoquiz.ExtraIsCheat";
    public static final String BUNDLE_M_IS_CHEATED = "com.example.geoquiz.mIsCheated";

    private Button mButtonShowAnswer;
    private TextView mTextViewAnswer;

    private boolean mIsAnswerTrue;
    private boolean mIsCheated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mIsAnswerTrue = getIntent().getBooleanExtra(GeoActivity.EXTRA_QUESTION_ANSWER, false);
        findViews();
        setOnListener();

        if (savedInstanceState != null) {
            mIsCheated = savedInstanceState.getBoolean(BUNDLE_M_IS_CHEATED, false);
        }

        if (mIsCheated) {
            if (mIsAnswerTrue) {
                mTextViewAnswer.setText(R.string.button_true);
            } else {
                mTextViewAnswer.setText(R.string.button_false);
            }
        }
    }

    private void setOnListener() {

        mButtonShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsAnswerTrue) {
                    mTextViewAnswer.setText(R.string.button_true);
                } else {
                    mTextViewAnswer.setText(R.string.button_false);
                }

                mIsCheated = true;
            }
        });

        setShownAnswerResult(true);
    }


    private void setShownAnswerResult(boolean isCheated) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_IS_CHEAT, isCheated);
        setResult(RESULT_OK, intent);
    }

    private void findViews() {
        mTextViewAnswer = findViewById(R.id.textView_Cheat_answer_show);
        mButtonShowAnswer = findViewById(R.id.btn_show_cheat);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(BUNDLE_M_IS_CHEATED, mIsCheated);
    }
}