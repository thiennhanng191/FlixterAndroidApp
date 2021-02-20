package com.example.flixter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    String id;
    String releasedYear;
    String voteAverage;
    String voteCount;

    public Movie(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        id = "" + jsonObject.getInt("id");
        releasedYear = jsonObject.getString("release_date").substring(0,4);
        voteAverage = jsonObject.getString("vote_average");
        voteCount = jsonObject.getString("vote_count");
    }

    /*
    Method to construct a list of Movie from a JSONArray of results
     */
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i=0; i<movieJsonArray.length(); i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath); // 342 is the fixed size
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath); // 342 is the fixed size
    }


    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getId() { return id; }

    public String getReleasedYear() {
        return releasedYear;
    }

    public String getVoteAverage() { return voteAverage; }

    public String getVoteCount() { return voteCount; }


}
