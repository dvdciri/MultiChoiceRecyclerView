package com.davidecirillo.multichoicerecyclerview;

/**
 * Created by davidecirillo on 13/03/16.
 */
class MultiChoiceAdapterNotFoundException extends Exception {

    MultiChoiceAdapterNotFoundException() {
        super("The adapter of this RecyclerView is not extending the MultiChoiceAdapter class");
    }
}
