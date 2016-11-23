package com.davidecirillo.multichoicerecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


public abstract class MultiChoiceAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    final static String EXCEPTION_MSG_NO_INTERFACE = "An interface must be set in order to make this adapter working";

    boolean isInMultiChoiceMode = false;
    boolean isInSingleClickMode = false;

    private MultiChoiceAdapterListener mMultiChoiceListener;

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        final View mCurrentView = holder.itemView;

        if (mMultiChoiceListener != null) {

            if (isInMultiChoiceMode || isInSingleClickMode) {
                mCurrentView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMultiChoiceListener.onSingleItemClickListener(mCurrentView, holder.getAdapterPosition());
                    }
                });
            } else if (defaultItemViewClickListener(holder, position) != null) {
                mCurrentView.setOnClickListener(defaultItemViewClickListener(holder, position));
            }

            mCurrentView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mMultiChoiceListener.onSingleItemLongClickListener(mCurrentView, holder.getAdapterPosition());
                    return true;
                }
            });

            mMultiChoiceListener.onUpdateItemListener(mCurrentView, holder.getAdapterPosition());

        } else {
            throw new IllegalStateException(EXCEPTION_MSG_NO_INTERFACE);
        }
    }


    void setMultiChoiceListener(MultiChoiceAdapterListener multiChoiceListener) {
        this.mMultiChoiceListener = multiChoiceListener;
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
}
