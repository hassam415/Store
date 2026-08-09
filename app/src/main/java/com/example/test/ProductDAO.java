package com.example.test;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao

public interface ProductDAO {

    @Query("SELECT * FROM  product")
    List<Product>getproduct();
    @Query("SELECT * FROM product Where id =:id")
    Product getproductid( int id);

    @Insert
    void insertproduct(Product product);
    @Delete
    void deletproduct(Product product);
    @Update
    void  updateproduct(Product product);

}
