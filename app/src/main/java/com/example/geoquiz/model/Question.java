package com.example.geoquiz.model;

public class Question {
    private int mQuestionResId;
    private boolean mIsAnsweredTrue;
    private boolean mIsAnsweredCorrect;
    private boolean mIsAnswered;

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
