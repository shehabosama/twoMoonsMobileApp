package com.android.two_moons_project.common.model.QrCodeModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductQr {
    @SerializedName("org_price")
    @Expose
    private double org_price;
    @SerializedName("price")
    @Expose
    private double price;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getOrg_price() {
        return org_price;
    }

    public void setOrg_price(double org_price) {
        this.org_price = org_price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
