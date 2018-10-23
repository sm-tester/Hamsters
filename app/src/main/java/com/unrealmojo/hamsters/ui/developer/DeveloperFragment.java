package com.unrealmojo.hamsters.ui.developer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unrealmojo.hamsters.R;
import com.unrealmojo.hamsters.databinding.DeveloperFragmentBinding;
import com.unrealmojo.hamsters.helpers.customs.SmartToolbar;
import com.unrealmojo.hamsters.ui.base.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

public class DeveloperFragment extends BaseFragment {

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
        DeveloperFragmentBinding UI = DataBindingUtil.inflate(
                inflater,
                R.layout.developer_fragment,
                container,
                false);
        return UI.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SmartToolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.hideAllMenu();
        toolbar.showBackBtn();
    }
}
