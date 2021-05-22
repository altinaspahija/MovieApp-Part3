package gr.athtech.coolmovies.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gr.athtech.coolmovies.Activity.DetailsFavActivity;
import gr.athtech.coolmovies.Adapter.FavMovieAdapter;
import gr.athtech.coolmovies.Adapter.MovieAdapter;
import gr.athtech.coolmovies.Model.MovieData;
import gr.athtech.coolmovies.R;
import gr.athtech.coolmovies.SharedPreference;


public class FragmentFav extends Fragment{


    public static final String ARG_ITEM_ID = "favorite_list";

    private String JSON_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=c1c68db8f902d5b4cd68ba02816b02ef&page=1";
    public static final String IMAGE_URI = "https://image.tmdb.org/t/p/w500";

    Context context;
    ListView favoriteList;
    private List<MovieData> favorites;
    SharedPreference sharedPreference;
    Activity activity;
    MovieAdapter movieAdapter;
    RequestQueue requestQueue;
    JsonArrayRequest request;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favorites = new ArrayList<>();
        activity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fav_movies, container, false);
        LinearLayout view_container = v.findViewById(R.id.view_container_fav);
        // Get favorite items from SharedPreferences
        sharedPreference = new SharedPreference();
        favorites = sharedPreference.getFavorites(activity);

            favoriteList = (ListView) v.findViewById(R.id.favourite_movies);

            if (favorites != null) {
                movieAdapter = new MovieAdapter(activity, favorites);
                favoriteList.setAdapter(movieAdapter);

                favoriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        MovieData movieData = (MovieData) parent.getItemAtPosition(position);
                        Intent i = new Intent(activity, DetailsFavActivity.class);
                        i.putExtra("id", movieData.getId());
                        i.putExtra("movie_name", movieData.getTitle());
                        startActivity(i);
                    }
                });

        }
        return v;
    }



    public void onResume() {
    super.onResume();

    favorites = sharedPreference.getFavorites(activity);

        if (favorites != null) {
            movieAdapter = new MovieAdapter(activity, favorites);
            favoriteList.setAdapter(movieAdapter);

            favoriteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MovieData movieData = (MovieData) parent.getItemAtPosition(position);
                    Intent i = new Intent(activity, DetailsFavActivity.class);
                    i.putExtra("id", movieData.getId());
                    i.putExtra("movie_name", movieData.getTitle());
                    startActivity(i);
                }
            });
        }
    }
}










