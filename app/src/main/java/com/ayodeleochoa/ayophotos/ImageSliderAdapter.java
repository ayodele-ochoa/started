package com.ayodeleochoa.ayophotos;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import Models.SliderItem;

public class ImageSliderAdapter extends
        SliderViewAdapter<ImageSliderAdapter.SliderAdapterVH>
{

    private Context context;
    private List<SliderItem> mSliderItems = new ArrayList<>();

    // Constructor
    public ImageSliderAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<SliderItem> sliderItems)
    {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position)
    {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    // Add items to SliderItem list
    public void addItem(SliderItem sliderItem)
    {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }


    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent)
    {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout, null);
        return new SliderAdapterVH(inflate);
    }

    // Add text descriptions and URL's to ImageSliderFragment
    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        SliderItem sliderItem = mSliderItems.get(position);

        viewHolder.textViewDescription.setText(sliderItem.getDescription());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.BLACK);

        GlideUrl glideLink = new GlideUrl(sliderItem.getImageUrl(), new LazyHeaders.Builder()
                .addHeader("User-Agent", "your-user-agent")
                .build());

        Glide.with(viewHolder.itemView)
                .load(glideLink)
                .fitCenter()
                .placeholder(R.drawable.default_photo)
                .override(600, 600)
                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();

            }
        });
    }

    // Return total count of galleryList items
    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }


    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }

}
