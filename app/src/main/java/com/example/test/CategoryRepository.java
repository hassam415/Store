package com.example.test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryRepository {

    public CategoryInterface categoryInterface;
    ExecutorService executorService= Executors.newSingleThreadExecutor();

    public CategoryRepository(CategoryInterface categoryInterface) {
        this.categoryInterface = categoryInterface;
    }
    public interface CategoryCallback {
        void onResult(List<Category> categories);
    }

    public void insertCategory(Category category) {
        executorService.execute(() -> {
            categoryInterface.insertCatgory(category);
        });


    }

    public void deleteCategory(Category category) {
        executorService.execute(() -> {
            categoryInterface.deletCatgory(category);
        });

    }

    public void updateCategory(Category category) {
        executorService.execute(() -> {
            categoryInterface.updateCatgory(category);
        });


    }

   public void getAllCategory(CategoryCallback callback){
        executorService.execute(() -> {
            List<Category>categoryList=categoryInterface.getAllCatgories();
            callback.onResult(categoryList);
        });
   }
    public interface CategoryCallback1 {
        void onResult(Category category);
    }
    public void getid(int id, CategoryCallback1 callback) {

        executorService.execute(() -> {

            Category category =
                    categoryInterface.getcategoryid(id);

            callback.onResult(category);
        });
    }


}

