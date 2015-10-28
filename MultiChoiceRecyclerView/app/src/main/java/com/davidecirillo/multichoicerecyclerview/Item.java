package com.davidecirillo.multichoicerecyclerview;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Item {

    public String name;
    public int background;
    public View view;
    public static List<Item> selectedItems = new ArrayList<>();

    Item(String name, int background){
        this.name = name;
        this.background = background;
    }

    /*
    * @function getName: get the name of the item
    */
    public String getName(){
        return name;
    }

    /*
    * @function getBackground: get the background of the item
    */
    public int getBackground(){
        return background;
    }

    /*
    * @function setView: set the view of the item
    * @param view: the view passed by the view holder
    */
    public void setView(View view){
        this.view = view;
    }

    /*
    * @function getPositionByName: get the position of the Item in a list of Items
    * @param list: the source list
    * @param name: the name to search for
    */
    public int getPositionByName(List<Item> list, String name){
        int p = 0;
        for (final Item Item : list) {
            if(Item.getName().equals(name)){
                return p;
            }
            p++;
        }
        return -1;
    }

    /*
    * @function updateItemStateActive: update the state active of the item
    * @param Item: item to update
    */
    public void updateItemStateActive(Item Item){
        if(isInSelectedList(Item)){
            setActive(true);
        }else{
            setActive(false);
        }
    }

    /*
    * @function isInSelectedList: check if the item is in the selected list
    * @param search: item to search for
    */
    public boolean isInSelectedList(Item search){
        for (Item Item : selectedItems) {
            if (Item.getName().equals(search.getName())) {
                return true;
            }
        }
        return false;
    }

    /*
    * @function toggleStateItem: toggle the state of the item
    * @param Item: item to toggle
    */
    public void toggleStateItem(Item Item){
        if(Item.isInSelectedList(Item)){
            setActive(false);
            selectedItems.remove(Item.getPositionByName(selectedItems, Item.getName()));
            Log.i("TOGGLE", "Removed " + Item.getName());
        }else{
            setActive(true);
            if(selectedItems.add(Item));
            Log.i("TOGGLE","Added "+Item.getName());
        }
    }

    /*
    * @function setActive: function that active or disactive the view of the item
    * @param state: true for activation/false for disactivation
    */
    public void setActive(boolean state) {

        /*
        * HERE YOU CAN CHANGE HOW THE ITEM VIEW HATE TO BE ACTIVATE ON THE UI
        * IN THIS EXAMPLE WE ONLY CHANGE THE ALPHA
        */

        if (state) {
            view.setAlpha(0.25f);
        } else {
            view.setAlpha(1f);
        }
    }

}