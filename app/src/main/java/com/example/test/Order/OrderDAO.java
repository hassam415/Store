package com.example.test;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OrderDAO {

    @Query("Select * From order")
    List<Order> getAllorder();
    @Query("Select * From order Where id=:id")
    Order getOrderId();
    @Insert
    void insertOrder(Order order);


}
