package com.davidecirillo.multichoicerecyclerview;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

public class MultiChoiceToolbar {

    AppCompatActivity mAppCompatActivity;
    MultiChoiceRecyclerView mMultiChoiceRecyclerView;
    Toolbar mToolbar;
    String mDefaultToolbarTitle;
    String mSelectedToolbarTitle;
    int mDefaultPrimaryColor = 0;
    int mDefaultPrimaryColorDark = 0;
    int mMultiPrimaryColor = 0;
    int mMultiPrimaryColorDark = 0;
    int mIcon;
    View.OnClickListener mIconAction;

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

    public void setMultiChoiceRecyclerView(MultiChoiceRecyclerView multiChoiceRecyclerView) {
        this.mMultiChoiceRecyclerView = multiChoiceRecyclerView;
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

        public Builder setIcon(int icon, View.OnClickListener action){
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
}
