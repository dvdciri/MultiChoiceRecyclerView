package com.davidecirillo.multichoicesample;

import android.view.View;

/**
 * Created by davidecirillo on 13/03/16.
 */
public interface MultiChoiceListener {

    /**
     * When an item is selected
     */
    void onSingleItemClickListener(View view, int position);

    void onUpdateItemListener(View view, int position);
}
