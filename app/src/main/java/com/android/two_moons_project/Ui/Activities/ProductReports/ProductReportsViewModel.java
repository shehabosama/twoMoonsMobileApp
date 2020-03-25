package com.android.two_moons_project.Ui.Activities.ProductReports;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.two_moons_project.common.model.DebtorResponse;
import com.android.two_moons_project.common.model.MainResponse;
import com.android.two_moons_project.common.model.ProductResponse;
import com.android.two_moons_project.common.network.WebService;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProductReportsViewModel extends ViewModel {
    MutableLiveData<ProductResponse> debtorResponseMutableLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void getAllProduct(){
        Single<ProductResponse> observable = WebService.getInstance().getApi().getAllProducts().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(observable.subscribe(observer->{
            debtorResponseMutableLiveData.setValue(observer);
        },error->{
            Log.e(TAG, "getAllProduct: "+error.getMessage() );
        }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
