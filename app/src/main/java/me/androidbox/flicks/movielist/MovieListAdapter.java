package me.androidbox.flicks.movielist;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;

import me.androidbox.flicks.R;
import me.androidbox.flicks.model.Movies;
import me.androidbox.flicks.utils.Constants;

/**
 * Created by steve on 10/15/16.
 */

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Movies mMoviesList;
    private WeakReference<Context> mContext;
    private final int PORTRAIT = 0;
    private final int LANDSCAPE = 1;

    public MovieListAdapter(Movies movies, Context context) {
        mMoviesList = movies;
        mContext = new WeakReference<>(context);
    }

    @Override
    public int getItemCount() {
        return (mMoviesList == null) ? 0 : mMoviesList.getResults().size();
    }

    @Override
    public int getItemViewType(int position) {
        /* Set default to portrait */
        int orientation = PORTRAIT;

        if(mContext.get().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT) {
            orientation = PORTRAIT;
        }
        else if(mContext.get().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {
            orientation = LANDSCAPE;
        }

        return orientation;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        View view;

        switch(viewType) {
            case PORTRAIT:
                view = layoutInflater.inflate(R.layout.movie_info, parent, false);
                viewHolder = new MovieViewHolderPortrait(view);
                break;

            case LANDSCAPE:
                view = layoutInflater.inflate(R.layout.movie_info_landscape, parent, false);
                viewHolder = new MovieViewHolderLandscape(view);
                break;
        }

        return viewHolder;
    }

    public void updateMovieList(Movies movies) {
        mMoviesList= movies;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch(holder.getItemViewType()) {
            case PORTRAIT:
                MovieViewHolderPortrait viewHolderPortrait = (MovieViewHolderPortrait)holder;
                bindPortraitMode(viewHolderPortrait, position);
                break;

            case LANDSCAPE:
                MovieViewHolderLandscape viewHolderLandscape = (MovieViewHolderLandscape)holder;
                bindLandscapeMode(viewHolderLandscape, position);
                break;
        }
    }

    private void bindPortraitMode(MovieViewHolderPortrait viewHolderPortrait, int position) {
        viewHolderPortrait.mTvMovieTitle.setText(mMoviesList.getResults().get(position).getTitle());
        //     holder.mTvMovieOverview.setText(mMoviesList.getResults().get(position).getOverview());

        /* Build image path to display associated image */
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.CONFIGURATION);
        stringBuilder.append(Constants.W185);
        stringBuilder.append(mMoviesList.getResults().get(position).getPoster_path());

        Glide.with(mContext.get())
                .load(stringBuilder.toString())
                .placeholder(R.drawable.placeholder_poster)
                .centerCrop()
                .crossFade()
                .into(viewHolderPortrait.mIvMovieHeader);
    }

    private void bindLandscapeMode(MovieViewHolderLandscape viewHolderLandscape, int position) {
        viewHolderLandscape.mTvMovieTitle.setText(mMoviesList.getResults().get(position).getTitle());
        viewHolderLandscape.mTvMovieOverview.setText(mMoviesList.getResults().get(position).getOverview());

        /* Build image path to display associated image */
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.CONFIGURATION);
        stringBuilder.append(Constants.W300);
        stringBuilder.append(mMoviesList.getResults().get(position).getBackdrop_path());

        Glide.with(mContext.get())
                .load(stringBuilder.toString())
                .placeholder(R.drawable.placeholder_poster)
                .centerCrop()
                .crossFade()
                .into(viewHolderLandscape.mIvBackdropImage);
    }
}
