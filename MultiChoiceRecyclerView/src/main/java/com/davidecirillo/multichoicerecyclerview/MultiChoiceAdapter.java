package com.davidecirillo.multichoicerecyclerview;

import android.view.View;
import android.view.ViewGroup;

import com.davidecirillo.multichoicerecyclerview.listeners.MultiChoiceAdapterListener;


/**
 * Created by davidecirillo on 12/03/16.
 */
public abstract class MultiChoiceAdapter<VH extends MultiChoiceRecyclerView.ViewHolder> extends MultiChoiceRecyclerView.Adapter<VH> {

    private MultiChoiceAdapterListener mMultiChoiceListener;

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        final View mCurrentView = holder.itemView;

        if (mMultiChoiceListener != null) {
            mCurrentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMultiChoiceListener.onSingleItemClickListener(mCurrentView, holder.getAdapterPosition());
                }
            });

            mCurrentView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    mMultiChoiceListener.onSingleItemLongClickListener(mCurrentView, holder.getAdapterPosition());
                    return true;
                }
            });

            mMultiChoiceListener.onUpdateItemListener(mCurrentView, holder.getAdapterPosition());
        }
    }

    public void setMultiChoiceListener(MultiChoiceAdapterListener multiChoiceListener) {
        this.mMultiChoiceListener = multiChoiceListener;
    }

    public void performActivation(View view, boolean state){
        if(view != null){
            setActive(view, state);
        }
    }

    /**
     * Override this method to customise the active item
     *
     * @param state true if the state is active/selected
     */
    protected void setActive(View view, boolean state) {
        if (state) {
            view.setAlpha(0.25f);
        } else {
            view.setAlpha(1f);
        }
    }
}
