package me.androidbox.flicks.movielist;

import javax.inject.Inject;

import me.androidbox.flicks.di.DaggerInjector;
import me.androidbox.flicks.model.FlicksMovieService;
import me.androidbox.flicks.model.Movies;
import me.androidbox.flicks.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by steve on 10/12/16.
 */

public class MovieListModelImp implements MovieListModelContract {

    @Inject FlicksMovieService mFlicksMoveService;
    private Subscription mSubscription;

    public MovieListModelImp() {
        DaggerInjector.getsAppComponent().inject(MovieListModelImp.this);

        if(mFlicksMoveService != null) {
            Timber.d("mFlicksMovieService != null");
        }
    }

    @Override
    public void releaseResources() {
        if(mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void getUpComingMovies(final UpComingMovieListener upComingMovieListener) {
        Timber.d("getUpComingMovies");

        mSubscription = mFlicksMoveService.getUpcomingMovies(Constants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Movies>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "onFailure");
                        upComingMovieListener.onGetMovieFailed();
                    }

                    @Override
                    public void onNext(Movies movies) {
                        upComingMovieListener.onGetMovieSuccess(movies);
                        Timber.d("onNext: %d", movies.getResults().size());
                    }
                });
    }
}
