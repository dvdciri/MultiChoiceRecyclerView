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

package com.davidecirillo.multichoicesample.sampleToolbar;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.davidecirillo.multichoicesample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davidecirillo on 13/03/16.
 */
public class MySampleToolbarViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.Item_category_name)
    public TextView mTextView;

    public MySampleToolbarViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}