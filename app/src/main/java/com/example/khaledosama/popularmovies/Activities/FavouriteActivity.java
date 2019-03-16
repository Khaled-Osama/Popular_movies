package com.example.khaledosama.popularmovies.Activities;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.khaledosama.popularmovies.Adapters.FavouriteCursorAdapter;
import com.example.khaledosama.popularmovies.Adapters.MoviesAdapter;
import com.example.khaledosama.popularmovies.Models.MovieViewModel;
import com.example.khaledosama.popularmovies.R;
import com.example.khaledosama.popularmovies.data.FavouriteMovies.FavouriteMovie;
import com.example.khaledosama.popularmovies.data.MovieRepository;

import java.util.List;

public class FavouriteActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MovieViewModel viewModel;
    FavouriteCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        recyclerView = findViewById(R.id.fav_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.setRepositry(new MovieRepository(this));

    }
    @Override
    public void onResume(){
        super.onResume();
        viewModel.getFavouriteMovies().observe(this, new Observer<Cursor>() {
            @Override
            public void onChanged(@Nullable Cursor cursor) {
                adapter = new FavouriteCursorAdapter(getApplicationContext(), cursor);
                recyclerView.setAdapter(adapter);
            }
        });
    }

}