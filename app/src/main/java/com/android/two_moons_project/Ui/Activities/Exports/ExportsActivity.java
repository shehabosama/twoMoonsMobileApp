package com.android.two_moons_project.Ui.Activities.Exports;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.android.two_moons_project.R;
import com.android.two_moons_project.Ui.Activities.PayCreditor.PayCreditorActivity;
import com.android.two_moons_project.Ui.Activities.SellProduct.SellProductActivity;
import com.android.two_moons_project.common.base.BaseActivity;
import com.android.two_moons_project.databinding.ActivityExportsBinding;

public class ExportsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityExportsBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_exports);
        setListeners(binding);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void setListeners(Object binding) {
        ((ActivityExportsBinding)binding).payCreditors.setOnClickListener(payCreditorListener);
        ((ActivityExportsBinding)binding).sellProduct.setOnClickListener(sellProductListener);
    }
    private View.OnClickListener payCreditorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), PayCreditorActivity.class));
        }
    };
    private View.OnClickListener sellProductListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), SellProductActivity.class));
        }
    };
}
