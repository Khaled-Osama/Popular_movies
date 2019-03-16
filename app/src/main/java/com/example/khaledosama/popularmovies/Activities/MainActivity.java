package com.example.khaledosama.popularmovies.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.example.khaledosama.popularmovies.Adapters.MoviesAdapter;
import com.example.khaledosama.popularmovies.Models.MovieViewModel;
import com.example.khaledosama.popularmovies.data.MainMovies.Movie;
import com.example.khaledosama.popularmovies.R;
import com.example.khaledosama.popularmovies.data.MovieRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView ;
    MoviesAdapter moviesAdapter;
    RequestQueue requestQueue;
    String POPULAR_MOVIES = "popular";
    String TOP_RATED_MOVIES = "top_rated";
    MovieRepository movieRepository;

    MovieViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.movies_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieRepository = new MovieRepository(this);
        viewModel.setInput(POPULAR_MOVIES, movieRepository);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                moviesAdapter = new MoviesAdapter(getApplicationContext(), (ArrayList<Movie>)movies);
                recyclerView.setAdapter(moviesAdapter);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.popular_menu_item:
                viewModel.setInput(POPULAR_MOVIES, movieRepository);
                break;
            case R.id.top_rated_menu_item:
                viewModel.setInput(TOP_RATED_MOVIES, movieRepository);

                break;
            case R.id.favourite_menu_item:
                Intent intent = new Intent(this, FavouriteActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}