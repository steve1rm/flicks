package me.androidbox.flicks.movielist;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.androidbox.flicks.R;
import me.androidbox.flicks.model.Results;
import me.androidbox.flicks.utils.Constants;
import me.androidbox.flicks.utils.ImageBuilder;

import static android.R.attr.bitmap;

/**
 * Created by steve on 10/15/16.
 */

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Results> mMoviesList = Collections.emptyList();
    private WeakReference<Context> mContext;
    private final int PORTRAIT = 0;
    private final int LANDSCAPE = 1;

    public MovieListAdapter(List<Results> moviesList, Context context) {
        mMoviesList = new ArrayList<>(moviesList);
        mContext = new WeakReference<>(context);
    }

    @Override
    public int getItemCount() {
        return (mMoviesList != null) ? mMoviesList.size() : 0;
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
                viewHolder = new MovieViewHolderPortrait(view, MovieListAdapter.this, mContext.get());
                break;

            case LANDSCAPE:
                view = layoutInflater.inflate(R.layout.movie_info_landscape, parent, false);
                viewHolder = new MovieViewHolderLandscape(view);
                break;
        }

        return viewHolder;
    }

    public void updateMovieList(List<Results> movies) {
        mMoviesList.addAll(movies);
        notifyItemRangeInserted(0, movies.size());
    }

    public int getMovieId(int position) {
        return mMoviesList.get(position).getId();
    }

    /* Clean all items for a refresh */
    public void clearMovies() {
        mMoviesList.clear();
        notifyDataSetChanged();
    }

    /* All fresh movies */
    public void addFreshMovies(List<Results> movies) {
        mMoviesList.addAll(movies);
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

    /* Change layout to display in portrait mode */
    private void bindPortraitMode(final MovieViewHolderPortrait viewHolderPortrait, int position) {
        viewHolderPortrait.mTvMovieTitle.setText(mMoviesList.get(position).getTitle());
        //     holder.mTvMovieOverview.setText(mMoviesList.getResults().get(position).getOverview());

        final com.squareup.picasso.Target target = new com.squareup.picasso.Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                viewHolderPortrait.mIvMovieHeader.setImageBitmap(bitmap);

                Palette.from(bitmap).maximumColorCount(12).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch vibrant = palette.getDarkVibrantSwatch();
                        if(vibrant != null) {
                            viewHolderPortrait.mFlFooterBackground.setBackgroundColor(vibrant.getRgb());
                            viewHolderPortrait.mTvMovieTitle.setTextColor(vibrant.getTitleTextColor());
                        }
                    }
                });


 /*                   Glide.with(mContext.get())
                            .load(ImageBuilder.buildImagePath(0, mMoviesList.get(position).getPoster_path()))
                            .placeholder(R.drawable.placeholder_poster)
                            .centerCrop()
                            .crossFade()
                            .into(viewHolderPortrait.mIvMovieHeader);
*/
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        Picasso.with(mContext.get()).
                load(ImageBuilder.buildImagePath(Constants.W185, mMoviesList.get(position).
                        getPoster_path()))
                .placeholder(R.drawable.placeholder_poster)
                .error(R.drawable.placeholder_poster)
                .into(target);

    }

    /* Change layout to display in landscape mode */
    private void bindLandscapeMode(MovieViewHolderLandscape viewHolderLandscape, int position) {
        viewHolderLandscape.mTvMovieTitle.setText(mMoviesList.get(position).getTitle());
        viewHolderLandscape.mTvMovieOverview.setText(mMoviesList.get(position).getOverview());
        viewHolderLandscape.mTvTagline.setText(mMoviesList.get(position).getRelease_date());

        /* Build image path to display associated image */
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.CONFIGURATION);
        stringBuilder.append(Constants.W300);
        stringBuilder.append(mMoviesList.get(position).getBackdrop_path());

        Glide.with(mContext.get())
                .load(stringBuilder.toString())
                .placeholder(R.drawable.placeholder_poster)
                .centerCrop()
                .crossFade()
                .into(viewHolderLandscape.mIvBackdropImage);
    }
}
