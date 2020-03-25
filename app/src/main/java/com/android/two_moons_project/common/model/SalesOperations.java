package com.android.two_moons_project.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SalesOperations {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("product_id")
    @Expose
    private String product_id;
    @SerializedName("price_of_product")
    @Expose
    private String price_of_product;
    @SerializedName("date_of_process")
    @Expose
    private String date_of_process;
    @SerializedName("product_name")
    @Expose
    private String product_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getPrice_of_product() {
        return price_of_product;
    }

    public void setPrice_of_product(String price_of_product) {
        this.price_of_product = price_of_product;
    }

    public String getDate_of_process() {
        return date_of_process;
    }

    public void setDate_of_process(String date_of_process) {
        this.date_of_process = date_of_process;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
