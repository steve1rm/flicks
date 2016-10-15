package me.androidbox.flicks.movielist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.androidbox.flicks.R;
import me.androidbox.flicks.model.Movies;
import me.androidbox.flicks.utils.Constants;

/**
 * Created by steve on 10/15/16.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {

    private Movies mMoviesList;
    private WeakReference<Context> mContext;

    public MovieListAdapter(Movies movies, Context context) {
        mMoviesList = movies;
        mContext = new WeakReference<>(context);
    }

    @Override
    public int getItemCount() {
        return 20; //mMoviesList.getResults().size();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.movie_info, parent, false);

        return new MovieViewHolder(view);
    }

    public void updateMovieList(Movies movies) {
        mMoviesList= movies;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.mTvMovieTitle.setText(mMoviesList.getResults().get(position).getTitle());
   //     holder.mTvMovieOverview.setText(mMoviesList.getResults().get(position).getOverview());

        /* Build image path to display associated image */
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.CONFIGURATION);
        stringBuilder.append(Constants.W185);
        stringBuilder.append(mMoviesList.getResults().get(position).getPoster_path());

        Glide.with(mContext.get())
                .load(stringBuilder.toString())
                .placeholder(R.drawable.ellie_300)
                .centerCrop()
                .crossFade()
                .into(holder.mIvMovieHeader);
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvMovieTitle) TextView mTvMovieTitle;
        @BindView(R.id.tvMovieOverview) TextView mTvMovieOverview;
        @BindView(R.id.ivMovieHeader) ImageView mIvMovieHeader;

        public MovieViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(MovieViewHolder.this, itemView);
        }
    }
}
