package com.davidecirillo.multichoicesample.SampleCustomView;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.davidecirillo.multichoicerecyclerview.MultiChoiceRecyclerView;
import com.davidecirillo.multichoicerecyclerview.MultiChoiceToolbar;
import com.davidecirillo.multichoicesample.BaseActivity;
import com.davidecirillo.multichoicesample.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SampleCustomActivity extends BaseActivity {

    @BindView(R.id.multiChoiceRecyclerView)
    public MultiChoiceRecyclerView multiChoiceRecyclerView;

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

        multiChoiceRecyclerView.setRecyclerColumnNumber(1);

        MultiChoiceToolbar multiChoiceToolbar = new MultiChoiceToolbar.Builder(this, toolbar)
                .setDefaultToolbarTitle(getString(R.string.app_name))
                .setSelectedToolbarTitle("")
                .setMultiPrimaryColor(R.color.colorPrimaryMulti)
                .setMultiPrimaryColorDark(R.color.colorPrimaryDarkMulti)
                .build();
        multiChoiceRecyclerView.setMultiChoiceToolbar(multiChoiceToolbar);

        getSampleMessageList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<List<MessageV0>>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(List<MessageV0> messageV0s) {

                        SampleCustomViewAdapter adapter = new SampleCustomViewAdapter(new ArrayList<>(messageV0s), SampleCustomActivity.this);
                        multiChoiceRecyclerView.setAdapter(adapter);
                    }
                });
    }

    private Observable<List<MessageV0>> getSampleMessageList() {
        return Observable
                .range(0, 30)
                .map(new Func1<Integer, MessageV0>() {
                    @Override
                    public MessageV0 call(Integer integer) {
                        return new MessageV0("Title message number " + integer, "Lorem ipsum dolor "+integer+" sit amet, consectetur adipiscing elit. Donec id mi pharetra, porta felis sed, aliquam urna. Curabitur porta dolor lobortis semper dictum. Vestibulum posuere velit nisl, at porta lectus condimentum vel. Duis pharetra auctor tempor. Proin feugiat turpis vel tincidunt molestie. Pellentesque tincidunt felis vitae leo pharetra aliquam. Donec sapien ante, feugiat at eleifend vel, laoreet vitae neque. Donec eu erat et diam fermentum congue a sed libero. Vivamus dapibus nunc nec posuere elementum. Nunc eget mi sed est finibus ultricies. Nam ut sapien feugiat neque tempus feugiat.");
                    }
                })
                .toList();
    }
}
