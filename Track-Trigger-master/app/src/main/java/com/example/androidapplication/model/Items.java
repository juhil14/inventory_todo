package com.example.androidapplication.model;

import java.util.Objects;

public class Items {
    private String itemname;
    private String itemcategory;
    private int itemqty;

    public Items(){
        itemname = "test";
        itemcategory = "test";
        itemqty = 1;
    }

    public Items(String itemname, String itemcategory, int itemqty) {
        this.itemname = itemname;
        this.itemcategory = itemcategory;
        this.itemqty = itemqty;
    }

    public int getItemqty() {
        return itemqty;
    }

    public void setItemqty(int itemqty) {
        this.itemqty = itemqty;
    }

    public String getItemcategory() {
        return itemcategory;
    }

    public void setItemcategory(String itemcategory) {
        this.itemcategory = itemcategory;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Items items = (Items) o;
        return itemname.equals(items.itemname) &&
                itemcategory.equals(items.itemcategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemname, itemcategory);
    }
}
