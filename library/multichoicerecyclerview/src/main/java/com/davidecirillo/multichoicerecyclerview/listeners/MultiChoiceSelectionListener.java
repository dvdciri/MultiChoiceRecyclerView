package com.davidecirillo.multichoicerecyclerview.listeners;

/**
 * Created by davidecirillo on 02/06/2016.
 */

public interface MultiChoiceSelectionListener{

    void OnItemSelected(int selectedPosition, int itemSelectedCount, int allItemCount);

    void OnItemDeselected(int deselectedPosition, int itemSelectedCount, int allItemCount);

    void OnSelectAll(int itemSelectedCount, int allItemCount);

    void OnDeselectAll(int itemSelectedCount, int allItemCount);
}