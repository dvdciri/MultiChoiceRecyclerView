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

import android.Manifest;
import android.content.Context;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MultiChoiceRecyclerView extends RecyclerView implements MultiChoiceAdapter.SelectionListener {


    private final HashMap<Integer, View> mSelectedList = new HashMap<>();
    private final HashMap<Integer, View> mAllList = new HashMap<>();
    private MultiChoiceAdapter mMultiChoiceAdapter = null;
    private MultiChoiceSelectionListener multiChoiceSelectionListener = null;

    private MultiChoiceToolbarHelper mMultiChoiceToolbarHelper;

    public MultiChoiceRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MultiChoiceRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MultiChoiceRecyclerView(Context context) {
        super(context);
        init();
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
        } else {
            throw new IllegalStateException("The adapter of this RecyclerView is not extending the MultiChoiceAdapter class");
        }
    }

    //region SelectionListener interface implementation
    @Override
    public void onSingleItemClickListener(View view, int position) {
        //Check if it's in a single mode of if there is at least one item in the selected list, before processing the click
        if (mSelectedList.size() >= 1 || mMultiChoiceAdapter.isInSingleClickMode) {
            performSingleClick(view, position);
        }
    }

    @Override
    public void onSingleItemLongClickListener(View view, int position) {
        if (mSelectedList.size() == 0) {
            performSingleClick(view, position);
        }
    }


    @Override
    public void onUpdateItemListener(View view, int position) {
        if (mMultiChoiceAdapter != null && mMultiChoiceAdapter.isInMultiChoiceMode) {
            if (mSelectedList.containsKey(position)) {
                perform(Action.SELECT, view, position, false, false);
            } else {
                perform(Action.DESELECT, view, position, false, false);
            }
        }
        mAllList.put(position, view);
    }
    //endregion

    //region Public methods

    /**
     * Deselect all the selected items in the adapter
     */
    public boolean deselectAll() {
        return performAll(Action.DESELECT);
    }

    /**
     * Select all the view in the adapter
     */
    public boolean selectAll() {
        return performAll(Action.SELECT);
    }


    /**
     * Select a view from position in the adapter
     *
     * @param position the position of the view in the adapter
     */
    public boolean select(int position) {
        if (mMultiChoiceAdapter != null) {
            perform(Action.SELECT, mAllList.get(position), position, true, true);
            return true;
        }
        return false;
    }


    /**
     * Set the selection of the RecyclerView to always single click (instead of first long click and then single click)
     *
     * @param set true if single click sctivated
     */
    public void setSingleClickMode(boolean set) {
        mMultiChoiceAdapter.isInSingleClickMode = set;

        //Notify adapter that something changed
        mMultiChoiceAdapter.notifyDataSetChanged();
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
        return mMultiChoiceAdapter.isInSingleClickMode;
    }

    //endregion

    //region Private methods

    private void init() {
        if (getLayoutManager() == null) {
            setLayoutManager(new LinearLayoutManager(getContext(), VERTICAL, false));
        }
    }

    private void updateToolbarIfInMultiChoiceMode(int number) {
        if (mMultiChoiceAdapter.isInMultiChoiceMode && mMultiChoiceToolbarHelper != null)
            mMultiChoiceToolbarHelper.updateToolbar(number);
    }

    private void updateMultiChoiceMode() {
        //every time the multi choice mode is updated and the value change
        // i want to update the adapter in order to refresh the click listeners
        if (mMultiChoiceAdapter.isInMultiChoiceMode != mSelectedList.size() > 0) {

            mMultiChoiceAdapter.isInMultiChoiceMode = mSelectedList.size() > 0;

            mMultiChoiceAdapter.notifyDataSetChanged();
        }
    }

    private void performSingleClick(View view, int position) {
        if (mMultiChoiceAdapter != null) {
            if (mSelectedList.containsKey(position)) {
                perform(Action.DESELECT, view, position, true, true);
            } else {
                perform(Action.SELECT, view, position, true, true);
            }
        }
    }

    /**
     * Remember to call this method before selecting or deselection something otherwise it won't vibrate
     */
    private void performVibrate() {
        if (mSelectedList.size() == 0) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.VIBRATE) == PermissionChecker.PERMISSION_GRANTED) {
                Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(10);
            }
        }
    }

    private void perform(Action action, View v, int position, boolean withCallback, boolean withVibration) {
        if (withVibration) {
            performVibrate();
        }

        if (action == Action.SELECT) {
            if (v != null) {
                mMultiChoiceAdapter.setActive(v, true);
            }
            mSelectedList.put(position, v);

            if (multiChoiceSelectionListener != null && withCallback) {
                multiChoiceSelectionListener.OnItemSelected(position, mSelectedList.size(), mAllList.size());
            }
        } else {
            if (v != null) {
                mMultiChoiceAdapter.setActive(v, false);
            }
            mSelectedList.remove(position);

            if (multiChoiceSelectionListener != null && withCallback) {
                multiChoiceSelectionListener.OnItemDeselected(position, mSelectedList.size(), mAllList.size());
            }
        }
        updateToolbarIfInMultiChoiceMode(mSelectedList.size());

        updateMultiChoiceMode();
    }

    private boolean performAll(Action action) {
        if (mMultiChoiceAdapter != null) {

            performVibrate();

            //select all the the view
            Iterator it = mAllList.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, View> pair = (Map.Entry<Integer, View>) it.next();

                perform(action, pair.getValue(), pair.getKey(), false, false);
            }

            if (multiChoiceSelectionListener != null) {
                if (action == Action.SELECT) {
                    multiChoiceSelectionListener.OnSelectAll(mSelectedList.size(), mAllList.size());
                } else {
                    multiChoiceSelectionListener.OnDeselectAll(mSelectedList.size(), mAllList.size());
                }
            }
            return true;
        }
        return false;
    }

    enum Action {
        SELECT,
        DESELECT
    }
    //endregion

}
