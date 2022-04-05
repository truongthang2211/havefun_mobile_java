package com.example.havefun.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PromotionViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PromotionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ưu đãi fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}