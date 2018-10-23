package com.unrealmojo.hamsters.ui.base;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment implements BaseOperations {
    @Override
    public String pageTag() {
        return "BaseFragment";
    }
}
