package com.example.test.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.example.test.Adapter.CategoryAdapter;
import com.example.test.AppDatabase;
import com.example.test.Category;
import com.example.test.CategoryRepository;
import com.example.test.CategoryViewModel;
import com.example.test.MyApplication;
import com.example.test.R;
import com.example.test.databinding.ActivityCategoryBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryActivity extends AppCompatActivity {
List<Category>categoryList=new ArrayList<>();
ActivityCategoryBinding binding;
    CategoryAdapter categoryAdapter;
    AppDatabase appDatabase;
    CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        appDatabase= MyApplication.getInstance().getAppDatabase();
        binding= DataBindingUtil.setContentView(this,R.layout.activity_category);
        CategoryRepository categoryRepository= new CategoryRepository(appDatabase.categoryInterface());
        categoryViewModel=new CategoryViewModel(categoryRepository);

        binding.catrecycler.setHasFixedSize(true);
        binding.catrecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.catrecycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        categoryAdapter=new CategoryAdapter(CategoryActivity.this ,categoryList,appDatabase,categoryViewModel);
        binding.catrecycler.setAdapter(categoryAdapter);

        binding.cataddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CategoryActivity.this, AddCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

             categoryViewModel.getAllCategory(new CategoryRepository.CategoryCallback() {
                 @Override
                 public void onResult(List<Category> categories) {
                     List<Category>categories1=(List<Category>) categories;
                     runOnUiThread(() -> {
                         categoryList.clear();
                         categoryList.addAll(categories1);
                         categoryAdapter.notifyDataSetChanged();
                     });
                 }
             });


    }
}