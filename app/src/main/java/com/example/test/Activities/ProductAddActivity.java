package com.example.test.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.core.content.FileProvider;
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

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductAddActivity extends AppCompatActivity {
    Uri imguri;
    int id = -1;
    List<Category> categoryList;
    CategoryViewModel categoryViewModel;
    ActivityProductAddBinding binding;
    ActivityResultLauncher<String[]> galleryLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;

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
        binding.imgView.setOnClickListener(v -> {
            galleryLauncher.launch(new String[]{"image/*"});
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
        binding.camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraLauncher.launch(cameraIntent);
            }
        });
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");

                        binding.imgView.setImageBitmap(imageBitmap);
                        imguri = saveImageToInternalStorage(imageBitmap);
                    }
                }
        );
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                uri -> {
                    if (uri != null) {
                        try {
                            getContentResolver().takePersistableUriPermission(
                                    uri,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        imguri = uri;
                        binding.imgView.setImageURI(imguri);
                    }
                }
        );

    }

    private Uri saveImageToInternalStorage(Bitmap imageBitmap) {
        File folder = new File(getFilesDir(), "images");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String fileName = "IMG_" + System.currentTimeMillis() + ".jpg";
        File file = new File(folder, fileName);

        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(file)) {
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return FileProvider.getUriForFile(
                this,
                getApplicationContext().getPackageName() + ".provider",
                file
        );
    }


}
