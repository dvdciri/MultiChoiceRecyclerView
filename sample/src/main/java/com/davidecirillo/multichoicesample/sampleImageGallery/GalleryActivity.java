package com.davidecirillo.multichoicesample.sampleImageGallery;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.davidecirillo.multichoicerecyclerview.MultiChoiceRecyclerView;
import com.davidecirillo.multichoicerecyclerview.MultiChoiceToolbar;
import com.davidecirillo.multichoicesample.BaseActivity;
import com.davidecirillo.multichoicesample.R;

import butterknife.BindView;

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

        MultiChoiceToolbar multiChoiceToolbar = new MultiChoiceToolbar.Builder(this, toolbar)
                .setDefaultToolbarTitle(getString(R.string.app_name))
                .setSelectedToolbarTitle("")
                .setMulti_primaryColor(R.color.colorPrimaryMulti)
                .setMulti_primaryColorDark(R.color.colorPrimaryDarkMulti)
                .build();
        mMultiChoiceRecyclerView.setMultiChoiceToolbar(multiChoiceToolbar);

        SampleGalleryAdapter adapter = new SampleGalleryAdapter(GalleryActivity.this);
        mMultiChoiceRecyclerView.setAdapter(adapter);
    }

}
