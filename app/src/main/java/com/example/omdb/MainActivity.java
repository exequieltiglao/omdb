package com.example.omdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.omdb.api.ApiInterface;
import com.example.omdb.model.Movie;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String BASE_URL = "http://www.omdbapi.com/?apikey=8eeefbee&";

    private TextView tv_result;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_result = findViewById(R.id.tv_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);

        getMovies();

    }

    public void  getMovies() {
        Call<List<Movie>> call = apiInterface.getMovies();

        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {

                /* checks if response is not successful */
                if (!response.isSuccessful()) {
                    tv_result.setText("Response: " + response.code());
                    Log.d(TAG, "onResponse: " + response.code());
                }

                List<Movie> movies = response.body();

                for (Movie movie : movies) {
                    String content = "";
                    content += "Title: " + movie.getTitle();
                    content += "Year: " + movie.getYear();
                    content += "Rated: " + movie.getRated();
                    content += "Released: " + movie.getReleased();
                    content += "Runtime: " + movie.getRuntime();
                    content += "Genre: " + movie.getGenre();
                    content += "imdbID: " + movie.getImdbId();

                    tv_result.append(content);

                }

            }

            /**
             *  private String title;
             *     private Integer year;
             *     private String rated;
             *     private String released;
             *     private String runtime;
             *     private String genre;
             *     private String director;
             *     private String writer;
             *     private String actors;
             *     private String plot;
             *     private String language;
             *     private String awards;
             *     private String imdbID;
             *
             */

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.d(TAG, "failed.... " + t.getMessage());
            }
        });


    }



}
