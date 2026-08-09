package com.example.test;

import android.content.Context;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.Update;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepositry {
   public ProductDAO productDAO;
ExecutorService executorService=Executors.newSingleThreadExecutor();
    public ProductRepositry(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }


   public void insertProduct(Product product){
        executorService.execute(() -> {
            productDAO.insertproduct(product);
        });

    }
    public void getProduct(ProductCallback callback){

        executorService.execute(() -> {

            List<Product> list = productDAO.getproduct();

            callback.onResult(list);

        });

    }


    public void getProductid(int id, ProductidCallback callback){

        executorService.execute(() -> {

            Product product = productDAO.getproductid(id);

            callback.onResult(product);

        });

    }

public interface ProductidCallback{
        void onResult(Product product);
}
    public interface ProductCallback{

        void onResult(List<Product> data);

    }
    public void updateProduct(Product product){
        executorService.execute(() -> {
            productDAO.updateproduct(product);
        });



    }
    public void deletProduct(Product product){
        executorService.execute(() -> {
            productDAO.deletproduct(product);
        });

    }
}

