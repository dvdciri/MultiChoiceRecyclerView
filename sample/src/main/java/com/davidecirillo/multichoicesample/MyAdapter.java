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

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.davidecirillo.multichoicerecyclerview.MultiChoiceAdapter;

import java.util.ArrayList;

/**
 * Created by davidecirillo on 13/03/16.
 */
public class MyAdapter extends MultiChoiceAdapter<MyViewHolder> {

    ArrayList<String> mList;
    Context mContext;

    public MyAdapter(ArrayList<String> stringList, Context context) {
        this.mList = stringList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.multichoice_grid_layout_item, parent, false));
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.mTextView.setText(mList.get(position));
    }


    /**
     * Override this method to implement a custom active/deactive state
     */
    @Override
    public void setActive(View view, boolean state) {

        RelativeLayout relativeLayout = (RelativeLayout) findViewByClass(view, Class.RELATIVE_LAYOUT);

        if(relativeLayout != null){
            if(state){
                relativeLayout.setBackgroundColor(Color.GREEN);
            }else{
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        }
    }

    private View findViewByClass(View rootView, Class clazz){
        for (int i = 0; i< ((ViewGroup) rootView).getChildCount(); i++){
            View view = ((ViewGroup)rootView).getChildAt(i);

            switch (clazz){
                case RELATIVE_LAYOUT:
                    if(view instanceof RelativeLayout)
                        return view;
                    break;
                case TEXT_VIEW:
                    if(view instanceof TextView)
                        return view;
                    break;
            }
        }
        return null;
    }

    private enum Class{
        RELATIVE_LAYOUT,
        TEXT_VIEW
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
