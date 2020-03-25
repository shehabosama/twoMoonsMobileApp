package com.android.two_moons_project.Ui.Activities.Imports;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.android.two_moons_project.R;
import com.android.two_moons_project.Ui.Activities.AddCreditor.AddCreditorActivity;
import com.android.two_moons_project.Ui.Activities.AddDebtor.AddDebtorActivity;
import com.android.two_moons_project.Ui.Activities.AddProducts.AddProductsActivity;
import com.android.two_moons_project.Ui.Activities.ReceiveFromDebtor.ReceiveFromDebtor;
import com.android.two_moons_project.Ui.Activities.ReceiveFromDebtor.ReceveFromDebtorViewModel;
import com.android.two_moons_project.common.base.BaseActivity;
import com.android.two_moons_project.databinding.ActivityImportBinding;

public class ImportActivity extends BaseActivity {
   private ActivityImportBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this,R.layout.activity_import);
        setListeners(binding);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void setListeners(Object binding) {
        ((ActivityImportBinding)binding).addDebtor.setOnClickListener(addDebtorListener);
        ((ActivityImportBinding)binding).addProduct.setOnClickListener(addProductListener);
        ((ActivityImportBinding)binding).addCreditor.setOnClickListener(addCreditorListener);
        ((ActivityImportBinding) binding).receiveDebtor.setOnClickListener(receiveDebtorListener);

    }
    private View.OnClickListener receiveDebtorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), ReceiveFromDebtor.class));
        }
    };
    private View.OnClickListener addDebtorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), AddDebtorActivity.class));
        }
    };
    private View.OnClickListener addProductListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), AddProductsActivity.class));
        }
    };
    private View.OnClickListener addCreditorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), AddCreditorActivity.class));
        }
    };


}
