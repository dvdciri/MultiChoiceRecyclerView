package com.davidecirillo.multichoicerecyclerview;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

public class MultiChoiceToolbar {

    private AppCompatActivity mAppCompatActivity;
    private Toolbar mToolbar;
    private String mDefaultToolbarTitle;
    private String mSelectedToolbarTitle;
    private int mDefaultPrimaryColor = 0;
    private int mDefaultPrimaryColorDark = 0;
    private int mMultiPrimaryColor = 0;
    private int mMultiPrimaryColorDark = 0;
    private int mIcon;
    private View.OnClickListener mIconAction;
    private Listener mListener;

    private MultiChoiceToolbar(Builder builder) {
        this.mAppCompatActivity = builder.mAppCompatActivity;
        this.mToolbar = builder.mToolbar;
        this.mDefaultToolbarTitle = builder.mDefaultToolbarTitle.trim();
        this.mSelectedToolbarTitle = builder.mSelectedToolbarTitle.trim();
        this.mDefaultPrimaryColor = builder.mDefaultPrimaryColor;
        this.mDefaultPrimaryColorDark = builder.mDefaultPrimaryColorDark;
        this.mMultiPrimaryColor = builder.mMultiPrimaryColor;
        this.mMultiPrimaryColorDark = builder.mMultiPrimaryColorDark;
        this.mIcon = builder.mIcon;
        this.mIconAction = builder.mIconAction;
    }

    void setToolbarListener(Listener listener) {
        mListener = listener;
    }

    /**
     * Builder class for MultiChoiceToolbar
     */
    public static class Builder {
        private AppCompatActivity mAppCompatActivity;
        private Toolbar mToolbar;
        private String mDefaultToolbarTitle;
        private String mSelectedToolbarTitle;

        // Colours
        private int mDefaultPrimaryColor = 0;
        private int mDefaultPrimaryColorDark = 0;
        private int mMultiPrimaryColor = 0;
        private int mMultiPrimaryColorDark = 0;
        private int mIcon;
        private View.OnClickListener mIconAction;

        public Builder(AppCompatActivity appCompatActivity,
                       Toolbar toolbar) {
            this.mAppCompatActivity = appCompatActivity;
            this.mToolbar = toolbar;
        }

        public Builder setDefaultPrimaryColor(int defaultPrimaryColor) {
            this.mDefaultPrimaryColor = defaultPrimaryColor;
            return this;
        }

        public Builder setDefaultToolbarTitle(String defaultToolbarTitle) {
            this.mDefaultToolbarTitle = defaultToolbarTitle;
            return this;
        }

        public Builder setSelectedToolbarTitle(String selectedToolbarTitle) {
            this.mSelectedToolbarTitle = selectedToolbarTitle;
            return this;
        }

        public Builder setDefaultPrimaryColorDark(int defaultPrimaryColorDark) {
            this.mDefaultPrimaryColorDark = defaultPrimaryColorDark;
            return this;
        }

        public Builder setMultiPrimaryColor(int multiPrimaryColor) {
            this.mMultiPrimaryColor = multiPrimaryColor;
            return this;
        }

        public Builder setMultiPrimaryColorDark(int multiPrimaryColorDark) {
            this.mMultiPrimaryColorDark = multiPrimaryColorDark;
            return this;
        }

        public Builder setIcon(int icon, View.OnClickListener action) {
            mIcon = icon;
            mIconAction = action;
            return this;
        }

        public MultiChoiceToolbar build() {
            if (mDefaultPrimaryColor == 0 || mDefaultPrimaryColorDark == 0) {
                mDefaultPrimaryColor = getDefaultColorFromContext(new int[]{R.attr.colorPrimary});
                mDefaultPrimaryColorDark = getDefaultColorFromContext(new int[]{R.attr.colorPrimaryDark});
            }
            return new MultiChoiceToolbar(this);
        }

        private int getDefaultColorFromContext(int[] colorRes) {
            TypedValue typedValue = new TypedValue();
            TypedArray a = mAppCompatActivity.obtainStyledAttributes(typedValue.data, colorRes);
            int color = a.getColor(0, 0);
            a.recycle();
            return color;
        }
    }

    /* Getters */

    public AppCompatActivity getAppCompatActivity() {
        return mAppCompatActivity;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public String getDefaultToolbarTitle() {
        return mDefaultToolbarTitle;
    }

    public String getSelectedToolbarTitle() {
        return mSelectedToolbarTitle;
    }

    public int getDefaultPrimaryColor() {
        return mDefaultPrimaryColor;
    }

    public int getDefaultPrimaryColorDark() {
        return mDefaultPrimaryColorDark;
    }

    public int getMultiPrimaryColor() {
        return mMultiPrimaryColor;
    }

    public int getMultiPrimaryColorDark() {
        return mMultiPrimaryColorDark;
    }

    public int getIcon() {
        return mIcon;
    }

    public View.OnClickListener getIconAction() {
        return mIconAction;
    }

    public Listener getToolbarListener() {
        return mListener;
    }

    /*
            * Listener
            * */
    interface Listener {
        void onClearButtonPressed();
    }
}
