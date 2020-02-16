package com.example.proiect_licenta;
public class ProduseItem {
    private String mProdus;
    private int mIcon;

    public ProduseItem(String numeProdus, int icon) {
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