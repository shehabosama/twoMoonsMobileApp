package com.android.two_moons_project.common.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
public class ProductResponse {
    @SerializedName("total_price")
    @Expose
    private Integer totalPrice;
    @SerializedName("total_org_price")
    @Expose
    private Integer totalOrgPrice;
    @SerializedName("difference")
    @Expose
    private Integer difference;
    @SerializedName("total_of_creditors")
    @Expose
    private String totalOfCreditors;
    @SerializedName("total_of_debtor")
    @Expose
    private String totalOfDebtor;
    @SerializedName("total_sales")
    @Expose
    private String totalSales;
    @SerializedName("products")
    @Expose
    private List<Products> products = null;
    
    public Integer getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalOrgPrice() {
        return totalOrgPrice;
    }

    public void setTotalOrgPrice(Integer totalOrgPrice) {
        this.totalOrgPrice = totalOrgPrice;
    }

    public Integer getDifference() {
        return difference;
    }

    public void setDifference(Integer difference) {
        this.difference = difference;
    }

    public String getTotalOfCreditors() {
        return totalOfCreditors;
    }

    public void setTotalOfCreditors(String totalOfCreditors) {
        this.totalOfCreditors = totalOfCreditors;
    }

    public String getTotalOfDebtor() {
        return totalOfDebtor;
    }

    public void setTotalOfDebtor(String totalOfDebtor) {
        this.totalOfDebtor = totalOfDebtor;
    }

    public String getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(String totalSales) {
        this.totalSales = totalSales;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
