package com.davidecirillo.multichoicesample;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by davidecirillo on 12/03/16.
 */
public abstract class MultiChoiceAdapter<VH extends MultiChoiceRecyclerView.ViewHolder> extends MultiChoiceRecyclerView.Adapter<VH> {

    MultiChoiceListener mMultiChoiceListener;

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        final View mCurrentView = holder.itemView;

        if (mMultiChoiceListener != null) {
            mCurrentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMultiChoiceListener.onSingleItemClickListener(mCurrentView, position);
                }
            });

            mMultiChoiceListener.onUpdateItemListener(mCurrentView, position);
        }
    }

    public void setMultiChoiceListener(MultiChoiceListener multiChoiceListener) {
        mMultiChoiceListener = multiChoiceListener;
    }

    /**
     * Override this method to customise the active item
     *
     * @param state true if the state is active/selected
     */
    public void setActive(View view, boolean state) {
        if(view != null) {
            if (state) {
                view.setAlpha(0.25f);
            } else {
                view.setAlpha(1f);
            }
        }
    }
}
