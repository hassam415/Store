package com.example.test;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao

public interface CategoryInterface {
    @Query("select * from category")
    List<Category>getAllCatgories();
    @Query("SELECT * FROM category Where id =:id")
    Category getcategoryid(int id);
    @Insert
    void insertCatgory(Category category);
    @Update
    void updateCatgory(Category category);
    @Delete
    void deletCatgory(Category category);

}
