package com.example.geoquiz.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geoquiz.R;
import com.example.geoquiz.model.Question;

import java.io.Serializable;

public class GeoActivity extends AppCompatActivity {

    private static final String TAG = "GeoQuizActivity";
    private static final String BUNDLE_KEY_CURRENT_INDEX = "currentIndex";
    private static final String BUNDLE_KEY_GRADE = "grade";
    private static final String BUNDDLE_KEY_BUTTOMN_ENABLEMENT = "buttomn_enablement";


    private Button mButtonTrue;
    private Button mButtonFalse;
    private ImageButton mButtonNext;
    private ImageButton mButtonPrev;
    private TextView mQuestionText;
    private TextView mScoreText;
    private Button mButtonReset;
    private View mAnswerLayout;
    private View mNextPrevLayout;
    private View mScoreLayout;


    private int mCurrentIndex = 0;
    private String mgrade = "";
    private boolean[] boolArr;
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, false),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, true),
            new Question(R.string.question_americas, false),
            new Question(R.string.question_asia, false)
    };
    private boolean[] mBoolAnswered = new boolean[mQuestionBank.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo);
        findViews();

        Log.d(TAG, "onCreate");

        if (savedInstanceState != null) {
            Log.d(TAG, "savedInstanceState: " + savedInstanceState);
            mgrade = savedInstanceState.getString(BUNDLE_KEY_GRADE);
            mCurrentIndex = savedInstanceState.getInt(BUNDLE_KEY_CURRENT_INDEX, 0);
            boolArr = savedInstanceState.getBooleanArray(BUNDDLE_KEY_BUTTOMN_ENABLEMENT);
            for (int i = 0; i < mQuestionBank.length; i++) {
                mQuestionBank[i].setAnswered(boolArr[i]);
            }

            setAnswerButtonsEnnoblement(!mQuestionBank[mCurrentIndex].isAnswered());
        } else
            Log.d(TAG, "savedInstanceState is NULL!!");
        updateQuestion();
        //This listener is implemented as an anonymous inner class.
        setListener();
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestory");
    }

    /**
     * it will save bundle before it will be destroyed
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: " + mCurrentIndex);

        for (int i = 0; i < mQuestionBank.length; i++) {
            mBoolAnswered[i] = mQuestionBank[i].isAnswered();
        }

        outState.putInt(BUNDLE_KEY_CURRENT_INDEX, mCurrentIndex);
        outState.putString(BUNDLE_KEY_GRADE, showGradeIfAnsweredAll());
        outState.putBooleanArray(BUNDDLE_KEY_BUTTOMN_ENABLEMENT, mBoolAnswered);
    }

    private void setListener() {
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                mQuestionBank[mCurrentIndex].setAnswered(true);
                setAnswerButtonsEnnoblement(false);
            }
        });

        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                mQuestionBank[mCurrentIndex].setAnswered(true);
                setAnswerButtonsEnnoblement(false);

            }
        });

        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
                updateButtonEnnoblement();
                String text = showGradeIfAnsweredAll();
                mScoreText.setText(text);
                mScoreText.setTextSize(20);
                mScoreText.setTextColor(Color.BLACK);
            }
        });

        mButtonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex - 1 + mQuestionBank.length) % mQuestionBank.length;
                updateQuestion();
                updateButtonEnnoblement();
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mNextPrevLayout != null) {
                    mNextPrevLayout.setVisibility(View.VISIBLE);
                }
                mAnswerLayout.setVisibility(View.VISIBLE);
                mQuestionText.setVisibility(View.VISIBLE);
                mScoreLayout.setVisibility(View.GONE);
                mCurrentIndex = 0;
                mQuestionBank = new Question[]{
                        new Question(R.string.question_australia, false),
                        new Question(R.string.question_oceans, true),
                        new Question(R.string.question_mideast, false),
                        new Question(R.string.question_africa, true),
                        new Question(R.string.question_americas, false),
                        new Question(R.string.question_asia, false)};
                updateQuestion();
//                GeoActivity.this.recreate();

            }
        });
    }

    private String showGradeIfAnsweredAll() {

        int countAnswered = 0;
        double grade = 0;
        for (int i = 0; i < mQuestionBank.length; i++) {
            countAnswered = mQuestionBank[i].isAnswered() ? countAnswered + 1 : countAnswered;
        }
        if (countAnswered == mQuestionBank.length || mCurrentIndex == mQuestionBank.length - 1) {
            grade = calculateGrade();
            if (mNextPrevLayout != null) {
                mNextPrevLayout.setVisibility(View.GONE);
            }
            mAnswerLayout.setVisibility(View.GONE);
            mQuestionText.setVisibility(View.GONE);
            mScoreLayout.setVisibility(View.VISIBLE);
        }

        String text = "امتیاز شما برابر است با: " + grade;
        return text;
    }

    private double calculateGrade() {
        int countAnsweredCorrectly = 0;
        for (int i = 0; i < mQuestionBank.length; i++) {
            countAnsweredCorrectly = mQuestionBank[i].isAnsweredCorrect() ? countAnsweredCorrectly + 1 : countAnsweredCorrectly;
        }
        return countAnsweredCorrectly;
    }

    private void setAnswerButtonsEnnoblement(boolean isEnabled) {
        mButtonTrue.setEnabled(isEnabled);
        mButtonFalse.setEnabled(isEnabled);
    }

    private void updateButtonEnnoblement() {
        if (mQuestionBank[mCurrentIndex].isAnswered()) {
            setAnswerButtonsEnnoblement(false);
        } else {
            setAnswerButtonsEnnoblement(true);
        }
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getQuestionResId();
        mQuestionText.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnsweredTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.toast_correct;
            mQuestionBank[mCurrentIndex].setAnsweredCorrect(true);
        } else {
            messageResId = R.string.toast_incorrect;
            mQuestionBank[mCurrentIndex].setAnsweredCorrect(false);
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void findViews() {
        mQuestionText = findViewById(R.id.textview_question_txt);
        mButtonTrue = findViewById(R.id.btn_true);
        mButtonFalse = findViewById(R.id.btn_false);
        mButtonNext = findViewById(R.id.btn_next);
        mButtonPrev = findViewById(R.id.btn_prev);
        mButtonReset = findViewById(R.id.btn_reset);
        mScoreText = findViewById(R.id.txt_score);
        mScoreLayout = findViewById(R.id.score);
        mAnswerLayout = findViewById(R.id.answer);
        mNextPrevLayout = findViewById(R.id.next_prev);
    }
}