package com.davidecirillo.multichoicesample.sampleToolbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.davidecirillo.multichoicerecyclerview.MultiChoiceRecyclerView;
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

    @BindView(R.id.multiChoiceRecyclerView)
    MultiChoiceRecyclerView mMultiChoiceRecyclerView;

    private ArrayList<String> stringList;

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

        if (stringList != null) {

            ArrayList<String> resultItems = new ArrayList<String>();
            for (int i : mMultiChoiceRecyclerView.getSelectedItemList()) {
                resultItems.add(stringList.get(i));
            }

            Intent intent = new Intent(SampleToolbarActivity.this, ResultActivity.class);
            intent.putExtra("selectedItems", resultItems);
            startActivity(intent);
        }
    }

    private void setUpMultiChoiceRecyclerView() {
        mMultiChoiceRecyclerView.setRecyclerColumnNumber(3);

        mMultiChoiceRecyclerView.setMultiChoiceToolbar(this,
                toolbar,
                getString(R.string.app_name),
                "item selected",
                R.color.colorPrimaryMulti, R.color.colorPrimaryDarkMulti);

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
                        stringList = new ArrayList<String>(strings);

                        MySampleToolbarAdapter mySampleToolbarAdapter = new MySampleToolbarAdapter(stringList, getApplicationContext());
                        mMultiChoiceRecyclerView.setAdapter(mySampleToolbarAdapter);
                    }
                });
    }

    private Observable<List<String>> getSampleList() {
        return Observable
                .range(0, 100)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "Test item "+ integer;
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

        switch (item.getItemId()){
            case R.id.select_all:
                mMultiChoiceRecyclerView.selectAll();
                return true;
            case R.id.deselect_all:
                mMultiChoiceRecyclerView.deselectAll();
                return true;
            case R.id.select_3:
                mMultiChoiceRecyclerView.select(2);
                return true;
            case R.id.single_click_mode:

                mMultiChoiceRecyclerView.setSingleClickMode(!mMultiChoiceRecyclerView.isInSingleClickMode());
                Toast.makeText(getApplicationContext(), "Always Single Click Mode ["+mMultiChoiceRecyclerView.isInSingleClickMode()+"]", Toast.LENGTH_SHORT).show();
                return true;
        }

        return false;
    }

}
