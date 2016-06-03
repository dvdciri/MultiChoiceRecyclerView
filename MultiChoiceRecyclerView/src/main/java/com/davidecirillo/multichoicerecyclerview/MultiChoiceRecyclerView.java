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

package com.davidecirillo.multichoicerecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.davidecirillo.multichoicerecyclerview.listeners.MultiChoiceAdapterListener;
import com.davidecirillo.multichoicerecyclerview.listeners.MultiChoiceSelectionListener;
import com.davidecirillo.multichoicesample.MultiChoiceAdapterNotFoundException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by davidecirillo on 12/03/16.
 */

public class MultiChoiceRecyclerView extends RecyclerView implements MultiChoiceAdapterListener {

    private String EXCEPTION_MESSAGE_ADAPTER = "The adapter of this RecyclerView is not extending the MultiChoiceAdapter class";

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private HashMap<Integer, View> mSelectedList = new HashMap<>();
    private HashMap<Integer, View> mAllList = new HashMap<>();
    private MultiChoiceAdapter mMultiChoiceAdapter = null;
    private MultiChoiceSelectionListener multiChoiceSelectionListener = null;

    public MultiChoiceRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter instanceof MultiChoiceAdapter) {
            mMultiChoiceAdapter = ((MultiChoiceAdapter) adapter);
            mMultiChoiceAdapter.setMultiChoiceListener(this);

            for(int i = 0; i < mMultiChoiceAdapter.getItemCount(); i++){
                mAllList.put(i, null);
            }
        } else
            try {
                throw new MultiChoiceAdapterNotFoundException(EXCEPTION_MESSAGE_ADAPTER);
            } catch (MultiChoiceAdapterNotFoundException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onSingleItemClickListener(View view, int position) {
        if (mMultiChoiceAdapter != null) {
            if (mSelectedList.containsKey(position)) {
                mMultiChoiceAdapter.setActive(view, false);
                mSelectedList.remove(position);

                if (multiChoiceSelectionListener != null)
                    multiChoiceSelectionListener.OnItemDeselected(position, mSelectedList.size(), mAllList.size());
            } else {
                mMultiChoiceAdapter.setActive(view, true);
                mSelectedList.put(position, view);

                if (multiChoiceSelectionListener != null)
                    multiChoiceSelectionListener.OnItemSelected(position, mSelectedList.size(), mAllList.size());
            }
        }
    }

    @Override
    public void onUpdateItemListener(View view, int position) {
        if (mMultiChoiceAdapter != null) {
            if (mSelectedList.containsKey(position))
                mMultiChoiceAdapter.setActive(view, true);
            else
                mMultiChoiceAdapter.setActive(view, false);
        }
        mAllList.put(position, view);
    }




    /*
    *
    * MultiChoice methods ************************************
    * */


    /**
     * Set the number of column with a VERTICAL layout.
     * <p/>
     * If you call this method, it will override the setRowNumber()
     *
     * @param columnNumber number of column
     */
    public void setRecyclerColumnNumber(int columnNumber) {
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(columnNumber, StaggeredGridLayoutManager.VERTICAL);
        setLayoutManager(mStaggeredGridLayoutManager);
    }


    /**
     * Set the number of row with a HORIZONTAL layout
     * <p/>
     * If you call this method, it will override the setColumnNumber()
     *
     * @param rowNumber number of row
     */
    public void setRecyclerRowNumber(int rowNumber) {
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(rowNumber, StaggeredGridLayoutManager.HORIZONTAL);
        setLayoutManager(mStaggeredGridLayoutManager);
    }


    /**
     * Deselect all the selected items in the adapter
     */
    public boolean deselectAll() {
        if (mMultiChoiceAdapter != null) {
            //select all the the view
            Iterator it = mAllList.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, View> pair = (Map.Entry<Integer, View>) it.next();
                mMultiChoiceAdapter.setActive(pair.getValue(), false);
            }
            mSelectedList.clear();

            if (multiChoiceSelectionListener != null)
                multiChoiceSelectionListener.OnSelectAll(mSelectedList.size(), mAllList.size());
            return true;
        }
        return false;
    }

    /**
     * Select all the view in the adapter
     */
    public boolean selectAll() {
        if (mMultiChoiceAdapter != null) {
            //select all the the view
            Iterator it = mAllList.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, View> pair = (Map.Entry<Integer, View>) it.next();
                mMultiChoiceAdapter.setActive(pair.getValue(), true);
                mSelectedList.put(pair.getKey(), pair.getValue());
            }

            if (multiChoiceSelectionListener != null)
                multiChoiceSelectionListener.OnSelectAll(mSelectedList.size(), mAllList.size());
            return true;
        }
        return false;
    }

    /**
     * Select a view from position in the adapter
     *
     * @param position the position of the view in the adapter
     */
    public boolean select(int position) {
        View v = mAllList.get(position);
        if (mMultiChoiceAdapter != null) {
            mMultiChoiceAdapter.setActive(v, true);
            mSelectedList.put(position, v);
            return true;
        }
        return false;
    }

    /**
     * Method to get the number of item in the adapter
     *
     * @return number of all item in the adapter
     */
    public int getAllItemCount() {
        return mMultiChoiceAdapter.getItemCount();
    }

    /**
     * Method to get the number of selected items
     *
     * @return number of selected items
     */
    public int getSelectedItemCount() {
        return mSelectedList.size();
    }

    /**
     * Get the list of selected item
     *
     * @return Collection of all the selected position in the adapter
     */
    public Collection<Integer> getSelectedItemList() {
        return mSelectedList.keySet();
    }

    public void setMultiChoiceSelectionListener(MultiChoiceSelectionListener multiChoiceSelectionListener) {
        this.multiChoiceSelectionListener = multiChoiceSelectionListener;
    }
}
