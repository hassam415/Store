package com.example.test;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao

public interface CustomerDAO {
    @Query("select * from customer")
    List<Customer>getAllCustomer();
    @Query("select * from customer Where id =:id")
    Customer getCustomerid(int id);

    @Insert
    void insertCustomer(Customer customer);
    @Update
    void updateCustomer(Customer customer);
    @Delete
    void deletCustomer(Customer customer);


}
