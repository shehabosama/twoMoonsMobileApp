package com.android.two_moons_project.Ui.Activities.SalesReports;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.two_moons_project.common.base.BaseView;
import com.android.two_moons_project.common.model.SalesOperationResponse;
import com.android.two_moons_project.common.network.WebService;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SalesReportsViewModel extends ViewModel {
    MutableLiveData<SalesOperationResponse> salesOperationResponseMutableLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void getAllSalesOperation(BaseView baseView){
        baseView.showProgress(true);
        Single<SalesOperationResponse> observable = WebService.getInstance().getApi().getAllSales().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(observable.subscribe(observer->{
            salesOperationResponseMutableLiveData.setValue(observer);
            baseView.showProgress(false);
        },error->{
            Log.e(TAG, "getAllSalesOperation: "+error.getMessage() );
            baseView.showProgress(false);
        }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
