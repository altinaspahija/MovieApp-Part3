package gr.athtech.coolmovies.Activity;

import android.content.Context;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gr.athtech.coolmovies.Adapter.SearchAdapter;
import gr.athtech.coolmovies.Model.MovieData;
import gr.athtech.coolmovies.R;

public class SearchActivity extends AppCompatActivity {

    ImageView movieImage;
    TextView textViewName;
    TextView textViewDate;
    TextView textViewMRating;
    EditText searchText;
    Button btnSearch;
    ImageView star;
    SearchAdapter searchAdapter;
    RecyclerView recyclerView;
    private ProgressBar loadingPB;
    private NestedScrollView nestedSV;
    List<MovieData> movieDataList = new ArrayList<>();

    // creating a variable for our page and limit and defining them
    int page = 1, limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_movies);
        movieImage = findViewById(R.id.movie_imageView);
        textViewName = findViewById(R.id.textName);
        textViewDate = findViewById(R.id.textDate);
        textViewMRating = findViewById(R.id.ratingText);
        searchText = findViewById(R.id.edit_search);
        btnSearch = findViewById(R.id.btnSearch);
        recyclerView = findViewById(R.id.recyclerViewSearch);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        searchAdapter = new SearchAdapter(movieDataList);
        recyclerView.setAdapter(searchAdapter);
        loadingPB = findViewById(R.id.idPBLoading);
        nestedSV = findViewById(R.id.idNestedSV);
        btnSearch.setOnClickListener(v ->
                // calling a method to load our api
                searchMovies(page, limit));
        // adding on scroll change listener method for our nested scroll view.
        nestedSV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // on scroll change we are checking when users scroll as bottom.
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    // in this method we are incrementing page number,
                    // making progress bar visible and calling get data method.
                    page++;
                    loadingPB.setVisibility(View.VISIBLE);
                    searchMovies(page, limit);
                }
            }
        });
    }

    private void searchMovies(int page, int limit) {
        closeKeyboard();
        String name = searchText.getText().toString();
        if (name.equals("")) {
            return;
        }
        if (page > limit) {
            // checking if the page number is greater than limit.
            // hiding our progress bar.
            loadingPB.setVisibility(View.GONE);
            return;
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        // Retrieve movies of English language and search by query given in searchbar
        String URL = "https://api.themoviedb.org/3/search/movie?api_key=40219592510d78632bfd917982326cc7&query=" + name + "&page=" + page;
        String IMAGE_URI = "https://image.tmdb.org/t/p/w500"; // Image uri
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject converted_response = new JSONObject(response);
                            JSONArray items = converted_response.getJSONArray("results");
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject jsonObject = items.getJSONObject(i);
                                MovieData movieData = new MovieData();
                                movieData.setTitle(jsonObject.getString("title"));
                                movieData.setReleaseDate(jsonObject.getString("release_date"));
                                movieData.setOverview(jsonObject.getString("overview"));
                                movieData.setPosterPath(IMAGE_URI + jsonObject.getString("poster_path"));
                                movieData.setVoteAverage(jsonObject.getDouble("vote_average"));
                                movieDataList.add(movieData);
                            }
                            searchAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Log.e("ResponseJSONException", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, "Fail to get data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
