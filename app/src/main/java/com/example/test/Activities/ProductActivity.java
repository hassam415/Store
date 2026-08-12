package com.example.test.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.example.test.Adapter.ProductAdapter;
import com.example.test.AppDatabase;
import com.example.test.CategoryRepository;
import com.example.test.CategoryViewModel;
import com.example.test.MyApplication;
import com.example.test.Product;
import com.example.test.ProductRepositry;
import com.example.test.ProductViewModel;
import com.example.test.R;
import com.example.test.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ProductAdapter productAdapter;
    AppDatabase productDatabase;
    ProductViewModel productViewModel;
    CategoryViewModel categoryViewModel;
    List<Product> productArrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        productDatabase = MyApplication.getInstance().getAppDatabase();
        ProductRepositry productRepositry=new ProductRepositry(productDatabase.productDAO());
        CategoryRepository categoryRepository= new CategoryRepository(productDatabase.categoryInterface());
        categoryViewModel=new CategoryViewModel(categoryRepository);
        productViewModel=new ProductViewModel(productRepositry);
        productAdapter = new ProductAdapter(this, productArrayList, productDatabase,productViewModel,categoryViewModel,true);
        binding.recyclerView.setAdapter(productAdapter);

        binding.addbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProductActivity.this, ProductAddActivity.class);
              startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        productViewModel.getProduct(new ProductRepositry.ProductCallback() {
            @Override
            public void onResult(List<Product> data) {
                List<Product>productList=(List<Product>)data;
                runOnUiThread(() -> {
                    productArrayList.clear();
                    productArrayList.addAll(productList);
                    productAdapter.notifyDataSetChanged();
                });
            }
        });
    }
}

