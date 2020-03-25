package com.android.two_moons_project.Ui.Activities.AddCreditor;

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

public class AddCreditorViewModel extends ViewModel {
    MutableLiveData<MainResponse> mainResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<CreditorResponse> creditorResponseMutableLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void addCreditor(Creditor creditor, BaseView baseView){

        baseView.showProgress(true);
        if(creditor!=null){
                Single<MainResponse> observable =  WebService.getInstance().getApi().AddCreditor(creditor.getName(),creditor.getAmountOfMoney(),creditor.getCreatedAt()).subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread());
                compositeDisposable.add(observable.subscribe(observer->{
                        mainResponseMutableLiveData.setValue(observer);

                    baseView.showProgress(false);
                } ,error->{
                    Log.e(TAG, "AddDebtor: "+error.getMessage() );

                    baseView.showProgress(false);
                } ));
            }
    }

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
    public void AddExistsCreditor(String id ,String amountOfMoney,String CreatedAt,BaseView baseView){
        baseView.showProgress(true);
        Single<MainResponse> observable = WebService.getInstance().getApi().addExistsCreditor(Integer.parseInt(id),Double.parseDouble(amountOfMoney), UiUtilities.getCurrentDate())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(observable.subscribe(observer->{
            mainResponseMutableLiveData.setValue(observer);
            baseView.showProgress(false);
        },error->{
            Log.e(TAG, "AddExistsDebtor: "+error.getMessage());
            baseView.showProgress(false);

        }));

    }
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
