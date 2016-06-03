package com.davidecirillo.multichoicesample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.davidecirillo.multichoicerecyclerview.MultiChoiceRecyclerView;
import com.davidecirillo.multichoicerecyclerview.listeners.MultiChoiceSelectionListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MultiChoiceSelectionListener {

    MultiChoiceRecyclerView mMultiChoiceRecyclerView;
    Button next, deselect_all, select_all;
    TextView all_item_count, select_item_count;

    ArrayList<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next = (Button) findViewById(R.id.result);
        deselect_all = (Button) findViewById(R.id.deselect_all);
        select_all = (Button) findViewById(R.id.select_all);
        all_item_count = (TextView) findViewById(R.id.all_item_count);
        select_item_count = (TextView) findViewById(R.id.selecte_item_count);

        setUpMultiChoiceRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> resultItems = new ArrayList<String>();
                for (int i : mMultiChoiceRecyclerView.getSelectedItemList()){
                    resultItems.add(mList.get(i));
                }

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("selectedItems", resultItems);
                startActivity(intent);
            }
        });

        deselect_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMultiChoiceRecyclerView.deselectAll();
            }
        });

        select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMultiChoiceRecyclerView.selectAll();
            }
        });
    }

    private void setUpMultiChoiceRecyclerView(){
        mMultiChoiceRecyclerView = (MultiChoiceRecyclerView) findViewById(R.id.multiChoiceRecyclerView);
        mMultiChoiceRecyclerView.setRecyclerColumnNumber(3);
        mMultiChoiceRecyclerView.setMultiChoiceSelectionListener(this);

        mList = new ArrayList<>();
        for(int i = 0; i<100; i++){
            mList.add("Test"+i);
        }

        MyAdapter myAdapter = new MyAdapter(mList, getApplicationContext());
        mMultiChoiceRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public void OnItemSelected(int selectedPosition, int itemSelectedCount, int allItemCount) {
        all_item_count.setText("All item count: "+allItemCount);
        select_item_count.setText("Selected item count: "+itemSelectedCount);
    }

    @Override
    public void OnItemDeselected(int deselectedPosition, int itemSelectedCount, int allItemCount) {
        all_item_count.setText("All item count: "+allItemCount);
        select_item_count.setText("Selected item count: "+itemSelectedCount);
    }

    @Override
    public void OnSelectAll(int itemSelectedCount, int allItemCount) {
        all_item_count.setText("All item count: "+allItemCount);
        select_item_count.setText("Selected item count: "+itemSelectedCount);
    }

    @Override
    public void OnDeselectAll(int itemSelectedCount, int allItemCount) {
        all_item_count.setText("All item count: "+allItemCount);
        select_item_count.setText("Selected item count: "+itemSelectedCount);
    }
}
