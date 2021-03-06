package com.example.flixter.adapters;

import android.content.Context;
import android.content.res.Configuration;
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

    private OnClickListener mOnClickListener;
    public MovieAdapter(Context context, List<Movie> movies, OnClickListener onClickListener) {
        this.context = context;
        this.movies = movies;
        this.mOnClickListener = onClickListener;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView, mOnClickListener);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvMovieTitle;
        TextView tvMovieOverview;
        TextView tvMovieYear;
        TextView tvMovieRating;
        ImageView ivMoviePoster;

        OnClickListener onClickListener;

        public ViewHolder(@NonNull View itemView, OnClickListener onClickListener) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            ivMoviePoster = itemView.findViewById(R.id.ivMoviePoster);
            tvMovieYear = itemView.findViewById(R.id.tvMovieYear);
            tvMovieRating = itemView.findViewById(R.id.tvMovieRating);

            this.onClickListener = onClickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            tvMovieTitle.setText(movie.getTitle());
            tvMovieYear.setText(movie.getReleasedYear());
            tvMovieRating.setText("Average rating " + movie.getVoteAverage() + " from " + movie.getVoteCount() + " users");

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

        @Override
        public void onClick(View v) {
            onClickListener.onItemClicked(getAdapterPosition());

        }
    }

    public interface OnClickListener {
        void onItemClicked(int position);
    }
}