package com.davidecirillo.multichoicesample.sampleCustomView;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.davidecirillo.multichoicerecyclerview.MultiChoiceAdapter;
import com.davidecirillo.multichoicesample.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davidecirillo on 24/06/2016.
 */

class SampleCustomViewAdapter extends MultiChoiceAdapter<SampleCustomViewAdapter.SampleCustomViewHolder> {

    private final ArrayList<MessageV0> messageV0s;
    private final Context mContext;

    SampleCustomViewAdapter(ArrayList<MessageV0> messageV0s, Context context) {
        this.messageV0s = messageV0s;
        this.mContext = context;
    }

    @Override
    public SampleCustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SampleCustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sample_custom_view, parent, false));
    }

    @Override
    public void onBindViewHolder(SampleCustomViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        MessageV0 currentItem = messageV0s.get(position);

        holder.title.setText(currentItem.getMessageTitle());

        holder.summary.setText(currentItem.getMessageContent());
    }

    @Override
    public int getItemCount() {
        return messageV0s.size();
    }

    @Override
    protected void setActive(View view, boolean state) {

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.container);
        if(state){
            relativeLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorBackgroundLight));
            imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
        }else{
            relativeLayout.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
            imageView.setBackgroundColor(ContextCompat.getColor(mContext, android.R.color.transparent));
        }
    }

    @Override
    protected View.OnClickListener defaultItemViewClickListener(SampleCustomViewHolder holder, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Click on item "+position, Toast.LENGTH_SHORT).show();
            }
        };
    }

    class SampleCustomViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.title)
        public TextView title;

        @BindView(R.id.summary)
        public TextView summary;

        SampleCustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
