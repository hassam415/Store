package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Activities.ProductListActivity;
import com.example.test.Adapter.CustomerAdapter;
import com.example.test.databinding.ActivityOrderBinding;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
CustomerViewModel customerViewModel;
List<Customer>customerArrayList=new ArrayList<>();
CustomerAdapter  customerAdapter;
    ActivityOrderBinding binding;
    AppDatabase appDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
        binding.recyclerView.setHasFixedSize(true);
        customerAdapter=new CustomerAdapter(this,customerArrayList,appDatabase,customerViewModel,false);
        binding.recyclerView.setAdapter(customerAdapter);
        customerViewModel=new CustomerViewModel(new CustomerRepository(MyApplication.getInstance().getAppDatabase().customerDAO() ));
        customerViewModel.getAllCustomer(new CustomerRepository.CustomerCallBack() {
            @Override
            public void onResult(List<Customer> customers) {
                List<Customer>customerList=(List<Customer>)customers;
                runOnUiThread(() -> {
                    customerArrayList.addAll(customerList);
                    customerAdapter.notifyDataSetChanged();


                });
            }
        });
      customerAdapter.setCustomerClick(new OnCustomerClick() {
          @Override
          public void onClick(int position) {
Intent intent=new Intent(OrderActivity.this,ProductListActivity.class);
startActivity(intent);
          }
      });

    }
}