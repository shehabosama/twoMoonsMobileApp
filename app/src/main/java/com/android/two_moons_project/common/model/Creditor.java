package com.android.two_moons_project.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Creditor {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("creditor_name")
    @Expose
    private String name;
    @SerializedName("amount_of_money")
    @Expose
    private double amountOfMoney;
    @SerializedName("create_at")
    @Expose
    private String createdAt;

    public Creditor(String name, double amountOfMoney, String createdAt) {
        this.name = name;
        this.amountOfMoney = amountOfMoney;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(double amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
