package com.android.two_moons_project.Ui.Activities.DebtorReports;

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
import com.android.two_moons_project.Ui.Activities.CreditorReports.CreditorAdapter;
import com.android.two_moons_project.Ui.Activities.CreditorReports.CreditorReportsActivity;
import com.android.two_moons_project.Ui.Activities.CreditorReports.CreditorReportsViewModel;
import com.android.two_moons_project.common.HelperStuffs.UiUtilities;
import com.android.two_moons_project.common.base.BaseActivity;
import com.android.two_moons_project.common.base.BaseView;
import com.android.two_moons_project.common.model.CreditorOperationResponse;
import com.android.two_moons_project.common.model.CreditorOperations;
import com.android.two_moons_project.common.model.DebtorOperationResponse;
import com.android.two_moons_project.common.model.DebtorsOperations;
import com.android.two_moons_project.databinding.ActivityDebtorReportsBinding;
import com.android.two_moons_project.databinding.ActivityProductReportsBinding;

import java.util.ArrayList;
import java.util.List;

public class DebtorReportsActivity extends BaseActivity implements BaseView,BaseActivity.onInit, DebtorAdapter.SalesInterAction {
    private ActivityDebtorReportsBinding binding;
    private DebtorReportsViewModel debtorReportsViewModel;
    private List<DebtorsOperations> debtorsOperations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this,R.layout.activity_debtor_reports);
        onInit();
        setListeners(binding);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }
    @Override
    protected void setListeners(Object binding) {
        ((ActivityDebtorReportsBinding)binding).swipe.setOnRefreshListener(swipListener);
        ((ActivityDebtorReportsBinding) binding).radioGroup.setOnCheckedChangeListener(radioListener);
    }
    private RadioGroup.OnCheckedChangeListener radioListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            View radioButton = binding.radioGroup.findViewById(checkedId);
            int index = binding.radioGroup.indexOfChild(radioButton);
            List<DebtorsOperations> found = new ArrayList<>();

            switch (index) {
                case 0:

                    for (DebtorsOperations debtorsOperations:debtorsOperations) {
                        if(debtorsOperations.getOper_type()== 2)
                            found.add(debtorsOperations);
                    }
                    setAllDataInAdapter(found);
                    break;
                case 1:
                    for (DebtorsOperations creditorOperations:debtorsOperations) {
                        if(creditorOperations.getOper_type()== 1)
                            found.add(creditorOperations);
                    }
                    setAllDataInAdapter(found);
                    break;
                case 2:
                    setAllDataInAdapter(debtorsOperations);
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
        debtorsOperations.clear();
        debtorReportsViewModel.getAllSalesOperation(this);
    }
    public void setAllDataInAdapter(List<DebtorsOperations> debtorsOperations){
        DebtorAdapter adapter = new DebtorAdapter(DebtorReportsActivity.this,debtorsOperations,this);
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onInit() {
        binding.swipe.setRefreshing(true);
        debtorReportsViewModel = new ViewModelProvider(this).get(DebtorReportsViewModel.class);
        debtorReportsViewModel.getAllSalesOperation(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        debtorsOperations = new ArrayList<>();
        debtorsOperations.clear();
        debtorReportsViewModel.debtorOperationResponseMutableLiveData.observe(this, new Observer<DebtorOperationResponse>() {
            @Override
            public void onChanged(DebtorOperationResponse debtorOperationResponse) {
                debtorsOperations.addAll(debtorOperationResponse.getDebtorOperations());
                setAllDataInAdapter(debtorsOperations);
                binding.totalOfSales.setText("أجمالي المدفوع من المدينون في الفتره السابقه : "+debtorOperationResponse.getTotal_of_debtor());
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
        UiUtilities.showToast(DebtorReportsActivity.this,result);
    }
}
