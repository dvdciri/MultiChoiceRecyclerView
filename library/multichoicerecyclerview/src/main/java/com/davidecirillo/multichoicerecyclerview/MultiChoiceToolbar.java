package com.davidecirillo.multichoicerecyclerview;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

public class MultiChoiceToolbar {

    AppCompatActivity appCompatActivity;
    MultiChoiceRecyclerView multiChoiceRecyclerView;
    Toolbar mToolbar;
    String defaultToolbarTitle;
    String selectedToolbarTitle;
    int default_primaryColor = 0;
    int default_primaryColorDark = 0;
    int multi_primaryColor = 0;
    int multi_primaryColorDark = 0;

    private MultiChoiceToolbar(Builder builder) {
        this.appCompatActivity = builder.appCompatActivity;
        this.mToolbar = builder.mToolbar;
        this.defaultToolbarTitle = builder.defaultToolbarTitle.trim();
        this.selectedToolbarTitle = builder.selectedToolbarTitle.trim();
        this.default_primaryColor = builder.default_primaryColor;
        this.default_primaryColorDark = builder.default_primaryColorDark;
        multi_primaryColor = builder.multi_primaryColor;
        multi_primaryColorDark = builder.multi_primaryColorDark;
    }

    public void setMultiChoiceRecyclerView(MultiChoiceRecyclerView multiChoiceRecyclerView) {
        this.multiChoiceRecyclerView = multiChoiceRecyclerView;
    }

    /**
     * Builder class for MultiChoiceToolbar
     */
    public static class Builder {
        private AppCompatActivity appCompatActivity;
        private Toolbar mToolbar;
        private String defaultToolbarTitle;
        private String selectedToolbarTitle;

        // Colours
        private int default_primaryColor = 0;
        private int default_primaryColorDark = 0;
        private int multi_primaryColor = 0;
        private int multi_primaryColorDark = 0;

        public Builder(AppCompatActivity appCompatActivity,
                       Toolbar toolbar) {
            this.appCompatActivity = appCompatActivity;
            this.mToolbar = toolbar;
        }

        public Builder setDefault_primaryColor(int default_primaryColor) {
            this.default_primaryColor = default_primaryColor;
            return this;
        }

        public Builder setDefaultToolbarTitle(String defaultToolbarTitle) {
            this.defaultToolbarTitle = defaultToolbarTitle;
            return this;
        }

        public Builder setSelectedToolbarTitle(String selectedToolbarTitle) {
            this.selectedToolbarTitle = selectedToolbarTitle;
            return this;
        }

        public Builder setDefault_primaryColorDark(int default_primaryColorDark) {
            this.default_primaryColorDark = default_primaryColorDark;
            return this;
        }

        public Builder setMulti_primaryColor(int multi_primaryColor) {
            this.multi_primaryColor = multi_primaryColor;
            return this;
        }

        public Builder setMulti_primaryColorDark(int multi_primaryColorDark) {
            this.multi_primaryColorDark = multi_primaryColorDark;
            return this;
        }

        public MultiChoiceToolbar build() {
            if (default_primaryColor == 0 || default_primaryColorDark == 0) {
                default_primaryColor = getDefaultColorFromContext(new int[]{R.attr.colorPrimary});
                default_primaryColorDark = getDefaultColorFromContext(new int[]{R.attr.colorPrimaryDark});
            }
            return new MultiChoiceToolbar(this);
        }

        private int getDefaultColorFromContext(int[] colorRes) {
            TypedValue typedValue = new TypedValue();
            TypedArray a = appCompatActivity.obtainStyledAttributes(typedValue.data, colorRes);
            int color = a.getColor(0, 0);
            a.recycle();
            return color;
        }

    }
}
