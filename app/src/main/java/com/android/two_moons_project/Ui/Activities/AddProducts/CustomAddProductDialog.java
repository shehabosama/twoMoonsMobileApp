package com.android.two_moons_project.Ui.Activities.AddProducts;


import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.android.two_moons_project.R;
import com.android.two_moons_project.common.HelperStuffs.Message;
import com.android.two_moons_project.common.HelperStuffs.UiUtilities;
import com.android.two_moons_project.common.model.MainResponse;
import com.android.two_moons_project.common.model.QrCodeModel.Product;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Hashtable;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


public class CustomAddProductDialog extends DialogFragment {

    private static final int PICK_FROM_GALLERY = 10;
    private static final int PIC_CROP = 20;
   // private Uri uri;
    private Activity activity;
    private Dialog dialog;
    private AddViewModel addViewModel;
    private EditText editTextName,editTextPrice,editTextOrgPrice,editTextAmount;
    private Button /*btnSelect,*/btnUpload;

    public final static int QRcodeWidth = 500 ;
    private static final String IMAGE_DIRECTORY = "/باركود الحج اسامه";
    private Bitmap bitmap ;
    private EditText etqr;

    private Button btn;

    public void showDialog(final Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
        dialog.show();
        dialog.setContentView(R.layout.custom_add_product_dialog);
        // dialog.setCancelable(false);
        initialize(dialog);
        setListener();

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        Log.e(TAG, "onActivityResult: "+"tests" );
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_FROM_GALLERY) {
//            if (resultCode == Activity.RESULT_OK) {
//                uri = data.getData();
//                Log.e(TAG, "onActivityResult: "+uri );
//            }
//        }else{
//            if (resultCode == Activity.RESULT_OK) {
//                uri = data.getData();
//                Log.e(TAG, "onActivityResult: "+uri );
//
//            }
//        }
//    }


    public void initialize(Dialog dialog){
        editTextName = dialog.findViewById(R.id.product_name);
        editTextAmount = dialog.findViewById(R.id.product_amount);
        editTextOrgPrice = dialog.findViewById(R.id.product_org_price);
        editTextPrice = dialog.findViewById(R.id.product_price);
       // btnSelect = dialog.findViewById(R.id.select_photo);
        btnUpload = dialog.findViewById(R.id.btn_add_product);
        addViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(AddViewModel.class);
        addViewModel.mutableLiveData.observe((LifecycleOwner) activity, new Observer<MainResponse>() {
            @Override
            public void onChanged(MainResponse mainResponse) {
                Log.e(TAG, "onChanged: "+mainResponse.message );
               if(Integer.parseInt(mainResponse.message)>0){
                   try {
                       bitmap = TextToImageEncode("{"+
                               " \"id\":"+mainResponse.message+","+
                               " \"name\":"+" \" "+editTextName.getText().toString()+"\","+
                               " \"price\":"+""+Double.parseDouble(editTextPrice.getText().toString())+","+
                               " \"org_price\":"+Double.parseDouble(editTextOrgPrice.getText().toString())+
                               "}");

                       String path = saveImage(bitmap);  //give read write permission
                       UiUtilities.showToast(activity, "QRCode saved to -> "+path);
                   } catch (WriterException e) {
                       e.printStackTrace();
                       Log.e(TAG, "onChanged: "+e.getMessage() );
                   }
               }else{
                   UiUtilities.showToast(activity,"حدث خطاء ما");
               }

            }
        });
    }


    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {
            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, editTextName.getText().toString() + ".jpg");
            f.createNewFile();   //give read write permission
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(activity,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }
    private Bitmap TextToImageEncode(String Value) throws WriterException {

        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, hints
            );

        } catch (IllegalArgumentException Illegalargumentexception) {
            Log.e(TAG, "TextToImageEncode: "+Illegalargumentexception.getMessage() );
            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        activity.getResources().getColor(R.color.black):activity.getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }




    public void setListener(){
      //  btnSelect.setOnClickListener(btnSelectListener);
        btnUpload.setOnClickListener(btnUploadListener);
    }
//    private View.OnClickListener btnSelectListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            openGallery();
//        }
//    };

    public void openGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
    }
    private View.OnClickListener btnUploadListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(TextUtils.isEmpty(editTextAmount.getText().toString())||
                    TextUtils.isEmpty(editTextName.getText().toString())||
                    TextUtils.isEmpty(editTextOrgPrice.getText().toString())||
                    TextUtils.isEmpty(editTextPrice.getText().toString())){
                Message.message(activity,"من فضلك املئ جميع البيانات");

            }else{
                int amount = Integer.parseInt(editTextAmount.getText().toString());
                double price = Double.parseDouble(editTextPrice.getText().toString());
                double orgPrice = Double.parseDouble(editTextOrgPrice.getText().toString());
                double totalPrice = price*amount;
                double totalOrgPrice = orgPrice*amount;
                Product product = new Product(editTextName.getText().toString(),price,orgPrice,
                        amount, UiUtilities.getCurrentDate(),totalOrgPrice,totalPrice);
                addViewModel.addProduct(product/*,uri*/,activity);
            }

        }
    };



}