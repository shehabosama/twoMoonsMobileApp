package com.android.two_moons_project.Ui.Activities.AddProducts;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.two_moons_project.R;
import com.android.two_moons_project.Ui.Activities.main.MainActivity;
import com.android.two_moons_project.Ui.Activities.main.PostViewModel;
import com.android.two_moons_project.Ui.Activities.main.PostsAdapter;
import com.android.two_moons_project.common.HelperStuffs.Message;
import com.android.two_moons_project.common.HelperStuffs.UiUtilities;
import com.android.two_moons_project.common.base.BaseActivity;
import com.android.two_moons_project.common.base.BaseView;
import com.android.two_moons_project.common.model.AddProductResponse;
import com.android.two_moons_project.common.model.QrCodeModel.Product;
import com.android.two_moons_project.common.model.QrCodeModel.ProductQr;
import com.android.two_moons_project.databinding.ActivityAddProdcutsBinding;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddProductsActivity extends BaseActivity implements BaseActivity.onInit , AddExitsProductAdapter.AddProductInterAction , BaseView {

    private AddExistProtViewModel addExistProtViewModel;
    private ActivityAddProdcutsBinding binding;
    CustomAddProductDialog customAddProductDialog;
    List<ProductQr> list ;
    List<Integer> ides;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this,R.layout.activity_add_prodcuts);
         onInit();
        setListeners(binding);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    protected void setListeners(Object binding) {
        ((ActivityAddProdcutsBinding)binding).addExistsProject.setOnClickListener(addListener);
        ((ActivityAddProdcutsBinding)binding).addNewProduct.setOnClickListener(addNewListener);
        ((ActivityAddProdcutsBinding)binding).AddProductsList.setOnClickListener(addProductListener);
    }


    private void initiateScan() {
        IntentIntegrator integrator = new IntentIntegrator(AddProductsActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setBeepEnabled(true)
                .setPrompt("من فضلك يا حج اسامه وجهه الكميرا بتعتك للمنتج")
                .initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("trsdt", "onActivityResult: "+requestCode );
        if (resultCode == RESULT_OK) {

            if (requestCode == IntentIntegrator.REQUEST_CODE) {
                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (scanResult != null) {
                    String re = scanResult.getContents();


                    Log.e("json", "onActivityResult: "+re );
                    Gson gson = new Gson();
                    ProductQr productQr = gson.fromJson(re,ProductQr.class);
                    //JsonObject jsonObject = new JsonParser().parse(re).getAsJsonObject();
                    //UiUtilities.showToast(getApplicationContext(),jsonObject.get("name").getAsString());

                    list.add(productQr);
                    ides.add(productQr.getId());
                    AddExitsProductAdapter adapter = new AddExitsProductAdapter(this,list,this);
                    binding.recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Message.message(AddProductsActivity.this,ides.toString());

                    if(list.size()>0){
                        binding.AddProductsList.setVisibility(View.VISIBLE);
                    }


                    /**
                     {
                     "id":1,
                     "name": "زيت صنسيلك",
                     "price": 100.5,
                     "org_price":50.5
                     }
                     */

            }
        }else{
                System.out.println("onActivityResult Main Activity"+data.getData());
                    customAddProductDialog.onActivityResult(requestCode, resultCode, data);
                }
            }


        }


    private View.OnClickListener addNewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        customAddProductDialog.showDialog(AddProductsActivity.this);
        }
    };
    private View.OnClickListener addListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            initiateScan();
            //  startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    };
    private View.OnClickListener addProductListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                 addProduct();
        }
    };

    public void addProduct(){
        addExistProtViewModel.addProduct(ides,this);
    }
    @Override
    public void onInit() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
         customAddProductDialog = new CustomAddProductDialog();

        addExistProtViewModel = new ViewModelProvider(this).get(AddExistProtViewModel.class);
         list= new ArrayList<>();
         ides = new ArrayList<>();

        addExistProtViewModel.mutableLiveData.observe(this, new Observer<AddProductResponse>() {
            @Override
            public void onChanged(AddProductResponse addProductResponse) {
                binding.textResult.setText("المنتجات التي سجلت: "+String.valueOf(addProductResponse.getAddedFile().toString()) +" من المنتجات"+ "\n "+"المنتجات التي تم رفضها :"+ addProductResponse.getNotAdded().toString() );
            }
        });
    }

    @Override
    public void showProgress(boolean show) {
      if(show){
          dialog = new ProgressDialog(this);
          dialog.setMessage("Uploading....");
          dialog.setIndeterminate(false);
          dialog.setCancelable(false);
          dialog.show();
      }else{
          dialog.dismiss();
      }

    }

    @Override
    public void dataValidate(String result) {

    }
}
