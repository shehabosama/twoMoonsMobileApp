package com.android.two_moons_project.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DebtorResponse {
    @SerializedName("debtor")
    @Expose
    private List<Debtor> debtors = new ArrayList<>();

    public List<Debtor> getDebtors() {
        return debtors;
    }

    public void setDebtors(List<Debtor> debtors) {
        this.debtors = debtors;
    }
}
