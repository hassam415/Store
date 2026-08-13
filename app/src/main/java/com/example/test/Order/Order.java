package com.example.test;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "order")
public class Order {
    @PrimaryKey(autoGenerate = true)
    public int id;
     public int customer_id,date,time;

    public Order() {
    }

    public Order(int id, int customer_id, int date, int time) {
        this.id = id;
        this.customer_id = customer_id;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
