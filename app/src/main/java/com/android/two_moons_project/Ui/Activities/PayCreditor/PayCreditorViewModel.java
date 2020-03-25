package com.android.two_moons_project.Ui.Activities.PayCreditor;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.two_moons_project.common.HelperStuffs.UiUtilities;
import com.android.two_moons_project.common.base.BaseView;
import com.android.two_moons_project.common.model.Creditor;
import com.android.two_moons_project.common.model.CreditorResponse;
import com.android.two_moons_project.common.model.MainResponse;
import com.android.two_moons_project.common.network.WebService;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PayCreditorViewModel extends ViewModel {
    MutableLiveData<CreditorResponse> creditorResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<MainResponse> mainResponseMutableLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void getAllExistsCreditor(BaseView baseView){
        baseView.showProgress(true);
        Single<CreditorResponse> observable = WebService.getInstance().getApi().getAllCreditor().
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(observable.subscribe(observer->{
            try{
                creditorResponseMutableLiveData.setValue(observer);
            }catch (ExceptionInInitializerError|NullPointerException t){
                Log.e(TAG, "addCreditor: "+t.getMessage() );
            }
            baseView.showProgress(false);
        },error->{
            Log.e(TAG, "getAllExistsDebtor: "+error.getMessage() );
            baseView.showProgress(false);
        }));
    }
    public void PayToCreditor(String id ,String amountOfMoney, BaseView baseView){
        baseView.showProgress(true);
            Single<MainResponse> observable =  WebService.getInstance().getApi().PayToCreditor(Integer.parseInt(id),Double.parseDouble(amountOfMoney), UiUtilities.getCurrentDate())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            compositeDisposable.add(observable.subscribe(observer->{
                mainResponseMutableLiveData.setValue(observer);

                baseView.showProgress(false);
            } ,error->{
                Log.e(TAG, "AddDebtor: "+error.getMessage() );

                baseView.showProgress(false);
            } ));

    }
}
