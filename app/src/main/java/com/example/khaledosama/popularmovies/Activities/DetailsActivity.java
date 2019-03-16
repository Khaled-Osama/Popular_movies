package com.example.khaledosama.popularmovies.Activities;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khaledosama.popularmovies.data.FavouriteMovies.FavouriteMovieDAO;
import com.example.khaledosama.popularmovies.data.MainMovies.Movie;
import com.example.khaledosama.popularmovies.R;
import com.example.khaledosama.popularmovies.data.MainMovies.MovieContract;
import com.example.khaledosama.popularmovies.data.MovieDatabase;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    TextView movieTitle, movieDesc;
    ImageView moviePoster;
    FloatingActionButton addBtn;
    boolean FAB_CLICKED = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        final Movie movie = (Movie)getIntent().getSerializableExtra("movie");
        movieTitle = findViewById(R.id.details_movie_title);
        movieDesc = findViewById(R.id.details_movie_description);
        moviePoster = findViewById(R.id.details_movie_poster);
        addBtn = findViewById(R.id.floatingActionButton);
        FavouriteMovieDAO favouriteMovieDAO= MovieDatabase.getInstance(this).favouriteMovieDAO();
        FAB_CLICKED = favouriteMovieDAO.favouriteMovie(movie.getId()).getCount()>0;
        Log.v("WWW", String.valueOf(FAB_CLICKED));

        if(FAB_CLICKED){
            addBtn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fab_remove));
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!FAB_CLICKED){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MovieContract.MovieEntry._ID, movie.getId());
                    contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
                    contentValues.put(MovieContract.MovieEntry.COLUMN_IMAGE, movie.getImage());
                    contentValues.put(MovieContract.MovieEntry.COLUMN_AVG_RATING, movie.getRating());
                    contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());

                    Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,contentValues);


                    Toast.makeText(getApplicationContext(),"Movie has been added to favourite list",Toast.LENGTH_SHORT).show();
                    FAB_CLICKED = true;
                    addBtn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fab_remove));
                }
                else{
                    Toast.makeText(getApplicationContext(),"Movie has been removed from favourite list",Toast.LENGTH_SHORT).show();
                    FAB_CLICKED = false;
                    addBtn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fab_add));
                    int rawsRemoved = getContentResolver().delete(ContentUris.withAppendedId(
                            MovieContract.MovieEntry.CONTENT_URI, movie.getId()),null,null);
                }
            }
        });

        movieTitle.setText(movie.getTitle());
        movieDesc.setText(movie.getOverview());
        Log.v("QQQ", movie.getImage());
        Picasso.with(this).load(movie.getFullPathImage()).into(moviePoster);

    }
}