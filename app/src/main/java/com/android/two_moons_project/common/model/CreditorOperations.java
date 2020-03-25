package com.android.two_moons_project.common.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditorOperations {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("create_at")
    @Expose
    private String create_at;
    @SerializedName("the_payment")
    @Expose
    private String the_payment;
    @SerializedName("company_name")
    @Expose
    private String creditor_name;
    @SerializedName("amount_of_money")
    @Expose
    private String amount_of_money;
    @SerializedName("oper_type")
    @Expose
    private int oper_type;
    @SerializedName("remaining")
    @Expose
    private String remaining;

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }

    public int getOper_type() {
        return oper_type;
    }

    public void setOper_type(int oper_type) {
        this.oper_type = oper_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getThe_payment() {
        return the_payment;
    }

    public void setThe_payment(String the_payment) {
        this.the_payment = the_payment;
    }

    public String getCreditor_name() {
        return creditor_name;
    }

    public void setCreditor_name(String creditor_name) {
        this.creditor_name = creditor_name;
    }

    public String getAmount_of_money() {
        return amount_of_money;
    }

    public void setAmount_of_money(String amount_of_money) {
        this.amount_of_money = amount_of_money;
    }
}
