package com.android.two_moons_project.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Products {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_price")
    @Expose
    private String productPrice;
    @SerializedName("product_count")
    @Expose
    private String productCount;
    @SerializedName("product_orginal_price")
    @Expose
    private String productOrginalPrice;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("ceate_at")
    @Expose
    private String ceateAt;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("total_org_price")
    @Expose
    private String totalOrgPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }

    public String getProductOrginalPrice() {
        return productOrginalPrice;
    }

    public void setProductOrginalPrice(String productOrginalPrice) {
        this.productOrginalPrice = productOrginalPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getCeateAt() {
        return ceateAt;
    }

    public void setCeateAt(String ceateAt) {
        this.ceateAt = ceateAt;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalOrgPrice() {
        return totalOrgPrice;
    }

    public void setTotalOrgPrice(String totalOrgPrice) {
        this.totalOrgPrice = totalOrgPrice;
    }
}
