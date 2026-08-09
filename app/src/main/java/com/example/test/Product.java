package com.example.test;

import android.widget.TableLayout;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product")
public class Product {
    @PrimaryKey(autoGenerate = true)
       public      int id;

    public Product(int id, String name, String price, String image, String detail) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.detail = detail;
    }

    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String name,price,image,detail;
    private int categoryid;

    public Product(int id, String name, String price, String detail, String image, int categoryid) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.image = image;
        this.categoryid = categoryid;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


}
