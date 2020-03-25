package com.android.two_moons_project.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SalesOperationResponse {
    @SerializedName("total_of_sales")
    @Expose
    private double total_of_sales;
    @SerializedName("sales")
    @Expose
    private List<SalesOperations> salesOperations = new ArrayList<>();

    public double getTotal_of_sales() {
        return total_of_sales;
    }

    public void setTotal_of_sales(double total_of_sales) {
        this.total_of_sales = total_of_sales;
    }

    public List<SalesOperations> getSalesOperations() {
        return salesOperations;
    }

    public void setSalesOperations(List<SalesOperations> salesOperations) {
        this.salesOperations = salesOperations;
    }
}
