package com.davidecirillo.multichoicerecyclerview;

/**
 * Created by davidecirillo on 13/03/16.
 */
public class MultiChoiceAdapterNotFoundException extends Exception {

    public MultiChoiceAdapterNotFoundException() {
        super("The adapter of this RecyclerView is not extending the MultiChoiceAdapter class");
    }
}
