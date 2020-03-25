package com.android.two_moons_project.common.model;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;

import com.android.two_moons_project.common.base.BaseView;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Debtor  {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("debtor_name")
    @Expose
    private String name;
    @SerializedName("amount_of_money")
    @Expose
    private double amountOfMoney;
    @SerializedName("create_at")
    @Expose
    private String createdAt;

    public Debtor( String name, double amountOfMoney, String createdAt) {
            this.name = name;
            this.amountOfMoney = amountOfMoney;
            this.createdAt = createdAt;

    }

    public int getId() {
      return id;
    }

    public String getName() {
        return name;
    }

    public double getAmountOfMoney() {
        return amountOfMoney;
    }

    public String getCreatedAt() {
            return createdAt;
        }

}
