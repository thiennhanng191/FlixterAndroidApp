package com.example.flixter.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixter.R;
import com.example.flixter.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);

        // Get the movie at the specified position
        Movie movie = movies.get(position);

        // Bind the movie data into the ViewHolder
        holder.bind(movie);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMovieTitle;
        TextView tvMovieOverview;
        ImageView ivMoviePoster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.textViewMovieTitle);
            tvMovieOverview = itemView.findViewById(R.id.textViewMovieOverview);
            ivMoviePoster = itemView.findViewById(R.id.itemViewMoviePoster);
        }

        public void bind(Movie movie) {
            tvMovieTitle.setText(movie.getTitle());
            tvMovieOverview.setText(movie.getOverview());

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
            Glide.with(context).load(imageUrl).into(ivMoviePoster);

            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(placeHolder)
                    .error(placeHolder)
                    .into(ivMoviePoster);
        }
    }
}
