package com.unrealmojo.hamsters.ui.hamsters;

import com.unrealmojo.hamsters.repos.HamstersLiveData;

import androidx.lifecycle.ViewModel;

public class HamstersViewModel extends ViewModel {
    public HamstersLiveData repo = new HamstersLiveData();

    public void refreshData() {
        repo.loadHamstersFromNetwork();
    }
}
