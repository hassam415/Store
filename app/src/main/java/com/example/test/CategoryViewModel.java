package com.example.test;

public class CategoryViewModel {

    CategoryRepository categoryRepository;

    public CategoryViewModel(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void getid(int id, CategoryRepository.CategoryCallback1 callback) {
        categoryRepository.getid(id, callback);
    }

    public void getAllCategory(CategoryRepository.CategoryCallback callback) {
        categoryRepository.getAllCategory(callback);
    }

    public void insertCategory(Category category) {
        categoryRepository.insertCategory(category);
    }

    public void updateCategory(Category category) {
        categoryRepository.updateCategory(category);
    }

    public void deletCategory(Category category) {
        categoryRepository.deleteCategory(category);
    }
}