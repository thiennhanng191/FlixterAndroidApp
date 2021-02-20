package com.example.flixter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.adapters.MovieAdapter;
import com.example.flixter.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnClickListener {
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + BuildConfig.TMDB_API_KEY;
    public static final String TAG = "MainActivity";
    public static final String MOVIE_ID = "movie_id";
    public static final String MOVIE_TITLE_ID = "movie_title_id";

    // private static int movieId = 0;
    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.recyclerViewMovies);
        movies = new ArrayList<>();


        // Create the adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies, this); // first this refers to context, latter this refers to MovieAdapter.OnClickListener

        // Set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);

        // Set a Layout Manager on the recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvMovies.addItemDecoration(itemDecoration);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;


                try {
                    JSONArray results = jsonObject.getJSONArray("results"); // results field is of type json array
                    Log.i(TAG, "Results: " + results.toString());

                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();

                    Log.e(TAG, "Movies: " + movies.size());

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
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(MainActivity.this, MovieDetailsActivty.class);
        intent.putExtra("MOVIE_ID", movies.get(position).getId());
        startActivity(intent);

    }
}