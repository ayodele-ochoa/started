package com.ayodeleochoa.ayophotos;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.os.AsyncTask;
import java.io.IOException;
import com.google.gson.Gson;

import android.view.ViewGroup;
import android.widget.Button;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import Models.CheckNetwork;
import Models.RecyclerList;


public class MainActivity extends AppCompatActivity
{

    Bitmap bitmap;
    ImageView testImageView;

    // Initialize arraylists to be filled with albumID's and thumbnail URL's of the first photo for each album
    ArrayList<String> album_titles = new ArrayList<String>();
    ArrayList<String> thumbnail_images = new ArrayList<String>();

    JsonPhotos[] posts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button to trigger download of data from api
        final Button downloadButton = findViewById(R.id.btnDownload);
        downloadButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Verify network connection before attempting api request
                if (CheckNetwork.isConnected(MainActivity.this))
                {
                    new HttpPhotosRequest().execute();
                }
                else
                {
                    Toast toast = Toast.makeText(MainActivity.this, "Please check internet connection and try again", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        downloadButton.performClick();


    }

    // Instantiate and modify settings for recyclerview
    public void RunRecyclerView()
    {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.albumsRV);
        recyclerView.setBackgroundResource(0);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),4);


        ViewGroup.MarginLayoutParams marginLayoutParams= (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
        marginLayoutParams.setMargins(5, 5, 5, 5);
        recyclerView.setLayoutParams(marginLayoutParams);

        recyclerView.setLayoutManager(layoutManager);
        ArrayList<RecyclerList> createLists = prepareData();
        AlbumsAdapter adapter = new AlbumsAdapter(getApplicationContext(), createLists) {
        };
        recyclerView.setAdapter(adapter);

        Toast toast = Toast.makeText(MainActivity.this, "Photo Albums Updated", Toast.LENGTH_LONG);
        toast.show();
    }

    private ArrayList<RecyclerList> prepareData()
    {

        ArrayList<RecyclerList> theimage = new ArrayList<>();
        for(int i = 0; i< album_titles.size(); i++){
            RecyclerList createList = new RecyclerList();
            createList.setAlbumTitle(album_titles.get(i));
            createList.setImageUrl(thumbnail_images.get(i));
            theimage.add(createList);

        }
        return theimage;
    }

    //  Asynchronous HttpURLConnection request
    private class HttpPhotosRequest extends AsyncTask<Void, Void, Void>
    {
        StringBuilder sb = null;

        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            Log.i("HTTP - ", "called");
            try {
                URL url = new URL("https://jsonplaceholder.typicode.com/photos");
                urlConnection = (HttpURLConnection) url.openConnection();

                int code = urlConnection.getResponseCode();
                if (code !=  200) {
                    throw new IOException("Invalid response from server: " + code);
                }

                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                String line = "";

                // Deserialize downloaded json with JsonPhotos class
                sb = new StringBuilder();


                while ((line = rd.readLine()) != null)
                {
                    sb.append(line + "\n");
                }

                String downloadedData = sb.toString();
                Gson gson = new Gson();
                String jsonOutput = downloadedData;
                posts = gson.fromJson(jsonOutput, JsonPhotos[].class);

                for (int i = 0; i < posts.length; i++)
                {
                    if (!album_titles.contains("Album " + posts[i].getAlbumId()))
                    {
                        album_titles.add("Album " + posts[i].getAlbumId());
                        thumbnail_images.add(posts[i].thumbnailUrl);
                    }

                }
                Log.i("album_titles count = ", String.valueOf(album_titles.size()));
                Log.i("thumbnail_images 1 = ", thumbnail_images.get(1));

            } catch (Exception e) {
                e.printStackTrace();
            } finally
            {
               if (posts.length > 0)
               {
                   // Update RecyclerView on main thread
                   new Handler(Looper.getMainLooper()).post(new Runnable(){
                       @Override
                       public void run() {
                           RunRecyclerView();
                       }
                   });
               }

                if (urlConnection != null)
                {
                    urlConnection.disconnect();
                }
            }

            return null;
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}