package com.android.two_moons_project.Ui.Activities.SellProduct;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.android.two_moons_project.R;
import com.android.two_moons_project.Ui.Activities.AddProducts.AddExitsProductAdapter;
import com.android.two_moons_project.Ui.Activities.AddProducts.AddProductsActivity;
import com.android.two_moons_project.Ui.Activities.main.MainActivity;
import com.android.two_moons_project.common.HelperStuffs.Message;
import com.android.two_moons_project.common.HelperStuffs.UiUtilities;
import com.android.two_moons_project.common.base.BaseActivity;
import com.android.two_moons_project.common.base.BaseView;
import com.android.two_moons_project.common.model.AddProductResponse;
import com.android.two_moons_project.common.model.QrCodeModel.ProductQr;
import com.android.two_moons_project.databinding.ActivityMainBinding;
import com.android.two_moons_project.databinding.ActivitySellProductBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;


public class SellProductActivity extends BaseActivity implements BaseActivity.onInit , AddExitsProductAdapter.AddProductInterAction, BaseView {
    private ActivitySellProductBinding binding;
    private List<ProductQr> list ;
    private List<Integer> ides;
    private SellProductViewModel sellProductViewModel;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this,R.layout.activity_sell_product);
         onInit();
        setListeners(binding);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
                    Message.message(SellProductActivity.this,ides.toString());

                    if(list.size()>0){
                        binding.btnSell.setVisibility(View.VISIBLE);
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
            }
        }

    }
    private void initiateScan() {
        IntentIntegrator integrator = new IntentIntegrator(SellProductActivity.this);
        integrator.setBeepEnabled(true)
                .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                .setPrompt("من فضلك يا حج اسامه وجهه الكميرا بتعتك للمنتج")
                .initiateScan();
    }
    @Override
    protected void setListeners(Object obj) {
        ((ActivitySellProductBinding) obj).sellProductNow.setOnClickListener(sellListener);
        ((ActivitySellProductBinding) obj).btnSell.setOnClickListener(btnSellListener);
    }
    private View.OnClickListener btnSellListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sellProduct();
        }
    };
    public void sellProduct(){
        sellProductViewModel.sellProduct(ides,this);

    }
    private View.OnClickListener sellListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            initiateScan();
          //  startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    };

    @Override
    public void onInit() {
        list= new ArrayList<>();
        ides = new ArrayList<>();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sellProductViewModel = new ViewModelProvider(this).get(SellProductViewModel.class);
        sellProductViewModel.addProductResponseMutableLiveData.observe(this, new Observer<AddProductResponse>() {
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

    }
}
