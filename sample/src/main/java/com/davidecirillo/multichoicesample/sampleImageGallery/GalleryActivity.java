package com.davidecirillo.multichoicesample.sampleImageGallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

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

        mMultiChoiceRecyclerView.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));

        MultiChoiceToolbar multiChoiceToolbar = new MultiChoiceToolbar.Builder(this, toolbar)
                .setDefaultToolbarTitle(toolbarTitle())
                .setSelectedToolbarTitle("")
                .setMultiPrimaryColor(R.color.colorPrimaryMulti)
                .setMultiPrimaryColorDark(R.color.colorPrimaryDarkMulti)
                .setIcon(R.drawable.ic_arrow_back_white_24dp, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                })
                .build();
        mMultiChoiceRecyclerView.setMultiChoiceToolbar(multiChoiceToolbar);

        SampleGalleryAdapter adapter = new SampleGalleryAdapter(GalleryActivity.this);
        mMultiChoiceRecyclerView.setAdapter(adapter);
    }

    @Override
    protected String toolbarTitle() {
        return getString(R.string.image_gallery);
    }

    @Override
    protected boolean showBackHomeAsUpIndicator() {
        return true;
    }
}
