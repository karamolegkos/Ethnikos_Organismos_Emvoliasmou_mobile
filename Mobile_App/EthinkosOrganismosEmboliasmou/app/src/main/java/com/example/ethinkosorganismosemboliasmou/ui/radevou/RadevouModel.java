package com.example.ethinkosorganismosemboliasmou.ui.radevou;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RadevouModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RadevouModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is radevou fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}