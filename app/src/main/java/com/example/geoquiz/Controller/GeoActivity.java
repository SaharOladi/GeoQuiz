package com.example.geoquiz.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geoquiz.R;
import com.example.geoquiz.model.Question;

public class GeoActivity extends AppCompatActivity {

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
    private Question[] mQuestionBank = new Question[]{
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
        updateQuestion();




        //This listener is implemented as an anonymous inner class.
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
                mNextPrevLayout.setVisibility(View.VISIBLE);
                mAnswerLayout.setVisibility(View.VISIBLE);
                mQuestionText.setVisibility(View.VISIBLE);
                mScoreLayout.setVisibility(View.GONE);
                GeoActivity.this.recreate();

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
            mNextPrevLayout.setVisibility(View.GONE);
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

    private void setAnswerButtonsEnnoblement(boolean isEnabled) {
        mButtonTrue.setEnabled(isEnabled);
        mButtonFalse.setEnabled(isEnabled);
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


//    private Button mButtonTrue;
//    private Button mButtonFalse;
//    private ImageButton mButtonNext;
//    private ImageButton mButtonPrev;
//    private TextView mQuestionText;
//
//
//    private int mCurrentIndex = 0;
//    private Question[] mQuestionBank = {
//            new Question(R.string.question_australia, false),
//            new Question(R.string.question_oceans, true),
//            new Question(R.string.question_mideast, false),
//            new Question(R.string.question_africa, true),
//            new Question(R.string.question_americas, false),
//            new Question(R.string.question_asia, false)
//    };
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_geo);
//
//
//        findViews();
//        setOnListener();
//        updateQuestion();
//    }
//
//    private void setOnListener() {
//        mButtonTrue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checkAnswer(true);
//                mQuestionBank[mCurrentIndex].setAnswered(true);
//                setAnswerButtonsEnablement(false);
//            }
//        });
//
//        mButtonFalse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checkAnswer(false);
//                mQuestionBank[mCurrentIndex].setAnswered(true);
//                setAnswerButtonsEnablement(false);
//            }
//        });
//
//        mButtonNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
//                updateQuestion();
//                updateButtonEnablement();
//                showGrade();
//            }
//        });
//
//        mButtonPrev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCurrentIndex = (mCurrentIndex - 1 + mQuestionBank.length) % mQuestionBank.length;
//                updateQuestion();
//                updateButtonEnablement();
//                showGrade();
//            }
//        });
//    }
//
//    private void findViews() {
//        mQuestionText = findViewById(R.id.textview_question_txt);
//        mButtonTrue = findViewById(R.id.btn_true);
//        mButtonFalse = findViewById(R.id.btn_false);
//        mButtonNext = findViewById(R.id.btn_next);
//        mButtonPrev = findViewById(R.id.btn_prev);
//    }
//
//    private void updateQuestion() {
//        int questionResId = mQuestionBank[mCurrentIndex].getQuestionResId();
//        mQuestionText.setText(questionResId);
//    }
//
//    private void checkAnswer(boolean userPressed) {
//        int msg = 0;
//        if (mQuestionBank[mCurrentIndex].isAnsweredTrue() == userPressed) {
//            msg = R.string.toast_correct;
//            mQuestionBank[mCurrentIndex].setAnsweredCorrect(true);
//        } else {
//            msg = R.string.toast_incorrect;
//            mQuestionBank[mCurrentIndex].setAnsweredCorrect(false);
//        }
//        Toast.makeText(GeoActivity.this, msg, Toast.LENGTH_LONG).show();
//    }
//
//    private void setAnswerButtonsEnablement(boolean isEnabled) {
//        mButtonTrue.setEnabled(isEnabled);
//        mButtonFalse.setEnabled(isEnabled);
//    }
//
//    private void updateButtonEnablement() {
//        if (mQuestionBank[mCurrentIndex].isAnswered()) {
//            setAnswerButtonsEnablement(false);
//        } else {
//            setAnswerButtonsEnablement(true);
//        }
//    }
//
//    private int calGrade() {
//        int countCorrect = 0;
//        for (int i = 0; i < mQuestionBank.length; i++) {
//            countCorrect = mQuestionBank[mCurrentIndex].isAnsweredCorrect() ? countCorrect + 1 : countCorrect;
//        }
//        return countCorrect;
//    }
//
//    private void showGrade() {
//        int grade = 0;
//        grade = calGrade();
//        Toast.makeText(this, "امتیاز شما برابر است با: "+ grade, Toast.LENGTH_SHORT).show();
//
//    }


//    private void setViewsButtonTrue() {
//        Toast toast = Toast.makeText(GeoActivity.this, R.string.toast_correct, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.TOP, 0, 225);
//
//
//        View view_1 = toast.getView();
//        TextView textView = view_1.findViewById(android.R.id.message);
//        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_check_24, 0);
//        textView.setTextSize(20);
//
//        mTextView.setTextSize(20);
//        mTextView.setTextColor(Color.GREEN);
//
//        toast.show();
//    }


//    private void setViewsButtonFalse() {
//        Toast toast = Toast.makeText(GeoActivity.this, R.string.toast_incorrect, Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.BOTTOM, 0, 50);
//
//        View view_1 = toast.getView();
//        TextView textView = view_1.findViewById(android.R.id.message);
//        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_clear_24, 0, 0, 0);
//        textView.setTextSize(20);
//
//        mTextView.setTextSize(20);
//        mTextView.setTextColor(Color.RED);
//
//        toast.show();
//    }


