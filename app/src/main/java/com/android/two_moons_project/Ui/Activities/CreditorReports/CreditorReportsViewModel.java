package com.android.two_moons_project.Ui.Activities.CreditorReports;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.two_moons_project.common.base.BaseView;
import com.android.two_moons_project.common.model.CreditorOperationResponse;
import com.android.two_moons_project.common.network.WebService;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CreditorReportsViewModel extends ViewModel {
    MutableLiveData<CreditorOperationResponse> creditorOperationResponseMutableLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void getAllSalesOperation(BaseView baseView){
        baseView.showProgress(true);
        Single<CreditorOperationResponse> observable = WebService.getInstance().getApi().getAllCreditorOperation().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(observable.subscribe(observer->{
            creditorOperationResponseMutableLiveData.setValue(observer);
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
