package gr.athtech.coolmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import gr.athtech.coolmovies.Activity.DetailsActivity;
import gr.athtech.coolmovies.Model.MovieData;
import gr.athtech.coolmovies.R;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.viewHolder> {

    Context context;
    List<MovieData> movieData;

    public FavMovieAdapter(Context context, List<MovieData> movieData) {
        this.movieData = movieData;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_fav_item_list, parent, false);
        viewHolder viewHolder = new viewHolder(view);

        viewHolder.view_container_fav.setOnClickListener(v -> {
            Intent i = new Intent(context, DetailsActivity.class);
            i.putExtra("id", movieData.get(viewHolder.getAdapterPosition()).getId());
            i.putExtra("movie_name", movieData.get(viewHolder.getAdapterPosition()).getTitle());
            context.startActivity(i);
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.textViewName.setText(movieData.get(position).getTitle());
        holder.textViewDate.setText(new StringBuilder().append("Released: ").append(movieData.get(position).getReleaseDate()));
        Picasso.get().load(movieData.get(position).getPosterPath()).fit().centerCrop().into(holder.movieImage);
        holder.textViewMRating.setText(String.valueOf(movieData.get(position).getVoteAverage()));
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView textViewName;
        TextView textViewDate;
        TextView textViewMRating;
        LinearLayout view_container_fav;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            view_container_fav = itemView.findViewById(R.id.view_container_fav);
            movieImage = itemView.findViewById(R.id.movie_imageView);
            textViewName = itemView.findViewById(R.id.textName);
            textViewDate = itemView.findViewById(R.id.textDate);
            textViewMRating = itemView.findViewById(R.id.ratingText);
        }
    }

}
