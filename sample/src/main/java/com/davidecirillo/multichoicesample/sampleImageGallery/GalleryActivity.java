package com.davidecirillo.multichoicesample.sampleImageGallery;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.davidecirillo.multichoicerecyclerview.MultiChoiceRecyclerView;
import com.davidecirillo.multichoicesample.BaseActivity;
import com.davidecirillo.multichoicesample.R;

import butterknife.BindView;

/**
 * Created by davidecirillo on 27/06/2016.
 */

public class GalleryActivity extends BaseActivity {

    @BindView(R.id.multiChoiceRecyclerView)
    public MultiChoiceRecyclerView mMultiChoiceRecyclerView;

    @Override
    protected int setActivityIdentifier() {
        return R.layout.activity_sample_custom_view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpMultiChoiceRecyclerView();
    }

    private void setUpMultiChoiceRecyclerView() {

        mMultiChoiceRecyclerView.setRecyclerColumnNumber(4);

        mMultiChoiceRecyclerView.setMultiChoiceToolbar(this,
                toolbar,
                getString(R.string.app_name),
                "",
                R.color.colorPrimaryMulti, R.color.colorPrimaryDarkMulti);

        SampleGalleryAdapter adapter = new SampleGalleryAdapter(GalleryActivity.this);
        mMultiChoiceRecyclerView.setAdapter(adapter);
    }

}
