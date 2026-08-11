package com.example.test;

import static com.example.test.BR.customer;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.example.test.databinding.ActivityCustomerAddBinding;
import com.squareup.picasso.Picasso;

import java.io.File;

public class CustomerAddActivity extends AppCompatActivity {
ActivityCustomerAddBinding binding;
Uri imguri;
int id =-1;
    private ActivityResultLauncher<Intent> cameraLauncher;

ActivityResultLauncher<String[]> resultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_customer_add);
        binding= DataBindingUtil.setContentView(this ,R.layout.activity_customer_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        CustomerViewModel customerViewModel=new CustomerViewModel(new CustomerRepository(MyApplication.getInstance().getAppDatabase().customerDAO()));
         id=getIntent().getIntExtra("id",-1);
        if (id !=-1){
            customerViewModel.getid(id, new CustomerRepository.CustomeridCallBack() {
                @Override
                public void onResult(Customer customer) {
                    Customer customer1=(Customer) customer;
                    runOnUiThread(() -> {

                        binding.edtName.getEditText().setText(customer1.getName());
                        binding.edtphone.getEditText().setText(customer1.getPhoneNumber());
                        binding.edtaddress.getEditText().setText(customer1.getAddress());
                        if (customer1.getImage() != null &&
                                !customer1.getImage().isEmpty()) {

                            imguri = Uri.parse(customer1.getImage());

                            Picasso.get()
                                    .load(customer1.getImage())
                                    .into(binding.imgview);
                        }


                    });
                }
            });
        }


        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imguri == null){
                    Toast.makeText(CustomerAddActivity.this, " Select the image", Toast.LENGTH_SHORT).show();}
                else {

                    String name = binding.edtName.getEditText().getText().toString();
                    String phone = binding.edtphone.getEditText().getText().toString();
                    String address = binding.edtaddress.getEditText().getText().toString();
                    Customer customer = new Customer();
                    customer.setName(name);
                    customer.setPhoneNumber(phone);
                    customer.setAddress(address);
                    customer.setImage(imguri.toString());
                    if (id != -1) {
                        customer.setId(id);
                    }
                    if (id == -1) {
                        customerViewModel.insertCustomer(customer);
                        runOnUiThread(() -> {
                            Toast.makeText(CustomerAddActivity.this, "Added", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    } else {

                        customerViewModel.updateCustomer(customer);
                        runOnUiThread(() -> {
                            Toast.makeText(CustomerAddActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                    }
                }

            }
        });
        binding.imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultLauncher.launch(new String[]{"image/*"});

            }
        });

        binding.camerabtn.setOnClickListener(v -> {

            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(cameraIntent);
        });
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");

                        binding.imgview.setImageBitmap(imageBitmap);
                        imguri = saveImageToInternalStorage(imageBitmap);
                    }
                }
        );
        resultLauncher = registerForActivityResult(
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
                        binding.imgview.setImageURI(imguri);
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

