package gr.athtech.coolmovies.Adapter;

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

import gr.athtech.coolmovies.Model.MovieData;
import gr.athtech.coolmovies.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.myviewholder> {
    List<MovieData> movieDataList;

    public SearchAdapter(List<MovieData> movieDataList) {
        this.movieDataList = movieDataList;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_search_item_list, parent, false);
        myviewholder myviewholder = new myviewholder(view);
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        MovieData movieData = movieDataList.get(position);
        holder.textViewName.setText(movieDataList.get(position).getTitle());
        holder.textViewDate.setText(new StringBuilder().append("Released: ").append(movieDataList.get(position).getReleaseDate()));
        Picasso.get().load(movieDataList.get(position).getPosterPath()).fit().centerCrop().into(holder.movieImage);
        holder.textViewMRating.setText(String.valueOf(movieDataList.get(position).getVoteAverage()));
    }


    @Override
    public int getItemCount() {
        return movieDataList.size();
    }


    public class myviewholder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView textViewName;
        TextView textViewDate;
        TextView textViewMRating;
        LinearLayout view_container;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            view_container = itemView.findViewById(R.id.view_container_search);
            movieImage = itemView.findViewById(R.id.movie_imageView);
            textViewName = itemView.findViewById(R.id.textName);
            textViewDate = itemView.findViewById(R.id.textDate);
            textViewMRating = itemView.findViewById(R.id.ratingText);
        }
    }
}
