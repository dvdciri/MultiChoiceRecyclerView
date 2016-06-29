package com.davidecirillo.multichoicesample.sampleImageGallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.davidecirillo.multichoicerecyclerview.MultiChoiceAdapter;
import com.davidecirillo.multichoicesample.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davidecirillo on 27/06/2016.
 */

class SampleGalleryAdapter extends MultiChoiceAdapter<SampleGalleryAdapter.SampleGalleryViewHolder> {

    private final Context mContext;

    SampleGalleryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    @Override
    public SampleGalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SampleGalleryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sample_gallery, parent, false));
    }

    @Override
    public void onBindViewHolder(SampleGalleryViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);

        Picasso picasso = Picasso.with(mContext);

        picasso
                .load("https://unsplash.it/200/200?image="+position)
                .error(R.drawable.placeholder)
                .into(holder.mImageView);

    }


    @Override
    protected void setActive(View view, boolean state) {

        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        final ImageView tickImage = (ImageView) view.findViewById(R.id.tick_image);

        //Select animation
        ScaleAnimation selectAnimation = new ScaleAnimation(
                1f, 0.8f,
                1f, 0.8f,
                imageView.getWidth()/2,
                imageView.getHeight()/2
        );
        selectAnimation.setFillAfter(true);
        selectAnimation.setFillEnabled(true);
        selectAnimation.setDuration(100);

        //Deselect animation
        ScaleAnimation deselectAnimation = new ScaleAnimation(
                0.8f, 1f,
                0.8f, 1f,
                imageView.getWidth()/2,
                imageView.getHeight()/2
        );
        deselectAnimation.setFillAfter(true);
        deselectAnimation.setFillEnabled(true);
        deselectAnimation.setDuration(100);

        if(state){

            imageView.startAnimation(selectAnimation);
            selectAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation arg0) {
                    tickImage.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}
            });


        }else{
            imageView.startAnimation(deselectAnimation);
            tickImage.setVisibility(View.INVISIBLE);
        }
    }

    class SampleGalleryViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.image_view)
        ImageView mImageView;

        SampleGalleryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
