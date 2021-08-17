package com.ayodeleochoa.ayophotos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.ArrayList;

import Models.RecyclerList;

public abstract class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder>
{
    private ArrayList<RecyclerList> galleryList;
    private Context context;

    // Constructor
    public AlbumsAdapter(Context context, ArrayList<RecyclerList> galleryList)
    {
        this.galleryList = galleryList;
        this.context = context;
    }

    @Override
    public AlbumsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    // Add album titles and thumbnail URL's to MainActivity
    @Override
    public void onBindViewHolder(AlbumsAdapter.ViewHolder viewHolder, int i)
    {
        viewHolder.title.setText(galleryList.get(i).getAlbumTitle());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
      //  viewHolder.img.setImageResource((galleryList.get(i).getImage_ID()));
        GlideUrl glideLink = new GlideUrl(galleryList.get(i).getThumbnailURL(), new LazyHeaders.Builder()
                .addHeader("User-Agent", "your-user-agent")
                .build());

        Glide.with(context)
                .load(glideLink)
                .placeholder(R.drawable.default_album)
                .into(viewHolder.img);

        viewHolder.img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                Bundle bundle = new Bundle();
                bundle.putInt("position", i);

                activity.getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragment_container_view, ImageSliderFragment.class, bundle)
                        .commit();
            }
        });
    }

    // Return total count of galleryList items
    @Override
    public int getItemCount()
    {
        return galleryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img;
        public ViewHolder(View view) {
            super(view);

            title = (TextView)view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }
}
