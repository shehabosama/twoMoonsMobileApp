package com.android.two_moons_project.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CreditorResponse {
    @SerializedName("creditors")
    @Expose
    private List<Creditor> creditors =new ArrayList<>();

    public List<Creditor> getCreditors() {
        return creditors;
    }

    public void setCreditors(List<Creditor> creditors) {
        this.creditors = creditors;
    }
}
