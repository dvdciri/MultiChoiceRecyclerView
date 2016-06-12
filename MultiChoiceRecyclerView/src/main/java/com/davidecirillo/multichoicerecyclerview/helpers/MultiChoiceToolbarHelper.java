package com.davidecirillo.multichoicerecyclerview.helpers;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.davidecirillo.multichoicerecyclerview.MultiChoiceRecyclerView;
import com.davidecirillo.multichoicerecyclerview.R;

/**
 * Created by davidecirillo on 12/06/2016.
 */

public class MultiChoiceToolbarHelper {

    private AppCompatActivity appCompatActivity;
    private MultiChoiceRecyclerView multiChoiceRecyclerView;
    private Toolbar mToolbar;

    private String defaultToolbarTitle;
    private String selectedToolbarTitle;

    //Default colours
    private int default_primaryColor = 0;
    private int default_primaryColorDark = 0;
    private int multi_primaryColor = 0;
    private int multi_primaryColorDark = 0;


    public MultiChoiceToolbarHelper(AppCompatActivity appCompatActivity,
                                    MultiChoiceRecyclerView multiChoiceRecyclerView,
                                    Toolbar toolbar) {
        this.appCompatActivity = appCompatActivity;
        this.mToolbar = toolbar;
        this.multiChoiceRecyclerView = multiChoiceRecyclerView;

        default_primaryColor = getDefaultColorFromContext(new int[]{R.attr.colorPrimary});
        default_primaryColorDark = getDefaultColorFromContext(new int[]{R.attr.colorPrimaryDark});
    }


    public MultiChoiceToolbarHelper(AppCompatActivity appCompatActivity,
                                    MultiChoiceRecyclerView multiChoiceRecyclerView,
                                    Toolbar toolbar,
                                    String defaultToolbarTitle,
                                    String selectedToolbarTitle) {
        this.appCompatActivity = appCompatActivity;
        this.mToolbar = toolbar;
        this.multiChoiceRecyclerView = multiChoiceRecyclerView;
        this.defaultToolbarTitle = defaultToolbarTitle.trim();
        this.selectedToolbarTitle = selectedToolbarTitle.trim();

        default_primaryColor = getDefaultColorFromContext(new int[]{R.attr.colorPrimary});
        default_primaryColorDark = getDefaultColorFromContext(new int[]{R.attr.colorPrimaryDark});
    }


    public MultiChoiceToolbarHelper(AppCompatActivity appCompatActivity,
                                    MultiChoiceRecyclerView multiChoiceRecyclerView,
                                    Toolbar toolbar,
                                    String defaultToolbarTitle,
                                    String selectedToolbarTitle,
                                    int selectedPrimaryColor,
                                    int selectedPrimaryColorDark) {

        this.appCompatActivity = appCompatActivity;
        this.mToolbar = toolbar;
        this.multiChoiceRecyclerView = multiChoiceRecyclerView;
        this.defaultToolbarTitle = defaultToolbarTitle.trim();
        this.selectedToolbarTitle = selectedToolbarTitle.trim();

        this.default_primaryColor = getDefaultColorFromContext(new int[]{R.attr.colorPrimary});
        this.default_primaryColorDark = getDefaultColorFromContext(new int[]{R.attr.colorPrimaryDark});
        multi_primaryColor = selectedPrimaryColor;
        multi_primaryColorDark = selectedPrimaryColorDark;
    }


    public MultiChoiceToolbarHelper(AppCompatActivity appCompatActivity,
                                    MultiChoiceRecyclerView multiChoiceRecyclerView,
                                    Toolbar toolbar,
                                    String defaultToolbarTitle,
                                    String selectedToolbarTitle,
                                    int selectedPrimaryColor,
                                    int selectedPrimaryColorDark,
                                    int defaultPrimaryColor,
                                    int defaultPrimaryColorDark) {

        this.appCompatActivity = appCompatActivity;
        this.mToolbar = toolbar;
        this.multiChoiceRecyclerView = multiChoiceRecyclerView;
        this.defaultToolbarTitle = defaultToolbarTitle.trim();
        this.selectedToolbarTitle = selectedToolbarTitle.trim();

        this.default_primaryColor = defaultPrimaryColor;
        this.default_primaryColorDark = defaultPrimaryColorDark;
        multi_primaryColor = selectedPrimaryColor;
        multi_primaryColorDark = selectedPrimaryColorDark;
    }


    public void updateToolbar(int itemNumber) {
        try {

            if (itemNumber > 0) {
                showMultiSelectionToolbar(itemNumber);
            } else {
                showDefaultToolbar();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMultiSelectionToolbar(int itemNumber) throws Exception {

        if (appCompatActivity.getSupportActionBar() != null) {

            appCompatActivity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            if (multi_primaryColor != 0)
                appCompatActivity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(appCompatActivity, multi_primaryColor)));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = appCompatActivity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                if (multi_primaryColorDark != 0)
                    window.setStatusBarColor(ContextCompat.getColor(appCompatActivity, multi_primaryColorDark));
            }

            if (selectedToolbarTitle != null)
                appCompatActivity.getSupportActionBar().setTitle(itemNumber + " " + selectedToolbarTitle);
            else
                appCompatActivity.getSupportActionBar().setTitle(itemNumber + " selected");

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (multiChoiceRecyclerView != null)
                        multiChoiceRecyclerView.deselectAll();
                }
            });

        } else throw new Exception("Toolbar not implemented via getSupportActionBar method");
    }

    private void showDefaultToolbar() throws Exception {

        if (appCompatActivity.getSupportActionBar() != null) {

            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            appCompatActivity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(default_primaryColor));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = appCompatActivity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(default_primaryColorDark);
            }

            if (defaultToolbarTitle != null)
                appCompatActivity.getSupportActionBar().setTitle(defaultToolbarTitle);
            else
                appCompatActivity.getSupportActionBar().setTitle(appCompatActivity.getTitle());


        } else throw new Exception("Toolbar not implemented via getSupportActionBar method");
    }

    private int getDefaultColorFromContext(int[] colorRes) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = appCompatActivity.obtainStyledAttributes(typedValue.data, colorRes);
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }
}
