package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.MainActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

// base class of adapter abstract
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    //need context to inflate a view
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies)
    {
        this.context = context;
        this.movies = movies;
    }
    //Usually involves inflating a layout (itemMovie) from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView =  LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }
    //populating data into item through ViewHolder take data on that position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d("MovieAdapter", "onCreateViewHolder"+ position);

        //get movie at the passed in position
        Movie movie = movies.get(position);
        // bind movie data into the VH
        holder.bind(movie);
    }
    //return total item count on list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageURL;

            //if phone landscape -> imageURL = backdrop
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            imageURL = movie.getBackdropPath();
            else
                imageURL = movie.getPosterPath();

            // else default -> poster image
            Glide.with(context).load(imageURL).into(ivPoster);
            //1.register click listener on whole row
            //2. navigate to new activity on tap
            container.setOnClickListener(new View.OnClickListener()
            {
                @Override

                public void onClick(View v)
                {
                    //Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();;
                    Intent i = new Intent(context, DetailActivity.class);
                    //i.putExtra("title", movie.getTitle());
                    i.putExtra("movie", Parcels.wrap(movie));
                    //parseable know how to create object down and recieve it
                    //make movie class in parseable
                    context.startActivity(i);           }

            });
        }
    }

}
