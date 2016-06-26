package com.davidecirillo.multichoicesample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davidecirillo on 24/06/2016.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    protected
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setActivityIdentifier());

        ButterKnife.bind(this);

        if(toolbar != null)
            setSupportActionBar(toolbar);
    }

    protected abstract int setActivityIdentifier();
}
