package com.example.proiect_licenta.model;
public class ProductsItem {
    private String mProdus;
    private int mIcon;

    public ProductsItem(String numeProdus, int icon) {
        mProdus = numeProdus;
        mIcon = icon;
    }

    public String getProdus() {
        return mProdus;
    }

    public int getIcon() {
        return mIcon;
    }
}