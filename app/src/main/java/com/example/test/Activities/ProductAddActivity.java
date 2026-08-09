package com.example.test.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.room.Room;

import com.example.test.AppDatabase;
import com.example.test.Category;
import com.example.test.CategoryRepository;
import com.example.test.CategoryViewModel;
import com.example.test.MyApplication;
import com.example.test.Product;
import com.example.test.ProductRepositry;
import com.example.test.ProductViewModel;
import com.example.test.R;
import com.example.test.databinding.ActivityProductAddBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductAddActivity extends AppCompatActivity {
    Uri imguri;
    int id = -1;
    List<Category> categoryList;
    CategoryViewModel categoryViewModel;
    ActivityProductAddBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_add);
        Toolbar toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        categoryViewModel = new CategoryViewModel(
                new CategoryRepository(
                        MyApplication.getInstance().getAppDatabase().categoryInterface()
                )
        );

        ProductViewModel productViewModel = new ProductViewModel(new ProductRepositry( MyApplication.getInstance().getAppDatabase().productDAO()));
        id = getIntent().getIntExtra("id", -1);

        if (id != -1) {

            productViewModel.getProductid(id, new ProductRepositry.ProductidCallback() {

                @Override
                public void onResult(Product product) {
                    Product  product1=(Product) product;
                    runOnUiThread(() -> {
                        if (product1 != null) {

                            binding.edtName.getEditText().setText(product1.getName());

                            binding.edtPrice.getEditText().setText(product1.getPrice());

                            binding.edtDetail.getEditText().setText(product1.getDetail());

                            if (product1.getImage() != null &&
                                    !product1.getImage().isEmpty()) {

                                imguri = Uri.parse(product1.getImage());

                                Picasso.get()
                                        .load(product1.getImage())
                                        .into(binding.imgView);
                            }

                            binding.addbtn.setText("Update");
                        }
                    });
                }


            });
        }


           categoryViewModel.getAllCategory(new CategoryRepository.CategoryCallback() {
                @Override
                public void onResult(List<Category> categories) {

                    runOnUiThread(() -> {
                    categoryList=categories;

                        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(ProductAddActivity.this, android.R.layout.simple_spinner_item, categoryList);
                        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        binding.CategorySpinner.setAdapter(categoryArrayAdapter);
                    });
                }
            });
       binding.imgView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ImagePicker.with(ProductAddActivity.this)
                       .crop()
                       .compress(1024)
                       .maxResultSize(1080, 1080)
                       .start();
           }
       });

        binding.addbtn.setOnClickListener(v -> {
            if (imguri == null) {
                Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
                finish();
            }
            String name = binding.edtName.getEditText().getText().toString().trim();
            String price = binding.edtPrice.getEditText().getText().toString().trim();
            String detail = binding.edtDetail.getEditText().getText().toString().trim();

            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setDetail(detail);
            if (id != -1) {
                product.setId(id);
            }


            Category selectedcategory = (Category) binding.CategorySpinner.getSelectedItem();
            product.setCategoryid(selectedcategory.getId());
            if (imguri != null) {
                product.setImage(imguri.toString());
            } else {
                product.setImage("");
            }

            if (id == -1) {

                    productViewModel.insertProduct(product);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Data Added", Toast.LENGTH_SHORT).show();
                        finish();
                    });


            } else {

                    productViewModel.updateProduct(product);
                    runOnUiThread(() -> {
                        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                        finish();
                    });

            }

        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {

            imguri = data.getData();

            binding.imgView.setImageURI(imguri);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {

            Toast.makeText(
                    this,
                    ImagePicker.getError(data),
                    Toast.LENGTH_SHORT
            ).show();
        }

    }
    }