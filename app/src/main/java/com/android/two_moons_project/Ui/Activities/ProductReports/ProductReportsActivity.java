package com.android.two_moons_project.Ui.Activities.ProductReports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.android.two_moons_project.R;
import com.android.two_moons_project.common.base.BaseActivity;
import com.android.two_moons_project.common.model.ProductResponse;
import com.android.two_moons_project.common.model.Products;
import com.android.two_moons_project.common.model.QrCodeModel.Product;
import com.android.two_moons_project.databinding.ActivityProductReportsBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductReportsActivity extends BaseActivity implements BaseActivity.onInit, ProductsAdapter.ProductsInterAction {

    private ActivityProductReportsBinding binding;
    private ProductReportsViewModel productReportsViewModel;
    private List<Products> productsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_product_reports);
        onInit();
        setListeners(binding);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void setListeners(Object binding) {
        ((ActivityProductReportsBinding)binding).swipe.setOnRefreshListener(swipListener);
    }
    SwipeRefreshLayout.OnRefreshListener swipListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            productsList.clear();
            productReportsViewModel.getAllProduct();
        }
    };

    public void setAllDataInAdapter(List<Products> products){
        ProductsAdapter adapter = new ProductsAdapter(ProductReportsActivity.this,products,this);
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onInit() {
        binding.swipe.setRefreshing(true);
        productReportsViewModel = new ViewModelProvider(this).get(ProductReportsViewModel.class);
        productReportsViewModel.getAllProduct();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productsList = new ArrayList<>();
        productsList.clear();
        productReportsViewModel.debtorResponseMutableLiveData.observe(this, new Observer<ProductResponse>() {
            @Override
            public void onChanged(ProductResponse productResponse) {
                productsList.addAll(productResponse.getProducts());
                setAllDataInAdapter(productsList);
                binding.totalOfCreditors.setText("أجمالي الدائنون : "+productResponse.getTotalOfCreditors());
                binding.difference.setText("صافي المبلغ بين الجمله والقطاعي : "+productResponse.getDifference());
                binding.totalOfDebtors.setText("اجمال المدينون : "+productResponse.getTotalOfDebtor());
                binding.totalPrice.setText("اجمالي السعر العادي : "+productResponse.getTotalPrice());
                binding.totalSales.setText("اجمالي المبيعات : "+productResponse.getTotalSales());
                binding.totalOrgPrice.setText("اجمالي سعر الجمله : "+productResponse.getTotalOrgPrice());
                binding.swipe.setRefreshing(false);
            }
        });
    }
}
