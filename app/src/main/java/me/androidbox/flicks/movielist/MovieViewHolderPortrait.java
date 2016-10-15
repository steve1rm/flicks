package me.androidbox.flicks.movielist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.androidbox.flicks.R;

/**
 * Created by steve on 10/15/16.
 */

public class MovieViewHolderPortrait extends RecyclerView.ViewHolder {
    @BindView(R.id.tvMovieTitle) TextView mTvMovieTitle;
    @BindView(R.id.tvMovieOverview) TextView mTvMovieOverview;
    @BindView(R.id.ivMovieHeader) ImageView mIvMovieHeader;

    public MovieViewHolderPortrait(View itemView) {
        super(itemView);

        ButterKnife.bind(MovieViewHolderPortrait.this, itemView);
    }
}
