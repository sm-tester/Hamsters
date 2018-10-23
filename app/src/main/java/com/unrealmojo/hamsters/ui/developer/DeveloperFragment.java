package com.unrealmojo.hamsters.ui.developer;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unrealmojo.hamsters.R;
import com.unrealmojo.hamsters.ui.base.BaseFragment;

public class DeveloperFragment extends BaseFragment {

    private DeveloperViewModel mViewModel;

    public static DeveloperFragment newInstance(Bundle args) {
        DeveloperFragment developerFragment = new DeveloperFragment();
        developerFragment.setArguments(args);
        return developerFragment;
    }

    @Override
    public String pageTag() {
        return "DeveloperFragment";
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.developer_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DeveloperViewModel.class);
    }
}
