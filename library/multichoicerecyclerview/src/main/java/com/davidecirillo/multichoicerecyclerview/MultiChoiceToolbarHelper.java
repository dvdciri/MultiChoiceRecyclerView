package com.davidecirillo.multichoicerecyclerview;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

class MultiChoiceToolbarHelper {

    private static final String TOOLBAR_ERROR_MESSAGE = "Toolbar not implemented via getSupportActionBar method";

    private MultiChoiceToolbar mMultiChoiceToolbar;
    private boolean isMCToolbarVisible = false;

    MultiChoiceToolbarHelper(MultiChoiceToolbar multiChoiceToolbar) {
        mMultiChoiceToolbar = multiChoiceToolbar;
    }

    void updateToolbar(int itemNumber) {
        try {
            if (itemNumber > 0 && isMCToolbarVisible) {
                updateMultiSelectionToolbar(itemNumber);
            } else if (itemNumber > 0) {
                showMultiSelectionToolbar(itemNumber);
            } else {
                showDefaultToolbar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMultiSelectionToolbar(int itemNumber) throws Exception {

        AppCompatActivity appCompatActivity = mMultiChoiceToolbar.mAppCompatActivity;
        ActionBar supportActionBar = appCompatActivity.getSupportActionBar();

        if (supportActionBar != null) {

            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);

            int multi_primaryColor = mMultiChoiceToolbar.mMultiPrimaryColor;
            if (multi_primaryColor != 0) {
                supportActionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(appCompatActivity, multi_primaryColor)));
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = appCompatActivity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                int multi_primaryColorDark = mMultiChoiceToolbar.mMultiPrimaryColorDark;
                if (multi_primaryColorDark != 0) {
                    window.setStatusBarColor(ContextCompat.getColor(appCompatActivity, multi_primaryColorDark));
                }
            }

            String selectedToolbarTitle = mMultiChoiceToolbar.mSelectedToolbarTitle;
            if (selectedToolbarTitle != null) {
                supportActionBar.setTitle(itemNumber + " " + selectedToolbarTitle);
            } else {
                supportActionBar.setTitle(itemNumber);
            }

            mMultiChoiceToolbar.mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MultiChoiceToolbar.Listener listener = mMultiChoiceToolbar.mListener;
                    if (listener != null) {
                        listener.onClearButtonPressed();
                    }
                }
            });

            isMCToolbarVisible = true;

        } else throw new Exception(TOOLBAR_ERROR_MESSAGE);
    }

    private void showDefaultToolbar() throws Exception {

        AppCompatActivity appCompatActivity = mMultiChoiceToolbar.mAppCompatActivity;
        ActionBar supportActionBar = appCompatActivity.getSupportActionBar();
        if (supportActionBar != null) {

            int icon = mMultiChoiceToolbar.mIcon;
            if (icon != 0) {
                supportActionBar.setHomeAsUpIndicator(icon);
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                mMultiChoiceToolbar.mToolbar.setNavigationOnClickListener(mMultiChoiceToolbar.mIconAction);
            } else {
                supportActionBar.setDisplayHomeAsUpEnabled(false);
            }
            supportActionBar.setBackgroundDrawable(new ColorDrawable(mMultiChoiceToolbar.mDefaultPrimaryColor));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = appCompatActivity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(mMultiChoiceToolbar.mDefaultPrimaryColorDark);
            }

            String defaultToolbarTitle = mMultiChoiceToolbar.mDefaultToolbarTitle;
            if (defaultToolbarTitle != null) {
                supportActionBar.setTitle(defaultToolbarTitle);
            } else {
                supportActionBar.setTitle(appCompatActivity.getTitle());
            }

            isMCToolbarVisible = false;

        } else throw new Exception(TOOLBAR_ERROR_MESSAGE);
    }

    private void updateMultiSelectionToolbar(int itemNumber) throws Exception {

        AppCompatActivity appCompatActivity = mMultiChoiceToolbar.mAppCompatActivity;
        if (appCompatActivity.getSupportActionBar() != null) {

            String selectedToolbarTitle = mMultiChoiceToolbar.mSelectedToolbarTitle;
            if (selectedToolbarTitle != null) {
                appCompatActivity.getSupportActionBar().setTitle(itemNumber + " " + selectedToolbarTitle);
            } else {
                appCompatActivity.getSupportActionBar().setTitle(itemNumber);
            }

        } else throw new Exception(TOOLBAR_ERROR_MESSAGE);
    }

}
