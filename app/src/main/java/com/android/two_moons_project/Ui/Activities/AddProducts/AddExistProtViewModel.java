package com.android.two_moons_project.Ui.Activities.AddProducts;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.two_moons_project.common.HelperStuffs.FileUtil;
import com.android.two_moons_project.common.HelperStuffs.ProgressRequestBody;
import com.android.two_moons_project.common.base.BaseView;
import com.android.two_moons_project.common.model.AddProductResponse;
import com.android.two_moons_project.common.model.MainResponse;
import com.android.two_moons_project.common.model.QrCodeModel.Product;
import com.android.two_moons_project.common.network.WebService;

import java.io.File;
import java.util.List;
import java.util.Random;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

import static android.content.ContentValues.TAG;

public class AddExistProtViewModel extends ViewModel {
    MutableLiveData<AddProductResponse> mutableLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void addProduct(List<Integer> list , BaseView baseView){
        baseView.showProgress(true);
        Single<AddProductResponse> observable = WebService.getInstance().getApi().AddExistProduct(list)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(observable.subscribe(observe->{
            mutableLiveData.setValue(observe);
            baseView.showProgress(false);
        },error->{
            Log.e(TAG, "addProduct: "+error.getMessage() );
            baseView.showProgress(false);
        } ));
    }

}
