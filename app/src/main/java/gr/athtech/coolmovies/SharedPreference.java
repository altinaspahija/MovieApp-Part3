package gr.athtech.coolmovies;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gr.athtech.coolmovies.Model.MovieData;

public class SharedPreference {
    public static final String PREFS_NAME = "Movie_Popular";
    public static final String FAVORITES = "Movie_Favorite";

    public SharedPreference() {
        super();
    }

    // These methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<MovieData> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        editor = settings.edit();

        Gson gson = new Gson();

        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.apply();
    }

    public void addFavorite(Context context, MovieData movieData) {
        List<MovieData> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<MovieData>();
        favorites.add(movieData);
        saveFavorites(context, favorites);
    }

//    public void removeFavorite(Context context, MovieData movieData) {
//        ArrayList<MovieData> favorites = getFavorites(context);
//        if (favorites != null) {
//            favorites.remove(movieData);
//            saveFavorites(context, favorites);
//        }
//    }

    public ArrayList<MovieData> getFavorites(Context context) {

        SharedPreferences settings;
        List<MovieData> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {

            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            MovieData[] favoriteItems = gson.fromJson(jsonFavorites,
                    MovieData[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<MovieData>(favorites);
        } else
            return null;

        return (ArrayList<MovieData>) favorites;
    }
}
