package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.test.Adapter.CustomerAdapter;
import com.example.test.databinding.ActivityCustomerBinding;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {
ActivityCustomerBinding binding;
List<Customer>customerList=new ArrayList<>();
CustomerViewModel customerViewModel;
AppDatabase appDatabase;
CustomerAdapter customerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_customer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
      appDatabase=MyApplication.getInstance().getAppDatabase();
      CustomerRepository customerRepository=new CustomerRepository(appDatabase.customerDAO());
        customerViewModel=new CustomerViewModel(customerRepository);
      binding.customerrecyler.setHasFixedSize(true);
      binding.customerrecyler.setLayoutManager(new LinearLayoutManager(CustomerActivity.this));
      customerAdapter=new CustomerAdapter(this,customerList,appDatabase,customerViewModel);
      binding.customerrecyler.setAdapter(customerAdapter);
        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CustomerActivity.this, CustomerAddActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        customerViewModel.getAllCustomer(new CustomerRepository.CustomerCallBack() {
            @Override
            public void onResult(List<Customer> customers) {
                List<Customer>customer=(List<Customer>) customers;
                runOnUiThread(() -> {
                    customerList.clear();
                    customerList.addAll(customer);
                    customerAdapter.notifyDataSetChanged();

                });
            }
        });
    }
}