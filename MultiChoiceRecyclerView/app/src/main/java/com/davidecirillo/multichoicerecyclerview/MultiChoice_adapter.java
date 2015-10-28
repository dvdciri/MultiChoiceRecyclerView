package com.davidecirillo.multichoicerecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by davidecirillo on 26/10/15.
 */
public class MultiChoice_adapter extends RecyclerView.Adapter<MultiChoice_adapter.ViewHolder>{

    private Context context;
    List<Item> Items;

    public MultiChoice_adapter(Context context, List<Item> Items) {
        this.Items = Items;
        this.context = context;
    }

    public MultiChoice_adapter() {
        super();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView Item_category_name;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView, int ViewType) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);

            Item_category_name = (TextView) itemView.findViewById(R.id.Item_category_name);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.get_started_relative_layout);

            relativeLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Log.i("POSITION", String.valueOf(position));
            Item selected = Items.get(position);
            selected.toggleStateItem(selected);
        }

    }

    @Override
    public MultiChoice_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.multichoice_grid_layout_item, parent, false);
        ViewHolder vhItem = new ViewHolder(v, viewType);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(MultiChoice_adapter.ViewHolder holder, int position) {

        //set up view in obkect Item
        Items.get(position).setView(holder.itemView);

        holder.Item_category_name.setText(Items.get(position).getName());
        holder.relativeLayout.setBackgroundResource(Items.get(position).getBackground());

        //set selections
        Item Item = Items.get(position);
        Item.updateItemStateActive(Item);
    }

    @Override
    public int getItemCount() {
        return this.Items.size();
    }



    }