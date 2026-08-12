package com.example.test.Activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.test.Adapter.ProductAdapter;
import com.example.test.Adapter.ProductListAdapter;
import com.example.test.AppDatabase;
import com.example.test.CategoryRepository;
import com.example.test.CategoryViewModel;
import com.example.test.MyApplication;
import com.example.test.OnAdapterclick;
import com.example.test.Product;
import com.example.test.ProductRepositry;
import com.example.test.ProductViewModel;
import com.example.test.R;
import com.example.test.databinding.ActivityProductListBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
public ActivityProductListBinding binding;
AppDatabase appDatabase;
ProductListAdapter productAdapter;
ProductViewModel productViewModel;
List<Product> productList=new ArrayList<>();
   public double grandTotal=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_product_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        productViewModel=new ProductViewModel(new ProductRepositry(MyApplication.getInstance().getAppDatabase().productDAO()));

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
productAdapter=new ProductListAdapter(this,productList,this);

binding.grandtotal.setText("Total :");
        binding.recyclerView.setAdapter(productAdapter);
        productViewModel.getProduct(new ProductRepositry.ProductCallback() {
            @Override
            public void onResult(List<Product> data) {
                List<Product>products=(List<Product>) data;
                runOnUiThread(() -> {
                    productList.addAll(products);
                    productAdapter.notifyDataSetChanged();
                });
            }
        });


    }
}