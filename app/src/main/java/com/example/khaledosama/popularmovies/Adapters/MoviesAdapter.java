package com.example.khaledosama.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khaledosama.popularmovies.Activities.DetailsActivity;
import com.example.khaledosama.popularmovies.data.MainMovies.Movie;
import com.example.khaledosama.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movie>movies;

    public MoviesAdapter(Context context, ArrayList<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View retView = LayoutInflater.from(context).inflate(R.layout.movie_item, viewGroup, false);
        return new ViewHolder(retView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Movie movie= movies.get(i);
        Picasso.with(context).load(movie.getFullPathImage()).into(viewHolder.moviePoster);
        viewHolder.movieTitle.setText(movie.getTitle());
        viewHolder.movieRating.setText(movie.getRating());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("movie", movie);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView moviePoster;
        TextView movieTitle, movieRating;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.movie_poster);
            movieRating = itemView.findViewById(R.id.movie_rating);
            movieTitle = itemView.findViewById(R.id.movie_title);
            cardView = itemView.findViewById(R.id.movie_card);
        }
    }
}