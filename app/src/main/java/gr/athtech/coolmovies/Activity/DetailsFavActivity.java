
package gr.athtech.coolmovies.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import gr.athtech.coolmovies.Adapter.FavMovieAdapter;
import gr.athtech.coolmovies.Fragment.FragmentFav;
import gr.athtech.coolmovies.Fragment.FragmentPopular;
import gr.athtech.coolmovies.R;

public class DetailsFavActivity extends AbstractActivity {

    TextView movie_name;
    ImageView movie_image;
    TextView movie_date;
    TextView movie_textrating;
    TextView movie_description;
    ScrollView scrollView;
    TextView movie_cast;
    Button btnShare;


    @Override
    int getLayout() {
        return R.layout.activity_details_fav;
    }


    @Override
    void initLayout() {


        movie_image = findViewById(R.id.movie_imageView_details_cover_fav);
        movie_name = findViewById(R.id.textName_details_fav);
        movie_date = findViewById(R.id.textDate_details_fav);
        movie_textrating = findViewById(R.id.ratingText_details_fav);
        movie_description = findViewById(R.id.description_details_fav);
        movie_cast = findViewById(R.id.movie_cast_fav);
        scrollView = findViewById(R.id.scrollView_details_fav);
        btnShare = findViewById(R.id.share);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getIntent().getExtras().getString("movie_name");
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share movie");
                String dataToShare = name;
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, dataToShare);
                startActivity(Intent.createChooser(shareIntent, "Share via"));

            }

        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchMovieDetails();
        searchMovieDetailsCast();
    }

    private void searchMovieDetails() {
        Integer id = getIntent().getExtras().getInt("id");
        RequestQueue queue = Volley.newRequestQueue(this);
        String JSON_URL = "https://api.themoviedb.org/3/movie/" + id + "?api_key=40219592510d78632bfd917982326cc7";
        String IMAGE_URI = "https://image.tmdb.org/t/p/w500";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String movie_name_data=response.get("original_title").toString();
                            movie_name.setText(movie_name_data);
                            String movie_date_data=response.get("release_date").toString();
                            movie_date.setText(movie_date_data);
                            String movie_description_date=response.get("overview").toString();
                            movie_description.setText(movie_description_date);
                            String movie_rating_data=response.get("vote_average").toString();
                            movie_textrating.setText(movie_rating_data);
                            String movie_image_path=response.get("poster_path").toString();
                            String movie_image_total=IMAGE_URI+movie_image_path;
                            Picasso.get().load(movie_image_total).into(movie_image);
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

    private void searchMovieDetailsCast() {
        Integer id = getIntent().getExtras().getInt("id");
        RequestQueue queue = Volley.newRequestQueue(this);
        String JSON_URL = "https://api.themoviedb.org/3/movie/" + id + "/credits?api_key=40219592510d78632bfd917982326cc7";
        String IMAGE_URI = "https://image.tmdb.org/t/p/w500";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("cast");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String cast = jsonObject.getString("original_name");
                                movie_cast.append(cast+" ; ");
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
    void runOperations() {

    }

    @Override
    void stopOperations() {

    }
}
