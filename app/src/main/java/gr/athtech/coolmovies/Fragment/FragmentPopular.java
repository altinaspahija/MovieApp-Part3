package gr.athtech.coolmovies.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gr.athtech.coolmovies.Activity.DetailsActivity;
import gr.athtech.coolmovies.Adapter.MovieAdapter;
import gr.athtech.coolmovies.Model.MovieData;
import gr.athtech.coolmovies.R;
import gr.athtech.coolmovies.SharedPreference;


public class FragmentPopular extends Fragment implements
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    public FragmentPopular() {

    }
    private String JSON_URL_ALL = "https://api.themoviedb.org/3/movie/popular?api_key=40219592510d78632bfd917982326cc7";
    public static final String IMAGE_URI = "https://image.tmdb.org/t/p/w500"; // Image uri
    public static final String ARG_ITEM_ID = "product_list";

    Activity activity;
    ListView movieView;
    List<MovieData> listMovieData;
    MovieAdapter movieAdapter;
    SharedPreference sharedPreference;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        sharedPreference = new SharedPreference();
        listMovieData = new ArrayList<>();
        jsonRequestPopularMovies();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.popular_movies, container, false);
        findViewsById(v);
        movieAdapter = new MovieAdapter(activity, listMovieData);
        movieView.setAdapter(movieAdapter);
        movieView.setOnItemClickListener(this);
        movieView.setOnItemLongClickListener(this);
        Toast.makeText(activity,
                "Long click any movie to make it as a favorite movie",
                Toast.LENGTH_SHORT).show();
        return v;

    }
    private void findViewsById(View view) {
        movieView = (ListView) view.findViewById(R.id.popular_movies);
    }

    private void jsonRequestPopularMovies() {
        // Retrieve all popular movies
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URL_ALL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            // Now looping through all the elements of the json array
                            for (int i = 0; i < jsonArray.length(); i++) {
                                // Getting the json object of the particular index inside the array
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                MovieData movieData = new MovieData();
                                movieData.setTitle(jsonObject.getString("original_title"));
                                movieData.setReleaseDate(jsonObject.getString("release_date"));
                                movieData.setOverview(jsonObject.getString("overview"));
                                movieData.setPosterPath(IMAGE_URI + jsonObject.getString("poster_path"));
                                movieData.setVoteAverage(jsonObject.getDouble("vote_average"));
                                movieData.setId(jsonObject.getInt("id"));
                                // Adding the json data to list
                                listMovieData.add(movieData);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        queue.add(jsonObjectRequest);


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        MovieData movieData = (MovieData) parent.getItemAtPosition(position);

        Intent i = new Intent(getActivity(), DetailsActivity.class);
        i.putExtra("id",movieData.getId());
        i.putExtra("movie_name",movieData.getTitle());
        startActivity(i);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View view,
                                   int position, long arg3) {
        ImageView button = (ImageView) view.findViewById(R.id.movie_imageView_heart);
        sharedPreference = new SharedPreference();
        String tag = button.getTag().toString();
        if (tag.equalsIgnoreCase("grey")) {
            sharedPreference.addFavorite(activity, listMovieData.get(position));
            Toast.makeText(activity,
                    "Added to Favorites - Click the movie to see this favorite movies's details",
                    Toast.LENGTH_SHORT).show();
            button.setTag("red");
            button.setImageResource(R.drawable.heart);

        }
//        else {
//            sharedPreference.removeFavorite(activity, listMovieData.get(position));
//            button.setTag("grey");
//            button.setImageResource(R.drawable.notfullheart);
//            Toast.makeText(activity,
//                    "Removed from Favorites",
//                    Toast.LENGTH_SHORT).show();
//        }

        return true;
    }

}





