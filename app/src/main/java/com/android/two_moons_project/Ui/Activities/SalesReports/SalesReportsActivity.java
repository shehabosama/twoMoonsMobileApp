package com.android.two_moons_project.Ui.Activities.SalesReports;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.android.two_moons_project.R;
import com.android.two_moons_project.common.HelperStuffs.UiUtilities;
import com.android.two_moons_project.common.base.BaseActivity;
import com.android.two_moons_project.common.base.BaseView;
import com.android.two_moons_project.common.model.SalesOperationResponse;
import com.android.two_moons_project.common.model.SalesOperations;
import com.android.two_moons_project.databinding.ActivityProductReportsBinding;
import com.android.two_moons_project.databinding.ActivitySalesReportsBinding;

import java.util.ArrayList;
import java.util.List;

public class SalesReportsActivity extends BaseActivity implements BaseActivity.onInit , SalesAdapter.SalesInterAction, BaseView {

    private ActivitySalesReportsBinding binding;
    private List<SalesOperations> salesOperations;
    private SalesReportsViewModel salesReportsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this,R.layout.activity_sales_reports);
         onInit();
         setListeners(binding);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }
    @Override
    protected void setListeners(Object binding) {
        ((ActivitySalesReportsBinding)binding).swipe.setOnRefreshListener(swipListener);
    }
    SwipeRefreshLayout.OnRefreshListener swipListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
           getAllSales();
        }
    };

    public void getAllSales(){
        salesOperations.clear();
        salesReportsViewModel.getAllSalesOperation(this);
    }
    public void setAllDataInAdapter(List<SalesOperations> salesOperations){
        SalesAdapter adapter = new SalesAdapter(SalesReportsActivity.this,salesOperations,this);
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onInit() {
        binding.swipe.setRefreshing(true);
        salesReportsViewModel = new ViewModelProvider(this).get(SalesReportsViewModel.class);
        salesReportsViewModel.getAllSalesOperation(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        salesOperations = new ArrayList<>();
        salesOperations.clear();
        salesReportsViewModel.salesOperationResponseMutableLiveData.observe(this, new Observer<SalesOperationResponse>() {
            @Override
            public void onChanged(SalesOperationResponse salesOperationResponse) {
                salesOperations.addAll(salesOperationResponse.getSalesOperations());
                setAllDataInAdapter(salesOperations);
                binding.totalOfSales.setText("أجمالي المبيعات : "+salesOperationResponse.getTotal_of_sales());
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
        UiUtilities.showToast(getApplicationContext(),result);

    }
}
