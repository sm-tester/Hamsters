package com.unrealmojo.hamsters.repos;

import android.os.Build;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.unrealmojo.hamsters.helpers.AppConfig;
import com.unrealmojo.hamsters.helpers.Utilities;
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

//        manufacturer LGE
//        model Nexus 4
//        version 22
//        versionRelease 5.1.1
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE;

        AndroidNetworking.get(AppConfig.API_URL)
                .addHeaders("X-Homo-Client-OS", "Android " + Build.VERSION.RELEASE)
                .addHeaders("X-Homo-Client-Version", "HamstersTestApp " + Utilities.object().versionName)
                .addHeaders("X-Homo-Client-Model", Build.MANUFACTURER + " " + Build.MODEL)
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
