/*
 * Copyright (c) 2014 Davide Cirillo
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *     Come on, don't tell me you read that.
 */

package com.davidecirillo.multichoicesample;

import android.widget.TextView;

import com.davidecirillo.multichoicesample.BaseActivity;
import com.davidecirillo.multichoicesample.R;

import java.util.ArrayList;

import butterknife.BindView;

public class ResultActivity extends BaseActivity {

    @BindView(R.id.result)
    TextView result;

    @Override
    protected void onStart() {
        super.onStart();
        updateList();
    }

    @Override
    protected int setActivityIdentifier() {
        return R.layout.activity_result;
    }

    private void updateList() {

        if(getIntent().hasExtra("selectedItems")) {
            ArrayList<String> selectedItems = (ArrayList<String>) getIntent().getSerializableExtra("selectedItems");
            for (String string : selectedItems) {
                result.setText(result.getText() + "\n" + string);
            }
        }else
            finish();
    }

}
