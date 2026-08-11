package com.example.test;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "Customer")
public class Customer {
    @PrimaryKey (autoGenerate = true)
     public int id;

    public Customer() {
    }

    public Customer(int id, String name, String phoneNumber, String address, String image) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.image = image;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



      String name,phoneNumber,address,image;

}
