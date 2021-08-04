package com.example.ethinkosorganismosemboliasmou.ui.questions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QuestionsModel extends ViewModel {

    private MutableLiveData<String> mText;

    public QuestionsModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is questions fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}