package com.android.two_moons_project.Ui.Activities.CreditorReports;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.android.two_moons_project.R;
import com.android.two_moons_project.common.HelperStuffs.UiUtilities;
import com.android.two_moons_project.common.base.BaseActivity;
import com.android.two_moons_project.common.base.BaseView;
import com.android.two_moons_project.common.model.CreditorOperationResponse;
import com.android.two_moons_project.common.model.CreditorOperations;
import com.android.two_moons_project.common.model.SalesOperations;
import com.android.two_moons_project.databinding.ActivityCreditrorReportsBinding;
import com.android.two_moons_project.databinding.ActivityProductReportsBinding;

import java.util.ArrayList;
import java.util.List;

public class CreditorReportsActivity extends BaseActivity implements BaseView, CreditorAdapter.CreditorsInterAction,BaseActivity.onInit {

    private List<CreditorOperations> creditorOperations;
    private CreditorReportsViewModel creditorReportsViewModel;
    private ActivityCreditrorReportsBinding binding;
    private String searchOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this,R.layout.activity_creditror_reports);
        onInit();
        setListeners(binding);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }
    @Override
    protected void setListeners(Object binding) {
        ((ActivityCreditrorReportsBinding)binding).swipe.setOnRefreshListener(swipListener);
        ((ActivityCreditrorReportsBinding) binding).radioGroup.setOnCheckedChangeListener(radioListener);
    }
    private RadioGroup.OnCheckedChangeListener radioListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            View radioButton = binding.radioGroup.findViewById(checkedId);
            int index = binding.radioGroup.indexOfChild(radioButton);
            List<CreditorOperations> found = new ArrayList<>();

            switch (index) {
                case 0:

                    for (CreditorOperations creditorOperations:creditorOperations) {
                        if(creditorOperations.getOper_type()== 2)
                            found.add(creditorOperations);
                    }
                    setAllDataInAdapter(found);
                    break;
                case 1:
                    for (CreditorOperations creditorOperations:creditorOperations) {
                        if(creditorOperations.getOper_type()== 1)
                            found.add(creditorOperations);
                    }
                    setAllDataInAdapter(found);
                    break;
                case 2:
                    setAllDataInAdapter(creditorOperations);
            }
        }
    };

    SwipeRefreshLayout.OnRefreshListener swipListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getAllSales();
        }
    };

    public void getAllSales(){
        creditorOperations.clear();
        creditorReportsViewModel.getAllSalesOperation(this);
    }
    public void setAllDataInAdapter(List<CreditorOperations> salesOperations){
        CreditorAdapter adapter = new CreditorAdapter(CreditorReportsActivity.this,salesOperations,this);
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onInit() {
        binding.swipe.setRefreshing(true);
        creditorReportsViewModel = new ViewModelProvider(this).get(CreditorReportsViewModel.class);
        creditorReportsViewModel.getAllSalesOperation(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        creditorOperations = new ArrayList<>();
        creditorOperations.clear();
        creditorReportsViewModel.creditorOperationResponseMutableLiveData.observe(this, new Observer<CreditorOperationResponse>() {
            @Override
            public void onChanged(CreditorOperationResponse salesOperationResponse) {
                creditorOperations.addAll(salesOperationResponse.getCreditorOperations());
                setAllDataInAdapter(creditorOperations);
                binding.totalOfSales.setText("أجمالي المدفوع للدائنون الفتره السابقه : "+salesOperationResponse.getTotal_of_creditor());
                binding.swipe.setRefreshing(false);
            }
        });
    }

    @Override
    public void showProgress(boolean show) {
        binding.swipe.setRefreshing(show);
    }

    @Override
    public void dataValidate(String result) {
        UiUtilities.showToast(CreditorReportsActivity.this,result);
    }
}
