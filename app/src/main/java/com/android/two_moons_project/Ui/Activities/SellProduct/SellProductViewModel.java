package com.android.two_moons_project.Ui.Activities.SellProduct;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.two_moons_project.common.base.BaseView;
import com.android.two_moons_project.common.model.AddProductResponse;
import com.android.two_moons_project.common.model.QrCodeModel.Product;
import com.android.two_moons_project.common.network.WebService;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class SellProductViewModel extends ViewModel {
    MutableLiveData<AddProductResponse> addProductResponseMutableLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void sellProduct(List<Integer> list , BaseView baseView){
        baseView.showProgress(true);
        Single<AddProductResponse> observable = WebService.getInstance().getApi().sellExistProduct(list)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(observable.subscribe(observe->{
            addProductResponseMutableLiveData.setValue(observe);
            baseView.showProgress(false);
        },error->{
            Log.e(TAG, "addProduct: "+error.getMessage() );
            baseView.showProgress(false);
        } ));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
