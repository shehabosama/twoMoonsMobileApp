package com.android.two_moons_project.Ui.Activities.Reports;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.android.two_moons_project.R;
import com.android.two_moons_project.Ui.Activities.CreditorReports.CreditorReportsActivity;
import com.android.two_moons_project.Ui.Activities.DebtorReports.DebtorReportsActivity;
import com.android.two_moons_project.Ui.Activities.ProductReports.ProductReportsActivity;
import com.android.two_moons_project.Ui.Activities.SalesReports.SalesReportsActivity;
import com.android.two_moons_project.common.base.BaseActivity;
import com.android.two_moons_project.databinding.ActivityReportsBinding;

public class ReportsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityReportsBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_reports);
        setListeners(binding);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void setListeners(Object binding) {
        ((ActivityReportsBinding)binding).btnCreditorReports.setOnClickListener(btnCreditorReportsListener);
        ((ActivityReportsBinding)binding).btnDebtorReports.setOnClickListener(btnDebtorReportsListener);
        ((ActivityReportsBinding)binding).btnSalesReports.setOnClickListener(btnSalesReportsListener);
        ((ActivityReportsBinding) binding).btnProductReports.setOnClickListener(btnProductsListener);

    }
    private View.OnClickListener btnProductsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), ProductReportsActivity.class));
        }
    };
    private View.OnClickListener btnCreditorReportsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), CreditorReportsActivity.class));
        }
    };
    private View.OnClickListener btnDebtorReportsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), DebtorReportsActivity.class));

        }
    };
    private View.OnClickListener btnSalesReportsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), SalesReportsActivity.class));

        }
    };
}
