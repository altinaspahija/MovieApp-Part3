package gr.athtech.coolmovies.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import gr.athtech.coolmovies.Model.MovieData;
import gr.athtech.coolmovies.R;
import gr.athtech.coolmovies.SharedPreference;


public class MovieAdapter extends ArrayAdapter<MovieData> {

    Context context;
    List<MovieData> movieData;
    SharedPreference sharedPreference;
    MovieData movieDataItem;

    public MovieAdapter(Context context, List<MovieData> movieData){
        super(context, R.layout.movie_item_list, movieData);
        this.movieData = movieData;
        this.context = context;
        sharedPreference = new SharedPreference();
    }

    private class ViewHolder {
        ImageView movieImage;
        TextView textViewName;
        TextView textViewDate;
        TextView textViewMRating;
        LinearLayout view_container;
        ImageView favoriteImage;
    }

    @Override
    public int getCount() {
        return movieData.size();
    }

    @Override
    public MovieData getItem(int position) {
        return movieData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.movie_item_list, null);
            holder = new ViewHolder();

            holder.movieImage = (ImageView) convertView
                    .findViewById(R.id.movie_imageView);
            holder.textViewName = (TextView) convertView
                    .findViewById(R.id.textName);
            holder.textViewDate = (TextView) convertView
                    .findViewById(R.id.textDate);
            holder.favoriteImage = (ImageView) convertView
                    .findViewById(R.id.movie_imageView_heart);
            holder.textViewMRating = (TextView) convertView
                    .findViewById(R.id.ratingText);
            holder.view_container = (LinearLayout) convertView
                    .findViewById(R.id.view_container);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MovieData movieData = (MovieData) getItem(position);

        holder.textViewName.setText(movieData.getTitle());
        holder.textViewDate.setText(new StringBuilder().append("Released: ").append(movieData.getReleaseDate()));
        Picasso.get().load(movieData.getPosterPath()).fit().centerCrop().into(holder.movieImage);
        holder.textViewMRating.setText(String.valueOf(movieData.getVoteAverage()));


        /*If a product exists in shared preferences then set heart_red drawable and set a tag*/
        if (checkFavoriteItem(movieData)) {
            holder.favoriteImage.setImageResource(R.drawable.heart);
            holder.favoriteImage.setTag("red");
        } else {
            holder.favoriteImage.setImageResource(R.drawable.notfullheart);
            holder.favoriteImage.setTag("grey");
        }

        return convertView;
    }


    /*Checks whether a particular product exists in SharedPreferences*/
    public boolean checkFavoriteItem(MovieData checkmovieData) {
        boolean check = false;
        List<MovieData> favorites = sharedPreference.getFavorites(context);
        if (favorites != null) {
            for (MovieData movieData : favorites) {
                if (movieData.equals(checkmovieData)) {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    @Override
    public void add(MovieData movieDataItem) {
        super.add(movieDataItem);
        movieData.add(movieDataItem);
        notifyDataSetChanged();
    }

//    @Override
//    public void remove(MovieData movieDataItem) {
//        super.remove(movieDataItem);
//        movieData.remove(movieDataItem);
//        notifyDataSetChanged();
//    }



}
