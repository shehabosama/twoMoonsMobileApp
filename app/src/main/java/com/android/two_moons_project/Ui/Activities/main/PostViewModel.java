package com.android.two_moons_project.Ui.Activities.main;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.two_moons_project.common.model.MainResponse;
import com.android.two_moons_project.common.model.Posts;
import com.android.two_moons_project.common.network.WebService;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class PostViewModel extends ViewModel {
    MutableLiveData<List<Posts>> postMutableLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public void get_Posts(){

        Single observable = WebService.getInstance().getApi().getAllPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observer observer = new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Object o) {

                postMutableLiveData.setValue((List<Posts>)o);
            }

            @Override
            public void onError(@NonNull Throwable e) {

                Log.d(TAG, "onError: "+ e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        //type of


        compositeDisposable.add(observable.subscribe(observe->postMutableLiveData.setValue((List<Posts>)observe),error-> Log.e(TAG, "get_Posts: "+error )));

        Observer<List<Posts>> observer1 = new Observer<List<Posts>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Posts> posts) {
                postMutableLiveData.setValue(posts);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();

    }
}
