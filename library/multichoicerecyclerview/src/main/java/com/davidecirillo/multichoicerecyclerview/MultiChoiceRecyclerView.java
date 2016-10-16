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
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MultiChoiceRecyclerView extends RecyclerView implements MultiChoiceAdapterListener {

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private final HashMap<Integer, View> mSelectedList = new HashMap<>();
    private final HashMap<Integer, View> mAllList = new HashMap<>();
    private MultiChoiceAdapter mMultiChoiceAdapter = null;
    private MultiChoiceSelectionListener multiChoiceSelectionListener = null;

    private boolean isInSingleClickMode = false;
    private boolean isInMultiChoiceMode = false;
    private MultiChoiceToolbarHelper mMultiChoiceToolbarHelper;

    public MultiChoiceRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter instanceof MultiChoiceAdapter) {
            mMultiChoiceAdapter = ((MultiChoiceAdapter) adapter);
            mMultiChoiceAdapter.setMultiChoiceListener(this);

            for (int i = 0; i < mMultiChoiceAdapter.getItemCount(); i++) {
                mAllList.put(i, null);
            }
        } else
            try {
                throw new MultiChoiceAdapterNotFoundException();
            } catch (MultiChoiceAdapterNotFoundException e) {
                e.printStackTrace();
            }
    }


    //region MultiChoiceAdapterListener interface implementation
    @Override
    public void onSingleItemClickListener(View view, int position) {
        //Check if it's in a single mode of if there is at least one item in the selected list, before processing the click
        if (mSelectedList.size() >= 1 || isInSingleClickMode) {
            performVibrate();

            performSingleClick(view, position);
        }
    }

    @Override
    public void onSingleItemLongClickListener(View view, int position) {
        if (mSelectedList.size() == 0) {
            performVibrate();

            performSingleClick(view, position);
        }
    }


    @Override
    public void onUpdateItemListener(View view, int position) {
        if (mMultiChoiceAdapter != null && isInMultiChoiceMode) {
            if (mSelectedList.containsKey(position))
                performSelect(view, position, false);
            else
                performDeselect(view, position, false);
        }
        mAllList.put(position, view);
    }
    //endregion

    //region Public methods

    /**
     * Deselect all the selected items in the adapter
     */
    public boolean deselectAll() {
        if (mMultiChoiceAdapter != null) {

            performVibrate();

            //select all the the view
            Iterator it = mAllList.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, View> pair = (Map.Entry<Integer, View>) it.next();

                performDeselect(pair.getValue(), pair.getKey(), false);
            }

            if (multiChoiceSelectionListener != null)
                multiChoiceSelectionListener.OnDeselectAll(mSelectedList.size(), mAllList.size());

            return true;
        }
        return false;
    }


    /**
     * Select all the view in the adapter
     */
    public boolean selectAll() {
        if (mMultiChoiceAdapter != null) {

            performVibrate();

            //select all the the view
            Iterator it = mAllList.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, View> pair = (Map.Entry<Integer, View>) it.next();

                performSelect(pair.getValue(), pair.getKey(), false);
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

            performVibrate();

            performSelect(v, position, true);

            return true;
        }
        return false;
    }

    /**
     * Set the number of column with a VERTICAL layout.
     * If you call this method, it will override the setRowNumber()
     *
     * @param columnNumber number of column
     */
    public MultiChoiceRecyclerView setRecyclerColumnNumber(int columnNumber) {
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(columnNumber, StaggeredGridLayoutManager.VERTICAL);
        setLayoutManager(mStaggeredGridLayoutManager);
        return this;
    }


    /**
     * Set the number of row with a HORIZONTAL layout
     * If you call this method, it will override the setColumnNumber()
     *
     * @param rowNumber number of row
     */
    public MultiChoiceRecyclerView setRecyclerRowNumber(int rowNumber) {
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(rowNumber, StaggeredGridLayoutManager.HORIZONTAL);
        setLayoutManager(mStaggeredGridLayoutManager);
        return this;
    }


    /**
     * Set the selection of the RecyclerView to always single click (instead of first long click and then single click)
     *
     * @param set true if single click sctivated
     */
    public void setSingleClickMode(boolean set) {
        this.isInSingleClickMode = set;
        mMultiChoiceAdapter.isInSingleClickMode = set;

        //Notify adapter that something changed
        mMultiChoiceAdapter.notifyDataSetChanged();
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

    public void setMultiChoiceToolbar(MultiChoiceToolbar multiChoiceToolbar) {
        multiChoiceToolbar.setMultiChoiceRecyclerView(this);
        mMultiChoiceToolbarHelper = new MultiChoiceToolbarHelper(multiChoiceToolbar);
    }

    /**
     * @return true if the single click mode is active
     */
    public boolean isInSingleClickMode() {
        return isInSingleClickMode;
    }

    //endregion

    //region Private methods

    private void updateToolbarIfInMultiChoiceMode(int number) {
        if (isInMultiChoiceMode && mMultiChoiceToolbarHelper != null)
            mMultiChoiceToolbarHelper.updateToolbar(number);
    }

    private void updateMultiChoiceMode() {
        //every time the multi choice mode is updated and the value change
        // i want to update the adapter in order to refresh the click listeners
        if (isInMultiChoiceMode != mSelectedList.size() > 0)
            mMultiChoiceAdapter.notifyDataSetChanged();

        //update values
        isInMultiChoiceMode = mSelectedList.size() > 0;
        mMultiChoiceAdapter.isInMultiChoiceMode = mSelectedList.size() > 0;
    }

    private void performSingleClick(View view, int position) {

        if (mMultiChoiceAdapter != null) {
            if (mSelectedList.containsKey(position)) {

                performDeselect(view, position, true);
            } else {

                performSelect(view, position, true);
            }
        }

    }

    /**
     * Remember to call this method before selecting or deselection something otherwise it wont vibrate
     */
    private void performVibrate() {
        if (mSelectedList.size() == 0) {
            Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(10);
        }
    }

    private void performSelect(View v, int position, boolean withCallback) {
        mMultiChoiceAdapter.performActivation(v, true);
        mSelectedList.put(position, v);

        updateToolbarIfInMultiChoiceMode(mSelectedList.size());

        updateMultiChoiceMode();

        if (multiChoiceSelectionListener != null && withCallback)
            multiChoiceSelectionListener.OnItemSelected(position, mSelectedList.size(), mAllList.size());
    }

    private void performDeselect(View v, int position, boolean withCallback) {
        mMultiChoiceAdapter.performActivation(v, false);
        mSelectedList.remove(position);

        updateToolbarIfInMultiChoiceMode(mSelectedList.size());

        updateMultiChoiceMode();

        if (multiChoiceSelectionListener != null && withCallback)
            multiChoiceSelectionListener.OnItemDeselected(position, mSelectedList.size(), mAllList.size());
    }
    //endregion

}
