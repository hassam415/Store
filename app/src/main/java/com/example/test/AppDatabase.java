package com.example.test;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class, Category.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDAO productDAO();

    public abstract CategoryInterface categoryInterface();


    public static final String DATABASE_NAME = "ApplicationData";
}
