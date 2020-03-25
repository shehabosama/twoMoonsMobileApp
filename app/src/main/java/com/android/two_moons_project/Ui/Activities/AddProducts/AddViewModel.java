package com.android.two_moons_project.Ui.Activities.AddProducts;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.two_moons_project.common.HelperStuffs.FileUtil;
import com.android.two_moons_project.common.HelperStuffs.Message;
import com.android.two_moons_project.common.HelperStuffs.ProgressRequestBody;
import com.android.two_moons_project.common.HelperStuffs.UploadCallBacks;
import com.android.two_moons_project.common.model.MainResponse;
import com.android.two_moons_project.common.model.QrCodeModel.Product;
import com.android.two_moons_project.common.network.WebService;

import java.io.File;
import java.util.Random;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

import static android.content.ContentValues.TAG;

public class AddViewModel extends ViewModel implements UploadCallBacks {
    MutableLiveData<MainResponse>mutableLiveData = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ProgressDialog dialog;
    public void addProduct(Product product/*, Uri uri*/, Context context){

        dialog = new ProgressDialog(context);
       // dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMessage("Uploading....");
        //dialog.setIndeterminate(false);
       // dialog.setMax(100);
        dialog.setCancelable(false);
        dialog.show();

//        Log.e(TAG, "addProduct: " );
//        File file = new File( FileUtil.getPath(uri,context));
////        final RequestBody requestFile =
////                RequestBody.create(
////                        MediaType.parse("image/jpg"),
////                        file
////                );
//
//        Random rand = new Random();
//        int value = rand.nextInt(50);
//        final String filename =String.valueOf(value)+file.getName();
//
//        ProgressRequestBody requestFile = new ProgressRequestBody(file,this);
//
//        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", filename, requestFile);
        Single<MainResponse> observable = WebService.getInstance().getApi().AddProduct(/*body,*/product.getProductName(),product.getCount(),product.getPrice(),product.getOrgPrice(),product.getCreateAt(),product.getTotalOfPrice(),product.getTotalOfOrgPrice())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        compositeDisposable.add(observable.subscribe(observe->{
            mutableLiveData.setValue(observe);
            dialog.dismiss();
        },error->{
            Log.e(TAG, "addProduct: "+error.getMessage() );
            dialog.dismiss();
        } ));
    }

    @Override
    public void onProgressUpdate(int percetage) {
        dialog.setProgress(percetage);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    //    public Single<MainResponse> uploadImageWithoutProgress(Product product,String filePath) {
//
//        return WebService.getInstance().getApi().AddProduct(createMultipartBody(filePath, emitter),product.getProductName(),product.getCount(),product.getPrice(),product.getOrgPrice(),product.getCreateAt(),product.getTotalOfPrice(),product.getTotalOfOrgPrice());
//    }



    // -- 3 --
//    private MultipartBody.Part createMultipartBody(String filePath, FlowableEmitter<Object> emitter) {
//        File file = new File(filePath); // -- 1 --
//        RequestBody requestBody = createRequestBody(file);
//        return MultipartBody.Part.createFormData("uploaded_file", file.getName(), createCountingRequestBody(file, emitter));
//    }
//
//    private RequestBody createCountingRequestBody(File file, FlowableEmitter<Object> emitter) {
//        RequestBody requestBody = createRequestBody(file);
//        return new CountingRequestBody(requestBody, (bytesWritten, contentLength) -> {
//            double progress = (1.0 * bytesWritten) / contentLength;
//            emitter.onNext(progress);
//        });
//    }
//
//    // -- 2 --
//    private RequestBody createRequestBody(File file) {
//        return RequestBody.create(MediaType.parse("image/*"), file);
//    }
}
