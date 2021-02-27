package com.example.flixter.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieDetailsAdapter extends RecyclerView.Adapter<MovieDetailsAdapter.ViewHolder> {
    Context context;
    List<Movie> movies;

    public MovieDetailsAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieDetailsView = LayoutInflater.from(context).inflate(R.layout.movie_details, parent, false);
        return new ViewHolder(movieDetailsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the movie at the specified position
        Movie movie = movies.get(position);

        // Bind the movie data into the ViewHolder
        holder.bind(movie);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieTitle;
        TextView tvMovieOverview;
        TextView tvMovieReleasedYear;
        TextView tvMovieRating;
        ImageView ivMoviePoster;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvMovieReleasedYear = itemView.findViewById(R.id.tvMovieDetailsYear);
            tvMovieOverview = itemView.findViewById(R.id.tvMovieDetailsOverview);
            tvMovieRating = itemView.findViewById(R.id.tvMovieDetailsRating);
            ivMoviePoster = itemView.findViewById(R.id.ivMoviePoster);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }

        public void bind(Movie movie) {
            tvMovieTitle.setText(movie.getTitle());
            tvMovieOverview.setText(movie.getOverview());
            tvMovieReleasedYear.setText(movie.getReleasedYear());
            tvMovieRating.setText("Average rating " + movie.getVoteAverage() + " from " + movie.getVoteCount() + " users");
            ratingBar.setNumStars(5);
            ratingBar.setRating(Float.parseFloat(movie.getVoteAverage())/2);

            String imageUrl;
            int placeHolder;
            // if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
                placeHolder = R.drawable.placeholder_horizontal;
            } else {  // else in vertical
                imageUrl = movie.getPosterPath();
                placeHolder = R.drawable.placeholder_verticle;
            }
            //displaying image with Glide
            // Glide.with(context).load(imageUrl).into(ivMoviePoster);

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(placeHolder)
                    .error(placeHolder)
                    .into(ivMoviePoster);
        }
    }
}

