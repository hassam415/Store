package com.example.test;

import java.util.List;

public class ProductViewModel {
    private ProductRepositry productRepositry;


    public ProductViewModel(ProductRepositry productRepositry) {
        this.productRepositry = productRepositry;
    }

    public void getProduct(ProductRepositry.ProductCallback callback){
         productRepositry.getProduct(callback);

    }
    public void getProductid(int id, ProductRepositry.ProductidCallback callback){

        productRepositry.getProductid(id,callback);

    }
    public void insertProduct(Product product){
        productRepositry.insertProduct(product);

    }
    public void updateProduct(Product product){
        productRepositry.updateProduct(product);

    }
    public void deleteProduct(Product product){
        productRepositry.deletProduct(product);

    }

}
