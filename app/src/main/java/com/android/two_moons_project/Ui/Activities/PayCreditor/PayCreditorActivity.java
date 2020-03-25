package com.android.two_moons_project.Ui.Activities.PayCreditor;

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
import com.android.two_moons_project.Ui.Activities.AddCreditor.CreditorAdapter;
import com.android.two_moons_project.common.HelperStuffs.UiUtilities;
import com.android.two_moons_project.common.base.BaseActivity;
import com.android.two_moons_project.common.base.BaseView;
import com.android.two_moons_project.common.model.Creditor;
import com.android.two_moons_project.common.model.CreditorResponse;
import com.android.two_moons_project.common.model.MainResponse;
import com.android.two_moons_project.databinding.ActivityPayCreditorBinding;

import java.util.ArrayList;
import java.util.List;

public class PayCreditorActivity extends BaseActivity implements BaseView,BaseActivity.onInit {
    private ActivityPayCreditorBinding binding;
    private PayCreditorViewModel payCreditorViewModel;
    private List<Creditor> creditorList;
    private int creditorId=0;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           binding = DataBindingUtil.setContentView(this,R.layout.activity_pay_creditor);
           onInit();
           setListeners(binding);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void setListeners(Object binding) {
        ((ActivityPayCreditorBinding)binding).btnPayForCreditor.setOnClickListener(btnPayForCreditorListener);
        ((ActivityPayCreditorBinding) binding).spinOfCreditor.setSelection(0,false);
        ((ActivityPayCreditorBinding) binding).spinOfCreditor.setOnItemSelectedListener(spinListener);

    }
    private AdapterView.OnItemSelectedListener spinListener =new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Creditor creditor = creditorList.get(position);
            creditorId = creditor.getId();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private View.OnClickListener btnPayForCreditorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            payToCreditor();
        }
    };

    public void payToCreditor(){
        String editTextAmoutOfMoney = binding.editTextAmountOfMoney.getText().toString();
        if(TextUtils.isEmpty(editTextAmoutOfMoney)){
            dataValidate("من فضلك اكتب قيمه السداد");
        }else if(creditorId<=0){
            dataValidate("هذه القيمه غير صالحه");
        }else{
            payCreditorViewModel.PayToCreditor(String.valueOf(creditorId),editTextAmoutOfMoney,this);
        }

    }
    @Override
    public void onInit() {
        creditorList = new ArrayList<>();
        creditorList.clear();
        creditorList.add(new Creditor("اختر دائن من القائمه",0d,""));
        payCreditorViewModel = new ViewModelProvider(this).get(PayCreditorViewModel.class);
        payCreditorViewModel.getAllExistsCreditor(this);
        payCreditorViewModel.creditorResponseMutableLiveData.observe(this, new Observer<CreditorResponse>() {
            @Override
            public void onChanged(CreditorResponse creditorResponse) {
                creditorList.addAll(creditorResponse.getCreditors());
                CreditorAdapter adapter = new CreditorAdapter(PayCreditorActivity.this,creditorList);
                binding.spinOfCreditor.setAdapter(adapter);
            }
        });

        payCreditorViewModel.mainResponseMutableLiveData.observe(this, new Observer<MainResponse>() {
            @Override
            public void onChanged(MainResponse mainResponse) {
                UiUtilities.showToast(PayCreditorActivity.this,mainResponse.message);
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
        UiUtilities.showToast(PayCreditorActivity.this,result);
    }
}
