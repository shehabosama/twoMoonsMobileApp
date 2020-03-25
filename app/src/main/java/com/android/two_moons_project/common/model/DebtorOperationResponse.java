package com.android.two_moons_project.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DebtorOperationResponse {
    @SerializedName("total_of_debtor")
    @Expose
    private double total_of_debtor;
    @SerializedName("debtor")
    @Expose
    private List<DebtorsOperations> debtorOperations = new ArrayList<>();

    public double getTotal_of_debtor() {
        return total_of_debtor;
    }

    public void setTotal_of_debtor(double total_of_debtor) {
        this.total_of_debtor = total_of_debtor;
    }

    public List<DebtorsOperations> getDebtorOperations() {
        return debtorOperations;
    }

    public void setDebtorOperations(List<DebtorsOperations> debtorOperations) {
        this.debtorOperations = debtorOperations;
    }
}
