package com.example.khaledosama.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.khaledosama.popularmovies.R;
import com.example.khaledosama.popularmovies.data.FavouriteMovies.FavouriteMovie;
import com.example.khaledosama.popularmovies.data.MainMovies.Movie;
import com.example.khaledosama.popularmovies.data.MainMovies.MovieContract;
import com.squareup.picasso.Picasso;

public class FavouriteCursorAdapter extends RecyclerView.Adapter<FavouriteCursorAdapter.ViewHolder> {

    Context mContext;
    Cursor mCursor;

    public FavouriteCursorAdapter(Context context, Cursor cursor){
        this.mContext =context;
        this.mCursor = cursor;
    }

    @NonNull
    @Override
    public FavouriteCursorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.movie_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteCursorAdapter.ViewHolder viewHolder, int i) {
        int movieTitleIndx = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE);
        int movieRatingIndx = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_AVG_RATING);
        int movieDescIndx = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW);
        int moviePosterIndx = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_IMAGE);

        mCursor.moveToPosition(i);

        final String moviePoster =mCursor.getString(moviePosterIndx);
        final String movieTitle = mCursor.getString(movieTitleIndx);
        final String movieRating = mCursor.getString(movieRatingIndx);
        final String movieOverview = mCursor.getString(movieDescIndx);


        Picasso.with(mContext).load( Movie.IMAGE_BASE_PATH + moviePoster).into(viewHolder.moviePoster);
        viewHolder.movieTitle.setText(movieTitle);
        viewHolder.movieRating.setText(movieRating);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                Movie movie = new Movie(mCursor.getLong(mCursor.getColumnIndex(MovieContract.MovieEntry._ID)),
                        movieTitle, moviePoster,
                        movieOverview, movieRating);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("movie", movie);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor moviesCursor) {
        mCursor = moviesCursor;
        if(mCursor!=null){
            this.notifyDataSetChanged();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView moviePoster;
        TextView movieTitle, movieRating;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieRating = itemView.findViewById(R.id.movie_rating);
            moviePoster = itemView.findViewById(R.id.movie_poster);
            cardView = itemView.findViewById(R.id.movie_card);
        }
    }
}