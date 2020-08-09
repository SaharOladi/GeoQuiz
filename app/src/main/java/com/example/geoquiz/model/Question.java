package com.example.geoquiz.model;

import java.io.Serializable;

public class Question implements Serializable {
    // the id of each question
    private int mQuestionResId;
    // the answer of each question
    private boolean mIsAnsweredTrue;

    private boolean mIsAnsweredCorrect;
    private boolean mIsAnswered;

    private boolean mIsCheater;

    public boolean isCheater() {
        return mIsCheater;
    }

    public void setCheater(boolean cheater) {
        mIsCheater = cheater;
    }

    public Question(int questionResId, boolean isAnsweredTrue) {
        mQuestionResId = questionResId;
        mIsAnsweredTrue = isAnsweredTrue;
    }

    public boolean isAnsweredCorrect() {
        return mIsAnsweredCorrect;
    }

    public void setAnsweredCorrect(boolean answeredCorrect) {
        mIsAnsweredCorrect = answeredCorrect;
    }

    public boolean isAnswered() {
        return mIsAnswered;
    }

    public void setAnswered(boolean answered) {
        mIsAnswered = answered;
    }

    public int getQuestionResId() {
        return mQuestionResId;
    }

    public void setQuestionResId(int questionResId) {
        mQuestionResId = questionResId;
    }

    public boolean isAnsweredTrue() {
        return mIsAnsweredTrue;
    }

    public void setAnsweredTrue(boolean answeredTrue) {
        mIsAnsweredTrue = answeredTrue;
    }
}
