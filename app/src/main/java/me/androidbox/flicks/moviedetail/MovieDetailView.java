package me.androidbox.flicks.moviedetail;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import me.androidbox.flicks.R;
import me.androidbox.flicks.di.DaggerInjector;
import me.androidbox.flicks.movielist.MovieViewHolderPortrait;
import me.androidbox.flicks.utils.Constants;
import me.androidbox.flicks.utils.ImageBuilder;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailView extends Fragment implements MovieDetailViewContract {
    private static final String MOVIEID_KEY = "movieid_key";

    @Inject MovieDetailPresenterImp mMovieDetailPresenterImp;

    @BindView(R.id.tvMovieTitle) TextView mTvMovieTitle;
    @BindView(R.id.tvReleaseDate) TextView mTvReleaseDate;
    @BindView(R.id.tvMovieOverview) TextView mTvMovieOverview;
    @BindView(R.id.ivMovieDetailPoster) ImageView mIvMovieDetailPoster;
    @BindView(R.id.tvRunningTime) TextView mTvRunningTime;

    private Unbinder mUnbinder;
    private int mMovieId;
    private YouTubePlayerFragment mYouTubePlayerFragment;

    public MovieDetailView() {
        // Required empty public constructor
    }

    public static MovieDetailView getNewInstance(int data) {
        MovieDetailView movieDetailView = new MovieDetailView();
        Bundle bundle = new Bundle();
        bundle.putInt(MOVIEID_KEY, data);
        movieDetailView.setArguments(bundle);

        return movieDetailView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.movie_detail_view, container, false);
        mUnbinder = ButterKnife.bind(MovieDetailView.this, view);

        if(getArguments() != null) {
            Bundle bundle = getArguments();
            mMovieId = bundle.getInt(MovieViewHolderPortrait.MOVIEID_KEY, -1);
            Timber.d("movieId: %d", mMovieId);
        }

  //      mIvMovieDetailPoster.setImageResource(R.drawable.contact_one);

/*
        Glide.with(getActivity())
                .load(R.drawable.contact_one)
                .bitmapTransform(new RoundedCornersTransformation(getActivity(), 8, 0))
                .into(mIvMovieDetailPoster);
*/

/*

        mYouTubePlayerFragment = YouTubePlayerFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.youtubePlayerFragment, mYouTubePlayerFragment);
        fragmentTransaction.commit();
*/

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DaggerInjector.getsAppComponent().inject(MovieDetailView.this);

        if(mMovieDetailPresenterImp != null) {
            Timber.d("mMovieDetailPresenterImp != null");
            mMovieDetailPresenterImp.attachView(MovieDetailView.this);
            if(mMovieId != -1) {
                /* Ask the presenter to get the movie detail */
                mMovieDetailPresenterImp.loadMovieDetail(mMovieId);
                mMovieDetailPresenterImp.loadMovieTrailer(mMovieId);
            }
        }
    }

    @Override
    public void playMovieTrailer(final String videoCode) {
        mYouTubePlayerFragment.initialize("AIzaSyBKQN1qEQAouJ-xUgtbyLg433VrlqD_pxo", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Timber.d("onInitializationSuccess %s", videoCode);
      //          youTubePlayer.loadVideo(videoCode);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Timber.e("Youtube initialization failure: %s is recoverable: %b", youTubeInitializationResult.name(), youTubeInitializationResult.isUserRecoverableError());
            }
        });
    }

    @Override
    public void playMovieTrailerFailure() {
        Toast.makeText(getActivity(), "Failed to get video trailer", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayOverview(String overview) {
        mTvMovieOverview.setText(overview);
    }

    @Override
    public void displayMovieThumbnail(String imageUrl) {

        Glide.with(getActivity())
                .load(ImageBuilder.buildImagePath(Constants.W92, imageUrl))
                .bitmapTransform(new RoundedCornersTransformation(getActivity(), 8, 0))
                .into(mIvMovieDetailPoster);

    }

    @Override
    public void displayTagline(String tagline) {

    }

    @Override
    public void displayTitle(String title) {
        mTvMovieTitle.setText(title);
    }

    @Override
    public void displayReleasedate(String releasedate) {
        mTvReleaseDate.setText(releasedate);
    }

    @Override
    public void displayRunningTime(int runningtime) {
        String minutes = String.valueOf(runningtime).concat(" mintues");
        mTvRunningTime.setText(minutes);
    }

    @Override
    public void displayFailureMessage() {
        Toast.makeText(getActivity(), "Failed to get movie details", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
