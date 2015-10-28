package com.davidecirillo.multichoicerecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result = (TextView) findViewById(R.id.result);
        updateList();
    }

    private void updateList() {
        //Get the selected list and update the result
        result.setText("");
        List<Item> selectedItems = Item.selectedItems;

        if(selectedItems.isEmpty()) result.setText("No selected items!");
        else
            for (Item item : selectedItems) {
                result.setText(result.getText()+item.getName()+"\n");
            }
    }

}
