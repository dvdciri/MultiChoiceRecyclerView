package com.davidecirillo.multichoicesample.sampleToolbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.davidecirillo.multichoicerecyclerview.MultiChoiceToolbar;
import com.davidecirillo.multichoicesample.BaseActivity;
import com.davidecirillo.multichoicesample.R;
import com.davidecirillo.multichoicesample.ResultActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SampleToolbarActivity extends BaseActivity {

    public static final String SELECTED_ITEMS = "selectedItems";
    @BindView(R.id.multiChoiceRecyclerView)
    RecyclerView mMultiChoiceRecyclerView;

    private ArrayList<String> stringList;
    private MySampleToolbarAdapter mMySampleToolbarAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpMultiChoiceRecyclerView();
    }

    @Override
    protected int setActivityIdentifier() {
        return R.layout.activity_sample_toolbar;
    }

    @OnClick(R.id.result)
    void result() {

        if (stringList != null && mMySampleToolbarAdapter != null) {

            ArrayList<String> resultItems = new ArrayList<>();
            for (int i : mMySampleToolbarAdapter.getSelectedItemList()) {
                resultItems.add(stringList.get(i));
            }

            Intent intent = new Intent(SampleToolbarActivity.this, ResultActivity.class);
            intent.putExtra(SELECTED_ITEMS, resultItems);
            startActivity(intent);
        }
    }

    private void setUpMultiChoiceRecyclerView() {
        mMultiChoiceRecyclerView.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));

        getSampleList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        setUpAdapter(strings);
                    }
                });
    }

    private void setUpAdapter(List<String> strings) {
        stringList = new ArrayList<>(strings);

        MultiChoiceToolbar multiChoiceToolbar =
                new MultiChoiceToolbar.Builder(SampleToolbarActivity.this, toolbar)
                        .setDefaultToolbarTitle(toolbarTitle())
                        .setSelectedToolbarTitle("item selected")
                        .setMultiPrimaryColor(R.color.colorPrimaryMulti)
                        .setMultiPrimaryColorDark(R.color.colorPrimaryDarkMulti)
                        .setIcon(R.drawable.ic_arrow_back_white_24dp, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onBackPressed();
                            }
                        })
                        .build();

        mMySampleToolbarAdapter = new MySampleToolbarAdapter(stringList, getApplicationContext());
        mMySampleToolbarAdapter.setMultiChoiceToolbar(multiChoiceToolbar);

        mMultiChoiceRecyclerView.setAdapter(mMySampleToolbarAdapter);
    }

    private Observable<List<String>> getSampleList() {
        return Observable
                .range(0, 100)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "Test item " + integer;
                    }
                })
                .toList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mMySampleToolbarAdapter != null) {
            switch (item.getItemId()) {
                case R.id.select_all:
                    mMySampleToolbarAdapter.selectAll();
                    return true;
                case R.id.deselect_all:
                    mMySampleToolbarAdapter.deselectAll();
                    return true;
                case R.id.select_3:
                    mMySampleToolbarAdapter.select(2);
                    return true;
                case R.id.single_click_mode:

                    mMySampleToolbarAdapter.setSingleClickMode(!mMySampleToolbarAdapter.isInSingleClickMode());
                    Toast.makeText(getApplicationContext(), "Always Single Click Mode [" + mMySampleToolbarAdapter.isInSingleClickMode() + "]", Toast
                            .LENGTH_SHORT).show();
                    return true;
            }
        }
        return false;
    }

    @Override
    protected boolean showBackHomeAsUpIndicator() {
        return true;
    }

    @Override
    protected String toolbarTitle() {
        return getString(R.string.toolbar_controls);
    }
}
