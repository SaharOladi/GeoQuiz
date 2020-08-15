package com.example.geoquiz.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geoquiz.Model.Question;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.geoquiz.R;

import java.io.Serializable;

public class GeoActivity extends AppCompatActivity implements Serializable {

    public static final String EXTRA_QUESTION_ANSWER = "com.example.geoActivity.questionAnswer";
    public static final String CURRENT_INDEX = "Current_Index";
    public static final String NUMBER_OF_ANSWERED = "Number_Of_Answered";
    public static final String QUESTION_BANK = "Question_Bank";
    public static final String CURRENT_SCORE = "CURRENT_SCORE";

    public static final int REQUEST_CODE_CHEAT = 0;
    public static final int REQUEST_SETTING_CODE = 1;

    private LinearLayout mMainLayout;
    private LinearLayout mScoreLayout;
    private TextView mTextViewQuestion;
    private Button mButtonTrue;
    private Button mButtonFalse;
    private Button mButtonCheat;
    private Button mButtonReset;
    private Button mButtonSetting;
    private ImageButton mImageButtonNext;
    private ImageButton mImageButtonPrev;
    private TextView mTextViewScore;
    private TextView mTextViewFinalScore;

    private boolean mIsCheater = false;
    private int mCurrentIndex = 0;
    private int mCurrentScore = 0;
    private int mNumOfAnswered = 0;

    private Question[] mQuestionBank = {
            new Question(R.string.question_australia, false),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, true),
            new Question(R.string.question_americas, false),
            new Question(R.string.question_asia, false)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_geo);

        findViews();

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(CURRENT_INDEX);
            mNumOfAnswered = savedInstanceState.getInt(NUMBER_OF_ANSWERED);
            mQuestionBank = (Question[]) savedInstanceState.getSerializable(QUESTION_BANK);
            mCurrentScore = savedInstanceState.getInt(CURRENT_SCORE);
            mTextViewScore.setText(" : امتیاز " + mCurrentScore);
        }

        setListeners();
        updateQuestion();
        checkGameOver();


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_INDEX, mCurrentIndex);
        outState.putInt(NUMBER_OF_ANSWERED, mNumOfAnswered);
        outState.putSerializable(QUESTION_BANK, mQuestionBank);
        outState.putInt(CURRENT_SCORE, mCurrentScore);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_CHEAT) {
            mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_IS_CHEAT, false);
            mQuestionBank[mCurrentIndex].setIsCheating(mIsCheater);
            checkGameOver();
        }
        if (requestCode == REQUEST_SETTING_CODE) {
            updateQuestion();
        }
    }

    private void setListeners() {
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ++mNumOfAnswered;
                mButtonTrue.setEnabled(false);
                mButtonFalse.setEnabled(false);
                mQuestionBank[mCurrentIndex].setIsAnswered(true);
                checkAnswer(true);

            }
        });

        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++mNumOfAnswered;
                mButtonTrue.setEnabled(false);
                mButtonFalse.setEnabled(false);
                mQuestionBank[mCurrentIndex].setIsAnswered(true);
                checkAnswer(false);

            }
        });

        mImageButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mImageButtonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex - 1 + mQuestionBank.length) % mQuestionBank.length;
                updateQuestion();
            }
        });
        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScoreLayout.setVisibility(View.GONE);
                resetGame();
            }
        });
        mButtonCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeoActivity.this, CheatActivity.class);
                intent.putExtra(EXTRA_QUESTION_ANSWER, mQuestionBank[mCurrentIndex].isAnswerTrue());

                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
        mButtonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeoActivity.this, SettingActivity.class);
                startActivityForResult(intent, REQUEST_SETTING_CODE);
            }
        });


    }

    private void resetGame() {
        mCurrentScore = 0;
        mCurrentIndex = 0;
        mTextViewScore.setText(mCurrentScore + " : امتیاز ");
        mButtonTrue.setEnabled(true);
        mButtonFalse.setEnabled(true);
        mNumOfAnswered = 0;

        for (Question element : mQuestionBank) {
            element.setIsAnswered(false);

        }
        mMainLayout.setVisibility(View.VISIBLE);

    }

    private void updateQuestion() {
        int questionTextResId = mQuestionBank[mCurrentIndex].getQuestionTextResId();
        mTextViewQuestion.setText(questionTextResId);
        if (!mQuestionBank[mCurrentIndex].isIsAnswered()) {
            mButtonTrue.setEnabled(true);
            mButtonFalse.setEnabled(true);

        } else {
            mButtonTrue.setEnabled(false);
            mButtonFalse.setEnabled(false);
        }
        checkGameOver();
    }

    private void checkAnswer(boolean userPressed) {
        if (mQuestionBank[mCurrentIndex].isIsCheating()) {
            Toast.makeText(this, R.string.toast_judgment, Toast.LENGTH_LONG).show();
            mIsCheater = false;
        } else {
            if (mQuestionBank[mCurrentIndex].isAnswerTrue() == userPressed) {
                Toast.makeText(GeoActivity.this, R.string.toast_correct, Toast.LENGTH_SHORT)
                        .show();

                updateScore();

            } else {
                Toast.makeText(GeoActivity.this, R.string.toast_incorrect, Toast.LENGTH_SHORT)
                        .show();

            }
        }


    }

    private void updateScore() {
        mTextViewScore.setText(++mCurrentScore + " : امتیاز ");
    }

    private void showFinalScore() {
        mTextViewFinalScore.setText(" : امتیاز شما در بازی  " + mCurrentScore);
        mTextViewFinalScore.setTextSize(25);

    }

    private void checkGameOver() {
        if (mNumOfAnswered == mQuestionBank.length) {
            mMainLayout.setVisibility(View.GONE);
            mScoreLayout.setVisibility(View.VISIBLE);
            showFinalScore();
        }
    }

    private void findViews() {
        mTextViewQuestion = findViewById(R.id.txt_view_question_text);
        mTextViewScore = findViewById(R.id.txt_view_score_text);
        mButtonTrue = findViewById(R.id.btn_true);
        mButtonFalse = findViewById(R.id.btn_false);
        mButtonCheat = findViewById(R.id.btn_cheat);
        mImageButtonNext = findViewById(R.id.im_btn_next);
        mImageButtonPrev = findViewById(R.id.im_btn_prev);
        mTextViewFinalScore = findViewById(R.id.txt_final_score);
        mButtonReset = findViewById(R.id.btn_reset);
        mScoreLayout = findViewById(R.id.score);
        mMainLayout = findViewById(R.id.main);
        mButtonSetting = findViewById(R.id.btn_setting);

    }
}