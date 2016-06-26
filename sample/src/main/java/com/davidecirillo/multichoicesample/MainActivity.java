package com.davidecirillo.multichoicesample;

import android.content.Intent;
import android.net.Uri;

import com.davidecirillo.multichoicesample.sampleCustomView.SampleCustomActivity;
import com.davidecirillo.multichoicesample.sampleToolbar.SampleToolbarActivity;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @Override
    public int setActivityIdentifier() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.sample_custom_view)
    public void customView(){

        startActivity(new Intent(this, SampleCustomActivity.class));
    }

    @OnClick(R.id.sample_toolbar)
    public void toolbar(){

        startActivity(new Intent(this, SampleToolbarActivity.class));
    }

    @OnClick(R.id.github_button)
    public void go_github(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/dvdciri/MultiChoiceRecyclerView"));
        startActivity(browserIntent);
    }


}
