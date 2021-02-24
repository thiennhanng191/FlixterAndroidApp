package com.example.flixter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.adapters.MovieAdapter;
import com.example.flixter.adapters.MovieDetailsAdapter;
import com.example.flixter.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MovieDetailsActivty extends AppCompatActivity {
    public static String movieId;
    public static String MOVIE_DETAILS_URL;
    public static final String TAG = "MovieDetailsActivity";

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);
        RecyclerView rvMoviesDetails = findViewById(R.id.rvMoviesDetails);
        movies = new ArrayList<>();

        try {
            movieId = getIntent().getExtras().getString("MOVIE_ID");
            MOVIE_DETAILS_URL = "https://api.themoviedb.org/3/movie/"+ movieId + "?api_key=" + BuildConfig.TMDB_API_KEY;
        }
        catch (NullPointerException e){}

        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        // Set title to false AFTER toolbar has been set -- remove app's name from menu bar
        try
        {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        catch (NullPointerException e){}

        // Create the adapter
        MovieDetailsAdapter movieDetailsAdapter = new MovieDetailsAdapter(this, movies);

        // Set the adapter on the recycler view
        rvMoviesDetails.setAdapter(movieDetailsAdapter);

        // Set a Layout Manager on the recycler view
        rvMoviesDetails.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(MOVIE_DETAILS_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;

                JSONArray results = new JSONArray();
                results.put(jsonObject);

                try {
                    movies.addAll(Movie.fromJsonArray(results));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                movieDetailsAdapter.notifyDataSetChanged();


                try {
                    Log.i(TAG, "Results: " + jsonObject.getString("original_title"));

                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movie_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}