package com.example.spaceoptima.ui.classes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainClassesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MainClassesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Classes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}