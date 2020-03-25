package com.android.two_moons_project.Ui.Activities.ReceiveFromDebtor;

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
import com.android.two_moons_project.Ui.Activities.AddDebtor.DebtorAdapter;
import com.android.two_moons_project.common.HelperStuffs.UiUtilities;
import com.android.two_moons_project.common.base.BaseActivity;
import com.android.two_moons_project.common.base.BaseView;
import com.android.two_moons_project.common.model.Debtor;
import com.android.two_moons_project.common.model.DebtorResponse;
import com.android.two_moons_project.common.model.MainResponse;
import com.android.two_moons_project.databinding.ActivityPayCreditorBinding;
import com.android.two_moons_project.databinding.ActivityReceiveFromDebtorBinding;

import java.util.ArrayList;
import java.util.List;

public class ReceiveFromDebtor extends BaseActivity implements BaseActivity.onInit , BaseView {

    private ActivityReceiveFromDebtorBinding binding;
    private List<Debtor> debtorList;
    private int debtorId =0;
    private ReceveFromDebtorViewModel receveFromDebtorViewModel;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_from_debtor);
        binding =  DataBindingUtil.setContentView(this,R.layout.activity_receive_from_debtor);
        onInit();
        setListeners(binding);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void setListeners(Object binding) {
        ((ActivityReceiveFromDebtorBinding)binding).btnPayForDebtor.setOnClickListener(receiveFromDebtorListenerListener);
        ((ActivityReceiveFromDebtorBinding) binding).spinOfDebtor.setSelection(0,false);
        ((ActivityReceiveFromDebtorBinding) binding).spinOfDebtor.setOnItemSelectedListener(spinListener);

    }
    private AdapterView.OnItemSelectedListener spinListener =new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Debtor debtor = debtorList.get(position);
            debtorId = debtor.getId();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private View.OnClickListener receiveFromDebtorListenerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            payToCreditor();
        }
    };

    public void payToCreditor(){
        String editTextAmoutOfMoney = binding.editTextAmountOfMoney.getText().toString();
        if(TextUtils.isEmpty(editTextAmoutOfMoney)){
            dataValidate("من فضلك اكتب قيمه السداد");
        }else if (Double.parseDouble(editTextAmoutOfMoney)<=0d){
            dataValidate("هذه القيمه غير صالحه");
        }else if(debtorId <=0){
            dataValidate("من فضلك اختر مدين من القائمه");
        }else{
            receveFromDebtorViewModel.receiveFromDebtor(String.valueOf(debtorId),editTextAmoutOfMoney,this);
        }

    }
    @Override
    public void onInit() {
        debtorList = new ArrayList<>();
        debtorList.clear();
        debtorList.add(new Debtor("اختر دائن من القائمه",0d,""));
        receveFromDebtorViewModel = new ViewModelProvider(this).get(ReceveFromDebtorViewModel.class);
        receveFromDebtorViewModel.getAllExistsCreditor(this);
        receveFromDebtorViewModel.debtorResponseMutableLiveData.observe(this, new Observer<DebtorResponse>() {
            @Override
            public void onChanged(DebtorResponse debtorResponse) {
                debtorList.addAll(debtorResponse.getDebtors());
                DebtorAdapter adapter = new DebtorAdapter(ReceiveFromDebtor.this, debtorList);
                binding.spinOfDebtor.setAdapter(adapter);
            }
        });

        receveFromDebtorViewModel.mainResponseMutableLiveData.observe(this, new Observer<MainResponse>() {
            @Override
            public void onChanged(MainResponse mainResponse) {
                UiUtilities.showToast(ReceiveFromDebtor.this,mainResponse.message);
            }
        });
    }

    @Override
    public void showProgress(boolean show) {
        if(show){
            dialog = new ProgressDialog(this);
            dialog.setTitle("انتظر ثوااني");
            dialog.setMessage("جاري عمليه البيع..");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();
        }else{
            dialog.dismiss();
        }
    }

    @Override
    public void dataValidate(String result) {
        UiUtilities.showToast(ReceiveFromDebtor.this,result);
    }
}
