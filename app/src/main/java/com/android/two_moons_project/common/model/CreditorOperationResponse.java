package com.android.two_moons_project.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CreditorOperationResponse {
    @SerializedName("total_of_creditor")
    @Expose
    private double total_of_creditor;
    @SerializedName("creditor")
    @Expose
    private List<CreditorOperations> creditorOperations = new ArrayList<>();

    public double getTotal_of_creditor() {
        return total_of_creditor;
    }

    public void setTotal_of_creditor(double total_of_creditor) {
        this.total_of_creditor = total_of_creditor;
    }

    public List<CreditorOperations> getCreditorOperations() {
        return creditorOperations;
    }

    public void setCreditorOperations(List<CreditorOperations> creditorOperations) {
        this.creditorOperations = creditorOperations;
    }
}
