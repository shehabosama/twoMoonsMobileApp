package com.android.two_moons_project.Ui.Activities.AddDebtor;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;

import com.android.two_moons_project.R;
import com.android.two_moons_project.common.HelperStuffs.UiUtilities;
import com.android.two_moons_project.common.base.BaseActivity;
import com.android.two_moons_project.common.base.BaseView;
import com.android.two_moons_project.common.model.Debtor;
import com.android.two_moons_project.common.model.DebtorResponse;
import com.android.two_moons_project.common.model.MainResponse;
import com.android.two_moons_project.databinding.ActivityAddDebrotBinding;

import java.util.ArrayList;
import java.util.List;

public class AddDebtorActivity extends BaseActivity implements BaseActivity.onInit, BaseView {
    private ActivityAddDebrotBinding binding;
    private AddDebtorViewModel debtorViewModel;
    private Debtor debtor;
    private ProgressDialog dialog;
    private List<Debtor> debtors;
    private DebtorAdapter adapter;
    private int debtorId=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this,R.layout.activity_add_debrot);
         onInit();
         setListeners(binding);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void setListeners(Object binding) {
        ((ActivityAddDebrotBinding)binding).addNewDebtor.setOnClickListener(addNewDebtorListener);
        ((ActivityAddDebrotBinding)binding).addExitsDebtor.setOnClickListener(addExistsDebtorListener);
        ((ActivityAddDebrotBinding)binding).submitExistDebtor.setOnClickListener(submitExistDebtorListener);
        ((ActivityAddDebrotBinding)binding).submitNewDebtor.setOnClickListener(submitNewDebtorListener);
        ((ActivityAddDebrotBinding)binding).spinDebtorName.setSelection(0,false);
        ((ActivityAddDebrotBinding)binding).spinDebtorName.setOnItemSelectedListener(spinListener);
    }

    private AdapterView.OnItemSelectedListener spinListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Debtor debtor = debtors.get(position);
            debtorId = debtor.getId();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private View.OnClickListener submitExistDebtorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addExistsDebtor();
        }
    };

    private View.OnClickListener submitNewDebtorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addProduct();
        }
    };

    private View.OnClickListener addNewDebtorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            binding.lenNewDebtor.setVisibility(View.VISIBLE);
            binding.lenExistDebtor.setVisibility(View.GONE);

        }
    };

    private View.OnClickListener addExistsDebtorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            binding.lenNewDebtor.setVisibility(View.GONE);
            binding.lenExistDebtor.setVisibility(View.VISIBLE);
            adapter = new DebtorAdapter(AddDebtorActivity.this,debtors);
            binding.spinDebtorName.setAdapter(adapter);
        }
    };

    public void getExistsDebtor(){
        debtorViewModel.getAllExistsDebtor(this);
    }
    public void addProduct(){
        if(TextUtils.isEmpty(binding.debtorName.getText().toString())){
            dataValidate("من فضلك اكتب اسم المدين");
        }else if(TextUtils.isEmpty(binding.valueOfMoney.getText().toString())){
            dataValidate("من فضلك اكتب القيمه..");
        }else if(Double.parseDouble(binding.valueOfMoney.getText().toString())<=0){
            dataValidate("لا يجوز اعطاء هذه القيمه");

        }else{
            debtor = new Debtor(binding.debtorName.getText().toString(),Double.parseDouble(binding.valueOfMoney.getText().toString()),UiUtilities.getCurrentDate());
            debtorViewModel.addDebtor(debtor,this);
        }

    }
    public void addExistsDebtor(){
        if(debtorId<=0){
            dataValidate("من فضلك اختر من القائمه اولا");
        }else if(TextUtils.isEmpty(String.valueOf(binding.valueOfMoneyExist.getText().toString()))){
            dataValidate("من فضلك اكتب القيمه المدفوعه");
        }else if (Double.parseDouble(binding.valueOfMoneyExist.getText().toString())<=0d){
            dataValidate("هذه القيمه غير صالحه");
        }else {
            debtorViewModel.AddExistsDebtor(String.valueOf(debtorId),binding.valueOfMoneyExist.getText().toString(),UiUtilities.getCurrentDate(),this);

        }
    }
    @Override
    public void onInit() {

        debtors = new ArrayList<>();
        debtors.add(new Debtor("اختر مدين من القائمه",0.0,""));
        debtorViewModel = new ViewModelProvider(this).get(AddDebtorViewModel.class);
        getExistsDebtor();
        debtorViewModel.mainResponseMutableLiveData.observe(this, new Observer<MainResponse>() {
            @Override
            public void onChanged(MainResponse mainResponse) {
                UiUtilities.showToast(AddDebtorActivity.this,mainResponse.message);
            }
        });
        debtorViewModel.debtorResponseMutableLiveData.observe(this, new Observer<DebtorResponse>() {
            @Override
            public void onChanged(DebtorResponse debtorResponse) {
                debtors.addAll(debtorResponse.getDebtors());
            }
        });
    }

    @Override
    public void showProgress(boolean show) {
        if(show){
            dialog = new ProgressDialog(this);
            dialog.setTitle("من فضلك انتظر ثواني");
            dialog.setMessage("جاري اعداد طلبك");
            dialog.setCancelable(false);
            dialog.show();
        }else{
            dialog.dismiss();
        }
    }

    @Override
    public void dataValidate(String result) {
        UiUtilities.showToast(AddDebtorActivity.this,result);
    }
}
