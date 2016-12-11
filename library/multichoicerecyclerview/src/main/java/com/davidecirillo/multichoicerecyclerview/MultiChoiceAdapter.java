package com.davidecirillo.multichoicerecyclerview;

import android.Manifest;
import android.content.Context;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public abstract class MultiChoiceAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements MultiChoiceToolbar.Listener {

    private static final String TAG = "MultiChoiceAdapter";
    private final LinkedHashMap<Integer, View> mSelectedList = new LinkedHashMap<>();
    private final LinkedHashMap<Integer, View> mAllList = new LinkedHashMap<>();
    private MultiChoiceSelectionListener multiChoiceSelectionListener = null;
    private MultiChoiceToolbarHelper mMultiChoiceToolbarHelper;
    private boolean isInMultiChoiceMode;
    private boolean isInSingleClickMode;
    private RecyclerView mRecyclerView;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;

        for (int i = 0; i < getItemCount(); i++) {
            mAllList.put(i, null);
        }
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        final View mCurrentView = holder.itemView;

        if ((isInMultiChoiceMode || isInSingleClickMode) && isSelectableInMultiChoiceMode(position)) {
            mCurrentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    processSingleClick(mCurrentView, holder.getAdapterPosition());
                }
            });
        } else if (defaultItemViewClickListener(holder, holder.getAdapterPosition()) != null) {
            mCurrentView.setOnClickListener(defaultItemViewClickListener(holder, holder.getAdapterPosition()));
        }

        mCurrentView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                processLongClick(mCurrentView, holder.getAdapterPosition());
                return true;
            }
        });

        processUpdate(mCurrentView, holder.getAdapterPosition());
    }

    /**
     * Override this method to customize the active item
     *
     * @param view  the view to customize
     * @param state true if the state is active/selected
     */
    public void setActive(@NonNull View view, boolean state) {
        if (state) {
            view.setAlpha(0.25f);
        } else {
            view.setAlpha(1f);
        }
    }

    /**
     * Provide the default behaviour for the item click with multi choice mode disabled
     *
     * @return the onClick action to perform when multi choice selection is off
     */
    protected View.OnClickListener defaultItemViewClickListener(VH holder, int position) {
        return null;
    }

    protected boolean isSelectableInMultiChoiceMode(int position) {
        return true;
    }

    private void processSingleClick(View view, int position) {
        //Check if it's in a single mode of if there is at least one item in the selected list, before processing the click
        if (mSelectedList.size() >= 1 || isInSingleClickMode) {
            processClick(view, position);
        }
    }

    private void processLongClick(View view, int position) {
        if (mSelectedList.size() == 0) {
            processClick(view, position);
        }
    }

    private void processUpdate(View view, int position) {
        if (isInMultiChoiceMode) {
            if (mSelectedList.containsKey(position)) {
                perform(Action.SELECT, view, position, false, false);
            } else {
                perform(Action.DESELECT, view, position, false, false);
            }
        }
        mAllList.put(position, view);
    }

    //region Public methods

    /**
     * Deselect all the selected items in the adapter
     */
    public void deselectAll() {
        performAll(Action.DESELECT);
    }

    /**
     * Select all the view in the adapter
     */
    public void selectAll() {
        performAll(Action.SELECT);
    }


    /**
     * Select a view from position in the adapter
     *
     * @param position the position of the view in the adapter
     */
    public void select(int position) {
        perform(Action.SELECT, mAllList.get(position), position, true, true);
    }


    /**
     * Set the selection of the RecyclerView to always single click (instead of first long click and then single click)
     *
     * @param set true if single click sctivated
     */
    public void setSingleClickMode(boolean set) {
        isInSingleClickMode = set;
        notifyDataSetChanged();
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
        multiChoiceToolbar.setToolbarListener(this);
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

    private void processClick(View view, int position) {
        if (mSelectedList.containsKey(position)) {
            perform(Action.DESELECT, view, position, true, true);
        } else {
            perform(Action.SELECT, view, position, true, true);
        }
    }

    /**
     * Remember to call this method before selecting or deselection something otherwise it won't vibrate
     */
    private void performVibrate() {
        if (mSelectedList.size() == 0 && mRecyclerView != null) {
            if (ContextCompat.checkSelfPermission(mRecyclerView.getContext(), Manifest.permission.VIBRATE) == PermissionChecker.PERMISSION_GRANTED) {
                Vibrator v = (Vibrator) mRecyclerView.getContext().getSystemService(Context.VIBRATOR_SERVICE);
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
                setActive(v, true);
            }
            mSelectedList.put(position, v);

            if (multiChoiceSelectionListener != null && withCallback) {
                multiChoiceSelectionListener.OnItemSelected(position, mSelectedList.size(), mAllList.size());
            }
        } else {
            if (v != null) {
                setActive(v, false);
            }
            mSelectedList.remove(position);

            if (multiChoiceSelectionListener != null && withCallback) {
                multiChoiceSelectionListener.OnItemDeselected(position, mSelectedList.size(), mAllList.size());
            }
        }

        if (isInMultiChoiceMode && mMultiChoiceToolbarHelper != null) {
            mMultiChoiceToolbarHelper.updateToolbar(mSelectedList.size());
        }

        if (isInMultiChoiceMode != mSelectedList.size() > 0) {
            isInMultiChoiceMode = mSelectedList.size() > 0;
            Log.d(TAG, "perform: updating isInMultiChoiceMode to '" + isInMultiChoiceMode + "'");
            notifyDataSetChanged();
        }
    }

    private void performAll(Action action) {
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
    }

    @Override
    public void onClearButtonPressed() {
        performAll(Action.DESELECT);
    }

//endregion

    private enum Action {
        SELECT,
        DESELECT
    }
}
