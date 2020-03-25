package com.android.two_moons_project.common.network;
import com.android.two_moons_project.common.model.AddProductResponse;
import com.android.two_moons_project.common.model.CreditorOperationResponse;
import com.android.two_moons_project.common.model.CreditorResponse;
import com.android.two_moons_project.common.model.Debtor;
import com.android.two_moons_project.common.model.DebtorOperationResponse;
import com.android.two_moons_project.common.model.DebtorResponse;
import com.android.two_moons_project.common.model.MainResponse;
import com.android.two_moons_project.common.model.Posts;
import com.android.two_moons_project.common.model.ProductResponse;
import com.android.two_moons_project.common.model.QrCodeModel.Product;
import com.android.two_moons_project.common.model.SalesOperationResponse;
import com.google.gson.annotations.Expose;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {
    @GET("posts")
    Single<List<Posts>> getAllPosts();
    @FormUrlEncoded
    @POST("AddProduct.php")
    Single<MainResponse> AddProduct(
                                     @Field("product_name") String name,
                                     @Field("product_count")int count,
                                     @Field("product_price")double price,
                                     @Field("product_org_price")double orgPrice,
                                     @Field("create_at")String createAt,
                                     @Field("total_price")double totalPrice,
                                     @Field("total_org_price")double totalOrgPrice);
//    @Multipart
//    @POST("AddProduct.php")
//    Single<MainResponse> AddProduct( @Part MultipartBody.Part file,
//                                     @Part("product_name") String name,
//                                     @Part("product_count")int count,
//                                     @Part("product_price")double price,
//                                     @Part("product_org_price")double orgPrice,
//                                     @Part("create_at")String createAt,
//                                     @Part("total_price")double totalPrice,
//                                     @Part("total_org_price")double totalOrgPrice);
    @FormUrlEncoded
    @POST("AddProductByQr.php")
    Single<AddProductResponse> AddExistProduct(@Field("ides[]") List<Integer> ides);
    @FormUrlEncoded
    @POST("AddDebtor.php")
    Single<MainResponse> AddDebtor(@Field("debtor_name")String name,
                                   @Field("amount_of_money")double amountOfMoney,
                                   @Field("create_at")String createdAt);

    @POST("getAllDebtor.php")
    Single<DebtorResponse> getAllDebtor();
    @FormUrlEncoded
    @POST("AddExistsDebtor.php")
    Single<MainResponse> addExistsDebtor(@Field("id")int id,
                                         @Field("amount_of_money")double amountOfMoney,
                                         @Field("updated_date")String createdAt);
    @FormUrlEncoded
    @POST("AddCreditor.php")
    Single<MainResponse> AddCreditor(@Field("creditor_name")String name,
                                     @Field("amount_of_money")double amountOfMoney,
                                     @Field("create_at")String createdAt);
    @POST("getAllCreditors.php")
    Single<CreditorResponse> getAllCreditor();
    @FormUrlEncoded
    @POST("AddExisitsCreditors.php")
    Single<MainResponse> addExistsCreditor(@Field("id")int id,
                                           @Field("amount_of_money")double amountOfMoney,
                                           @Field("updated_date")String createdAt);
    @FormUrlEncoded
    @POST("SellProducts.php")
    Single<AddProductResponse> sellExistProduct(@Field("ides[]") List<Integer> ides);
    @FormUrlEncoded
    @POST("PayToCreditor.php")
    Single<MainResponse> PayToCreditor(@Field("id")int id,
                                         @Field("amount_of_money")double amountOfMoney,
                                         @Field("updated_date")String createdAt);
    @FormUrlEncoded
    @POST("ReceiveFromDebtor.php")
    Single<MainResponse> receiveFromDebtor(@Field("id")int id,
                                       @Field("amount_of_money")double amountOfMoney,
                                       @Field("updated_date")String createdAt);
    @POST("getAllProducts.php")
    Single<ProductResponse> getAllProducts();

    @POST("getAllDebtorOperations.php")
    Single<DebtorOperationResponse> getAllDebtorOperation();
    @POST("getAllCreditorOperations.php")
    Single<CreditorOperationResponse> getAllCreditorOperation();
    @POST("getAllSales.php")
    Single<SalesOperationResponse> getAllSales();

}
