package com.example.omdb.api;

import com.example.omdb.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("/")
    Call<List<Movie>> getMovies();

}
