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
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MovieDetailsActivty extends YouTubeBaseActivity {
    public static String movieId;
    public static String MOVIE_DETAILS_URL;
    public static String VIDEOS_URL;
    public static final String TAG = "MovieDetailsActivity";
    private static final String YOUTUBE_API_KEY = "" + BuildConfig.YOUTUBE_API_KEY;
    private static Movie movie;
    YouTubePlayerView youtubePlayerView;

    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);
        youtubePlayerView = (YouTubePlayerView) findViewById(R.id.player);

        RecyclerView rvMoviesDetails = findViewById(R.id.rvMoviesDetails);
        movies = new ArrayList<>();

        movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        String movieId = movie.getId();
        MOVIE_DETAILS_URL = "https://api.themoviedb.org/3/movie/"+ movieId + "?api_key=" + BuildConfig.TMDB_API_KEY;
        VIDEOS_URL = "https://api.themoviedb.org/3/movie/"+ movieId + "/videos?api_key=" + BuildConfig.TMDB_API_KEY;

        // Create the adapter
        MovieDetailsAdapter movieDetailsAdapter = new MovieDetailsAdapter(this, movies);

        // Set the adapter on the recycler view
        rvMoviesDetails.setAdapter(movieDetailsAdapter);

        // Set a Layout Manager on the recycler view
        rvMoviesDetails.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(VIDEOS_URL,new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length() == 0) {
                        return;
                    } else {
                        String youtubeKey = results.getJSONObject(0).getString("key");
                        Log.d("DetailActivity", youtubeKey);

                        initializeYoutube(youtubeKey);

                    }
                } catch (JSONException e) {
                    Log.e("DetailActivity", "Failed to parse JSON", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });

        movies.add(movie);
        movieDetailsAdapter.notifyDataSetChanged();

        /*
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        */
    }

    private void initializeYoutube(final String youtubeKey) {
        youtubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("DetailActivity", "onInitializationSuccess");

                if (Float.parseFloat(movie.getVoteAverage()) >= 5) {
                    youTubePlayer.loadVideo(youtubeKey);
                    youTubePlayer.play();
                } else {
                    youTubePlayer.cueVideo(youtubeKey);

                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("DetailActivity", "onInitializationFailure");

            }
        });
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