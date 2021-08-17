package com.ayodeleochoa.ayophotos;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import Models.SliderItem;

public class ImageSliderFragment extends Fragment {

    // Global variables
    SliderView sliderView;
    private ImageSliderAdapter adapter;
    int position = 0;
    // Initialize arraylists to be filled with items from chosen album
    ArrayList<String> descriptions = new ArrayList<String>();
    ArrayList<String> urls = new ArrayList<String>();
    ArrayList<JsonPhotos> selectedAlbum = new ArrayList<JsonPhotos>();


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    )
    {

        View rootView = inflater.inflate(R.layout.fragment_image_slider, container, false);

        // Button to close down fragment
        ImageButton returnButton = (ImageButton) rootView.findViewById(R.id.btnReturn);
        returnButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                getFragmentManager().beginTransaction().remove(ImageSliderFragment.this).commitAllowingStateLoss();
            }
        });


        sliderView = (SliderView) rootView.findViewById(R.id.imageSlider);

        // Get album chosen from activity
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            position = bundle.getInt("position");
        }

        // Get Json data downloaded from api
        JsonPhotos[] posts = ((MainActivity)getActivity()).posts;

        if (posts.length > 0)
        {
            // Create an array of JsonPhotos that match album ID
            for(int i = 0; i < posts.length; i++)
            {
                if (posts[i].getAlbumId() == (position + 1))
                {
                    selectedAlbum.add(posts[i]);
                }
            }
        }


        // Instantiate sliderview
        adapter = new ImageSliderAdapter(getActivity());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINDEPTHTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.MAGENTA);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener()
        {
            @Override
            public void onIndicatorClicked(int position)
            {

            }
        });

        AddItems();

        return rootView;

    }

    // Add all photos and descriptions into SliderItems
    public void AddItems()
    {
        for(int i = 0; i< selectedAlbum.size(); i++)
        {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription(selectedAlbum.get(i).title);
            sliderItem.setImageUrl(selectedAlbum.get(i).url);
            adapter.addItem(sliderItem);
        }

        int itemCount = adapter.getCount();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Log.i("OnView ", "created");
    }


}