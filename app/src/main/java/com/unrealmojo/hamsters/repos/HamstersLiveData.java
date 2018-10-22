package com.unrealmojo.hamsters.repos;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.unrealmojo.hamsters.helpers.AppConfig;
import com.unrealmojo.hamsters.models.Hamster;

import java.util.List;
import java.util.concurrent.Executors;

import androidx.lifecycle.MutableLiveData;

public class HamstersLiveData extends MutableLiveData<HamstersResp> {
    private boolean loading = false;

    @Override
    protected void onInactive() {
        super.onInactive();
        AndroidNetworking.forceCancel("hamsters_req");
    }

    @Override
    protected void onActive() {
        super.onActive();
        loadHamstersFromNetwork();
    }

    public void loadHamstersFromNetwork() {

        if (loading) return;
        loading = true;

        AndroidNetworking.get(AppConfig.API_URL)
                .setExecutor(Executors.newSingleThreadExecutor())
                .build()
                .getAsObjectList(Hamster.class, new ParsedRequestListener<List<Hamster>>() {
                    @Override
                    public void onResponse(List<Hamster> response) {
                        loading = false;
                        postValue(new HamstersResp(response));
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading = false;
                        postValue(new HamstersResp());
                    }
                });
    }
}
