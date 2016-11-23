package com.davidecirillo.multichoicesample.sampleImageGallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.davidecirillo.multichoicerecyclerview.MultiChoiceAdapter;
import com.davidecirillo.multichoicesample.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by davidecirillo on 27/06/2016.
 */

class SampleGalleryAdapter extends MultiChoiceAdapter<SampleGalleryAdapter.SampleGalleryViewHolder> {

    private final Context mContext;
    private ArrayList<Integer> imageList;

    private Integer[] images = new Integer[]{
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5
    };

    SampleGalleryAdapter(Context mContext) {
        this.mContext = mContext;

        setUpImageList();
    }

    private void setUpImageList() {
        imageList = new ArrayList<>();
        Random r = new Random();

        for (int i = 0; i < getItemCount(); i++) {
            imageList.add(images[r.nextInt(5)]);
        }
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
                .load(imageList.get(position))
                .error(R.drawable.placeholder)
                .resize(200, 200)
                .centerCrop()
                .into(holder.mImageView);
    }


    @Override
    public void setActive(@NonNull View view, boolean state) {

        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        final ImageView tickImage = (ImageView) view.findViewById(R.id.tick_image);

        //Select animation
        ScaleAnimation selectAnimation = new ScaleAnimation(
                1f, 0.8f,
                1f, 0.8f,
                imageView.getWidth() / 2,
                imageView.getHeight() / 2
        );
        selectAnimation.setFillAfter(true);
        selectAnimation.setFillEnabled(true);
        selectAnimation.setDuration(100);

        //Deselect animation
        ScaleAnimation deselectAnimation = new ScaleAnimation(
                0.8f, 1f,
                0.8f, 1f,
                imageView.getWidth() / 2,
                imageView.getHeight() / 2
        );
        deselectAnimation.setFillAfter(true);
        deselectAnimation.setFillEnabled(true);
        deselectAnimation.setDuration(100);

        if (state) {

            imageView.startAnimation(selectAnimation);
            selectAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    tickImage.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });


        } else {
            imageView.startAnimation(deselectAnimation);
            tickImage.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected View.OnClickListener defaultItemViewClickListener(SampleGalleryViewHolder holder, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Click on item " + position, Toast.LENGTH_SHORT).show();
            }
        };
    }

    class SampleGalleryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_view)
        ImageView mImageView;

        SampleGalleryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
