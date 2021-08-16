package com.ayodeleochoa.ayophotos;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadPhotosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_photos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i("DPA", " called");

        /*final Button downloadButton = findViewById(R.id.btnDownload);
        downloadButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.i("Download", " clicked");
                new DownloadPhotosActivity.HttpPhotosRequest().execute();
            }
        });*/
    }

/*    private static class HttpPhotosRequest extends AsyncTask<Void, Void, Void>
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
                //   Log.d("line: ", line);

                // JSONObject jObject = new JSONObject(line);
                //   JSONArray jArray = new JSONArray(line);
                //  String aJsonString = jObject.getString("url");
                //   Log.i("url%%% = ", String.valueOf(jArray.get(0)));

                // Deserialize downloaded json with JsonPhotos class

                sb = new StringBuilder();


                while ((line = rd.readLine()) != null)
                {
                    // Log.i("data", line);
                    sb.append(line + "\n");
                }
                Log.i("sb ", sb.toString());
                String downloadedData = sb.toString();
                Gson gson = new Gson();
                String jsonOutput = downloadedData;
                JsonPhotos[] posts = gson.fromJson(jsonOutput, JsonPhotos[].class);
                Log.v("JsonLength = ", String.valueOf(posts.length));
                Log.i("First post: ", posts[0].getTitle());

            } catch (Exception e) {
                e.printStackTrace();
            } finally
            {
                if (urlConnection != null)
                {
                    urlConnection.disconnect();
                }

            }

            return null;
        }
    }*/
}