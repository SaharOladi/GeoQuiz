package com.example.geoquiz.Model;

import java.io.Serializable;

public class Question implements Serializable {
    // id of each question
    private int mQuestionTextResId;
    // the answer of each question (true or false)
    private boolean mIsAnswerTrue;
    // show the user cheat or not
    private boolean mIsCheating;
    // show the user answer question or not
    private boolean mIsAnswered;

    // just add default constructor and getter, setter for fields.
    public Question(int questionTextResId, boolean isAnswerTrue) {
        mQuestionTextResId = questionTextResId;
        mIsAnswerTrue = isAnswerTrue;
    }

    public Question() {
    }


    public boolean isIsCheating() {
        return mIsCheating;
    }

    public void setIsCheating(boolean mIsCheating) {
        this.mIsCheating = mIsCheating;
    }

    public boolean isIsAnswered() {
        return mIsAnswered;
    }

    public void setIsAnswered(boolean mIsAnswered) {
        this.mIsAnswered = mIsAnswered;
    }


    public int getQuestionTextResId() {
        return mQuestionTextResId;
    }

    public void setQuestionTextResId(int questionTextResId) {
        mQuestionTextResId = questionTextResId;
    }

    public boolean isAnswerTrue() {
        return mIsAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mIsAnswerTrue = answerTrue;
    }

}