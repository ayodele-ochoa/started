package com.ayodeleochoa.ayophotos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.ArrayList;

public abstract class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<RecyclerList> galleryList;
    private Context context;

    public MyAdapter(Context context, ArrayList<RecyclerList> galleryList) {
        this.galleryList = galleryList;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int i)
    {
        viewHolder.title.setText(galleryList.get(i).getAlbumTitle());
        viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
      //  viewHolder.img.setImageResource((galleryList.get(i).getImage_ID()));
        GlideUrl glideLink = new GlideUrl(galleryList.get(i).getThumbnailURL(), new LazyHeaders.Builder()
                .addHeader("User-Agent", "your-user-agent")
                .build());

        Glide.with(context)
                .load(glideLink)
                .placeholder(R.drawable.ic_launcher_background)
                .into(viewHolder.img);

        viewHolder.img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Add code to add navigate to photo gallery page
                Toast.makeText(context,"Image", Toast.LENGTH_SHORT).show();
            }
        });
    }

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
