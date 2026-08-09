package com.example.test.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.example.test.AppDatabase;
import com.example.test.Category;
import com.example.test.CategoryRepository;
import com.example.test.CategoryViewModel;
import com.example.test.MyApplication;
import com.example.test.R;
import com.example.test.databinding.ActivityAddCategoryBinding;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddCategoryActivity extends AppCompatActivity {
    ActivityAddCategoryBinding binding;
AppDatabase appDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        appDatabase= MyApplication.getInstance().getAppDatabase();
        CategoryRepository categoryRepository=new CategoryRepository(appDatabase.categoryInterface());
        CategoryViewModel categoryViewModel=new CategoryViewModel(categoryRepository);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_category);
        int id = getIntent().getIntExtra("id", -1);

        if (id != -1) {

            categoryViewModel.getid(id, new CategoryRepository.CategoryCallback1() {
                @Override
                public void onResult(Category category) {
                    Category category1 = (Category) category;
                    runOnUiThread(() -> {
                        binding.catName.getEditText().setText(category1.getName());
                    });
                }
            });

        }
        binding.addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.catName.getEditText().getText().toString();
                if (id == -1) {
                    addData(name,categoryViewModel);

                } else {
                    updatecategory(id, name,categoryViewModel);

                }

            }
        });
    }

    private void updatecategory(int id, String name, CategoryViewModel categoryViewModel) {

        Category category = new Category();
        category.setId(id);
        category.setName(name);
        categoryViewModel.updateCategory(category);
        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
        finish();


    }

    private void addData(String name, CategoryViewModel categoryViewModel) {
        Category category = new Category();
        category.setName(name);
        categoryViewModel.insertCategory(category);
        Toast.makeText(this, "Data Added", Toast.LENGTH_SHORT).show();
        finish();


    }
}