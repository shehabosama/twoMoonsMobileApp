package com.android.two_moons_project.Ui.Activities.AddDebtor;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.android.two_moons_project.common.HelperStuffs.UiUtilities;
import com.android.two_moons_project.common.base.BaseView;
import com.android.two_moons_project.common.model.Debtor;
import com.android.two_moons_project.common.model.DebtorResponse;
import com.android.two_moons_project.common.model.MainResponse;
import com.android.two_moons_project.common.network.WebService;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import static androidx.constraintlayout.widget.Constraints.TAG;
public class AddDebtorViewModel extends ViewModel {
    MutableLiveData<MainResponse> mainResponseMutableLiveData = new MutableLiveData<>();
    MutableLiveData<DebtorResponse> debtorResponseMutableLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void addDebtor(Debtor debtor, BaseView baseView){

        baseView.showProgress(true);
        if(debtor!=null){
                Single<MainResponse> observable =  WebService.getInstance().getApi().AddDebtor(debtor.getName(),debtor.getAmountOfMoney(),debtor.getCreatedAt()).subscribeOn(Schedulers.io()).
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

    public void getAllExistsDebtor(BaseView baseView){
        baseView.showProgress(true);
        Single<DebtorResponse> observable = WebService.getInstance().getApi().getAllDebtor().
                subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(observable.subscribe(observer->{
            debtorResponseMutableLiveData.setValue(observer);
            baseView.showProgress(false);
        },error->{
            Log.e(TAG, "getAllExistsDebtor: "+error.getMessage() );
            baseView.showProgress(false);
        }));
    }
    public void AddExistsDebtor(String id ,String amountOfMoney,String CreatedAt,BaseView baseView){
        baseView.showProgress(true);
        Single<MainResponse> observable = WebService.getInstance().getApi().addExistsDebtor(Integer.parseInt(id),Double.parseDouble(amountOfMoney), UiUtilities.getCurrentDate())
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
