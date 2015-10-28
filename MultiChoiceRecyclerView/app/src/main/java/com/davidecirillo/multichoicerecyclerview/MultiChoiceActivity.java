package com.davidecirillo.multichoicerecyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MultiChoiceActivity extends AppCompatActivity {

    Button next;
    RecyclerView rw;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_choice);

        next = (Button) findViewById(R.id.next);
        rw = (RecyclerView) findViewById(R.id.recycler);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GO TO RESULT
                Intent intent = new Intent("android.intent.action.RESULT_ACTIVITY");
                startActivity(intent);

            }
        });

        //create and popolate the list with all the object
        items = new ArrayList<>();
        items.add(0,new Item(getResources().getString(R.string.Items_1), R.color.Item_color_1));
        items.add(1,new Item(getResources().getString(R.string.Items_2), R.color.Item_color_2));
        items.add(2,new Item(getResources().getString(R.string.Items_3), R.color.Item_color_3));
        items.add(3,new Item(getResources().getString(R.string.Items_4), R.color.Item_color_4));
        items.add(4,new Item(getResources().getString(R.string.Items_5), R.color.Item_color_5));
        items.add(5,new Item(getResources().getString(R.string.Items_6), R.color.Item_color_6));
        items.add(6,new Item(getResources().getString(R.string.Items_7), R.color.Item_color_7));
        items.add(7,new Item(getResources().getString(R.string.Items_8), R.color.Item_color_8));
        items.add(8, new Item(getResources().getString(R.string.Items_9), R.color.Item_color_9));

        //Set up the card view
        rw.setHasFixedSize(true);
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(3, 1);
        rw.setLayoutManager(gaggeredGridLayoutManager);

        //set up the adapter
        MultiChoice_adapter rcAdapter = new MultiChoice_adapter(MultiChoiceActivity.this, items);
        rw.setAdapter(rcAdapter);

    }

}
