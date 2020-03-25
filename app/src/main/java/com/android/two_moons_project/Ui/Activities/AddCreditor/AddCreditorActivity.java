package com.android.two_moons_project.Ui.Activities.AddCreditor;

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
import com.android.two_moons_project.Ui.Activities.AddDebtor.AddDebtorActivity;
import com.android.two_moons_project.Ui.Activities.AddDebtor.DebtorAdapter;
import com.android.two_moons_project.common.HelperStuffs.UiUtilities;
import com.android.two_moons_project.common.base.BaseActivity;
import com.android.two_moons_project.common.base.BaseView;
import com.android.two_moons_project.common.model.Creditor;
import com.android.two_moons_project.common.model.CreditorResponse;
import com.android.two_moons_project.common.model.MainResponse;
import com.android.two_moons_project.databinding.ActivityAddCreditorBinding;
import java.util.ArrayList;
import java.util.List;
public class AddCreditorActivity extends BaseActivity implements BaseActivity.onInit, BaseView {
    private ActivityAddCreditorBinding binding;
    private List<Creditor> creditorList;
    private int creditorId;
    private AddCreditorViewModel addCreditorViewModel;
    private ProgressDialog dialog;
    private Creditor creditor;
    private CreditorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this,R.layout.activity_add_creditor);
         onInit();
        setListeners(binding);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void setListeners(Object binding) {
        ((ActivityAddCreditorBinding)binding).addNewCreditor.setOnClickListener(addNewDebtorListener);
        ((ActivityAddCreditorBinding)binding).addExitsCreditor.setOnClickListener(addExistsDebtorListener);
        ((ActivityAddCreditorBinding)binding).submitExistCreditor.setOnClickListener(submitExistCreditorListener);
        ((ActivityAddCreditorBinding)binding).submitNewCreditor.setOnClickListener(submitNewCreditorListener);
        ((ActivityAddCreditorBinding)binding).spinCreditorName.setOnItemSelectedListener(spinCreditorNameListener);
    }
    private AdapterView.OnItemSelectedListener spinCreditorNameListener = new android.widget.AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Creditor debtor = creditorList.get(position);
            creditorId = debtor.getId();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private View.OnClickListener submitExistCreditorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addExistsCreditor();
        }
    };


    private View.OnClickListener submitNewCreditorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addProduct();
        }
    };

    public void addProduct(){
        if(TextUtils.isEmpty(binding.creditorName.getText().toString())){
            dataValidate("من فضلك اكتب اسم الدائن");
        }else if(TextUtils.isEmpty(binding.valueOfMoney.getText().toString())){
            dataValidate("من فضلك اكتب القيمه..");
        }else if(Double.parseDouble(binding.valueOfMoney.getText().toString())<=0){
            dataValidate("لا يجوز اعطاء هذه القيمه");

        }else{
             creditor= new Creditor(binding.creditorName.getText().toString(),Double.parseDouble(binding.valueOfMoney.getText().toString()),UiUtilities.getCurrentDate());
            addCreditorViewModel.addCreditor(creditor,this);
        }

    }
    public void addExistsCreditor(){
        if(creditorId<=0){
            dataValidate("من فضلك اختر من القائمه اولا");
        }else if(TextUtils.isEmpty(String.valueOf(binding.valueOfMoneyExist.getText().toString()))){
            dataValidate("من فضلك اكتب القيمه المدفوعه");
        }else if (Double.parseDouble(binding.valueOfMoneyExist.getText().toString())<=0d){
            dataValidate("هذه القيمه غير صالحه");
        }else {
            addCreditorViewModel.AddExistsCreditor(String.valueOf(creditorId),binding.valueOfMoneyExist.getText().toString(),UiUtilities.getCurrentDate(),this);

        }
    }

    private View.OnClickListener addNewDebtorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            binding.lenNewCreditor.setVisibility(View.VISIBLE);
            binding.lenExistCreditor.setVisibility(View.GONE);

        }
    };

    private View.OnClickListener addExistsDebtorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            binding.lenNewCreditor.setVisibility(View.GONE);
            binding.lenExistCreditor.setVisibility(View.VISIBLE);
            adapter = new CreditorAdapter(AddCreditorActivity.this,creditorList);
            binding.spinCreditorName.setAdapter(adapter);
        }
    };
    public void getExistsCreditor(){
        addCreditorViewModel.getAllExistsCreditor(this);
    }
    @Override
    public void onInit() {
        creditorList = new ArrayList<>();
        creditorList.add(new Creditor("اختر مدين من القائمه",0.0,""));
        addCreditorViewModel = new ViewModelProvider(this).get(AddCreditorViewModel.class);
        getExistsCreditor();
        addCreditorViewModel.mainResponseMutableLiveData.observe(this, new Observer<MainResponse>() {
            @Override
            public void onChanged(MainResponse mainResponse) {
                UiUtilities.showToast(AddCreditorActivity.this,mainResponse.message);
            }
        });
        addCreditorViewModel.creditorResponseMutableLiveData.observe(this, new Observer<CreditorResponse>() {
            @Override
            public void onChanged(CreditorResponse creditorResponse) {
                creditorList.addAll(creditorResponse.getCreditors());
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
        UiUtilities.showToast(AddCreditorActivity.this,result);
    }
}
