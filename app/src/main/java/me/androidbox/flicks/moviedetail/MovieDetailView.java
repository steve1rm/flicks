package me.androidbox.flicks.moviedetail;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.androidbox.flicks.R;
import me.androidbox.flicks.di.DaggerInjector;
import me.androidbox.flicks.movielist.MovieViewHolderPortrait;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailView extends Fragment implements MovieDetailViewContract {

    @Inject
    MovieDetailPresenterImp mMovieDetailPresenterImp;

    @BindView(R.id.ivMovieHeader) ImageView mIvMovieHeader;
    @BindView(R.id.tvTagLine) TextView mTvTagline;
    @BindView(R.id.tvMovieTitle) TextView mTvMovieTitle;
    @BindView(R.id.tvReleaseDate) TextView mTvReleaseDate;
    @BindView(R.id.tvMovieOverview) TextView mTvMovieOverview;

    private Unbinder mUnbinder;
    private int mMovieId;

    public MovieDetailView() {
        // Required empty public constructor
    }

    public static MovieDetailView getNewInstance(int data) {
        MovieDetailView movieDetailView = new MovieDetailView();
        Bundle bundle = new Bundle();
        bundle.putInt(MovieViewHolderPortrait.MOVIEID_KEY, data);
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

        YouTubePlayerFragment youTubePlayerFragment = YouTubePlayerFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.youtubePlayerFragment, youTubePlayerFragment);
        fragmentTransaction.commit();

        youTubePlayerFragment.initialize("AIzaSyBKQN1qEQAouJ-xUgtbyLg433VrlqD_pxo", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Timber.d("onInitializationSuccess");
                youTubePlayer.loadVideo("wtZXHhUjev0");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Timber.e("Youtube initialization failure: %s is recoverable: %b", youTubeInitializationResult.name(), youTubeInitializationResult.isUserRecoverableError());
            }
        });

        if(getArguments() != null) {
            Bundle bundle = getArguments();
            mMovieId = bundle.getInt(MovieViewHolderPortrait.MOVIEID_KEY, -1);
            Timber.d("movieId: %d", mMovieId);
        }

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
                /* As the presenter to get the movie detail */
                mMovieDetailPresenterImp.loadMovieDetail(mMovieId);
            }
        }
    }

    @Override
    public void displayOverview(String overview) {
        mTvMovieOverview.setText(overview);
    }

    @Override
    public void displayTagline(String tagline) {
        mTvTagline.setText(tagline);
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
    public void displayFailureMessage() {
        Toast.makeText(getActivity(), "Failed to get movie details", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
