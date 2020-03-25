package com.android.two_moons_project.common.model.QrCodeModel;

public class Product {
    private String productName;
    private double price;
    private double orgPrice;
    private int count;
    private String createAt;
    private double totalOfPrice;
    private double totalOfOrgPrice;


    public Product(String productName, double price, double orgPrice, int count, String createAt,double totalOfOrgPrice,double totalOfPrice) {
        this.productName = productName;
        this.price = price;
        this.orgPrice = orgPrice;
        this.count = count;
        this.createAt = createAt;
        this.totalOfOrgPrice = totalOfOrgPrice;
        this.totalOfPrice = totalOfPrice;

    }

    public double getTotalOfPrice() {
        return totalOfPrice;
    }

    public double getTotalOfOrgPrice() {
        return totalOfOrgPrice;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public double getOrgPrice() {
        return orgPrice;
    }

    public int getCount() {
        return count;
    }

    public String getCreateAt() {
        return createAt;
    }


}
