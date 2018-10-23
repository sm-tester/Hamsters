/*
 * Copyright (C) 2015 Sahil Dave
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.unrealmojo.hamsters.helpers.customs;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.unrealmojo.hamsters.R;
import com.unrealmojo.hamsters.helpers.Utilities;

import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatImageButton;

public class SearchViewLayout extends FrameLayout {
    private boolean mIsExpanded = false;

    private ViewGroup mExpanded;
    private EditText mSearchEditText;
    private View mBackButtonView;

    private SearchListener mSearchListener;
    private SearchBoxListener mSearchBoxListener;

    private SearchViewCleanListener mCleanListener;
    private CollapseListener mCollapseListener;

    public EditText getEditText() {
        return mSearchEditText;
    }

    /***
     * Interface for listening to animation start and finish.
     * expanding and expanded tell the current state of animation.
     */
    public interface OnToggleAnimationListener {
        void onStart(boolean expanding);

        void onFinish(boolean expanded);
    }

    /***
     * Interface for listening to search finish call.
     * Called on clicking of search button on keyboard
     */

    public interface SearchListener {
        void onFinished(String searchKeyword);
    }

    /***
     * Interface for listening to search edit text.
     */

    public interface SearchBoxListener {
        void beforeTextChanged(CharSequence s, int start, int count, int after);
        void onTextChanged(CharSequence s, int start, int before, int count);
        void afterTextChanged(Editable s);
    }

    /***
     * Interface for listening to clean search edit text.
     */
    public interface SearchViewCleanListener {
        public void didTextClear();
    }

    /***
     * Interface for listening to collapse UI.
     */
    public interface CollapseListener {
        public void collapse();
    }

    public void setSearchListener(SearchListener listener) {
        mSearchListener = listener;
    }

    public void setSearchBoxListener(SearchBoxListener listener) {
        mSearchBoxListener = listener;
    }

    public SearchViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        mExpanded = findViewById(R.id.search_root);
        mSearchEditText = mExpanded.findViewById(R.id.search_edit_text);
        mBackButtonView = mExpanded.findViewById(R.id.search_back_button);
        AppCompatImageButton clearBtn = findViewById(R.id.search_clear_button);

        mSearchEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                Utilities.showInputMethod(v);
            } else {
                Utilities.hideInputMethod(v);
            }
        });
        mSearchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                callSearchListener();
                Utilities.hideInputMethod(v);
                return true;
            }
            return false;
        });
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(mSearchBoxListener!=null) mSearchBoxListener.onTextChanged(s, start, before, count);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(mSearchBoxListener!=null) mSearchBoxListener.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void afterTextChanged(Editable s) {
                clearBtn.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
                if(mSearchBoxListener!=null) mSearchBoxListener.afterTextChanged(s);
            }
        });

        mBackButtonView.setOnClickListener(v -> collapse());

        clearBtn.setOnClickListener(v -> {
            mSearchEditText.setText("");
            if(mCleanListener != null) mCleanListener.didTextClear();
        });

        super.onFinishInflate();
    }

    /***
     * Set hint in the expanded state
     *
     * Also see {@link #setHint(String)}
     * @param searchViewHint
     */
    public void setExpandedHint(String searchViewHint) {
        if (searchViewHint != null) {
            mSearchEditText.setHint(searchViewHint);
        }
    }

    /***
     * Overrides both, {@link #setHint(String)} and {@link #setExpandedHint(String)},
     * and sets hint for both the views.
     *
     * Use this if you don't want to show different hints in both the states
     * @param searchViewHint
     */
    public void setHint(String searchViewHint) {
        if (searchViewHint != null) {
            mSearchEditText.setHint(searchViewHint);
        }
    }
    
    /***
     * Set a text for the expanded editText
     *
     * Maybe what you input is not a full keyword, and you can use this to stuff the editText
     * usually by clicking the items of list showing inexact results.
     * @param searchViewText
     */
    public void setExpandedText(String searchViewText) {
        if (searchViewText != null) {
            mSearchEditText.setText(searchViewText);
        }
    }
    
    public void expand(boolean requestFocus) {
        mIsExpanded = true;
        setVisibility(View.VISIBLE);

        if (requestFocus) {
            mSearchEditText.requestFocus();
        }
    }

    public void collapse() {
        mSearchEditText.setText(null);
        mIsExpanded = false;
        setVisibility(View.GONE);

        if (mCollapseListener != null) mCollapseListener.collapse();
    }

    public boolean isExpanded() {
        return mIsExpanded;
    }

    /**
     * Allow user to set a back icon in the expanded view
     *
     * @param iconResource resource id of icon
     */
    public void setExpandedBackIcon(@DrawableRes int iconResource) {
        ((ImageView)mBackButtonView).setImageResource(iconResource);
    }

    private void callSearchListener() {
        Editable editable = mSearchEditText.getText();
        if (editable != null && editable.length() > 0) {
            if (mSearchListener != null) {
                mSearchListener.onFinished(editable.toString());
            }
        }
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (mSearchEditTextLayoutListener.onKey(this, event.getKeyCode(), event)) {
            return true;
        }
        return super.dispatchKeyEventPreIme(event);
    }

    /**
     * Open the search UI when the user clicks on the search box.
     */
    private final OnClickListener mSearchViewOnClickListener = v -> {
        if (!mIsExpanded) {
            expand(true);
        }
    };

    /**
     * If the search term is empty and the user closes the soft keyboard, close the search UI.
     */
    private final OnKeyListener mSearchEditTextLayoutListener = (v, keyCode, event) -> {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN &&
                isExpanded()) {
            boolean keyboardHidden = Utilities.hideInputMethod(v);
            if (keyboardHidden) return true;
            collapse();
            return true;
        }
        return false;
    };

    public SearchViewCleanListener getCleanListener() {
        return mCleanListener;
    }

    public void setCleanListener(SearchViewCleanListener mCleanListener) {
        this.mCleanListener = mCleanListener;
    }

    public CollapseListener getCollapseListener() {
        return mCollapseListener;
    }

    public void setCollapseListener(CollapseListener mCollapseListener) {
        this.mCollapseListener = mCollapseListener;
    }
}
