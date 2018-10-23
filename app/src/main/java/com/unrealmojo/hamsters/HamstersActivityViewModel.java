package com.unrealmojo.hamsters;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class HamstersActivityViewModel extends ViewModel {

    public InteractActions interactActions;

    public interface InteractActions {
        void openPage(int pageNum, boolean needBackStack, Bundle args);
    }
}
