package com.davidecirillo.multichoicesample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.davidecirillo.multichoicerecyclerview.MultiChoiceRecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MultiChoiceRecyclerView mMultiChoiceRecyclerView;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMultiChoiceRecyclerView = (MultiChoiceRecyclerView) findViewById(R.id.multiChoiceRecyclerView);
        mMultiChoiceRecyclerView.setRecyclerColumnNumber(3);

        final ArrayList<String> mList = new ArrayList<>();
        for(int i = 0; i<100; i++){
            mList.add("Test"+i);
        }

        MyAdapter myAdapter = new MyAdapter(mList, getApplicationContext());
        mMultiChoiceRecyclerView.setAdapter(myAdapter);

        next = (Button) findViewById(R.id.result);
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
    }
}
