package com.davidecirillo.multichoicerecyclerview.listeners;

import android.view.View;

/**
 * Created by davidecirillo on 02/06/2016.
 */

public interface MultiChoiceAdapterListener {

    void onSingleItemClickListener(View view, int position);

    void onSingleItemLongClickListener(View view, int position);

    void onUpdateItemListener(View view, int position);
}
