package com.example.test.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.AppDatabase;
import com.example.test.CategoryViewModel;
import com.example.test.Customer;
import com.example.test.CustomerAddActivity;
import com.example.test.CustomerViewModel;
import com.example.test.OnCustomerClick;
import com.example.test.OrderActivity;
import com.example.test.ViewHolder.CustomerViewHolder;
import com.example.test.databinding.CustomerItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerViewHolder> {
    Context context;
    List<Customer>customerList;

    AppDatabase appDatabase;
    CustomerViewModel customerViewModel;
    Boolean showAction;



    public void setCustomerClick(OnCustomerClick customerClick) {
        this.customerClick = customerClick;
    }

    OnCustomerClick customerClick;


    public CustomerAdapter(Context context, List<Customer> customerList, AppDatabase appDatabase, CustomerViewModel customerViewModel, Boolean showAction) {
        this.context = context;
        this.customerList = customerList;
        this.appDatabase = appDatabase;
        this.customerViewModel = customerViewModel;
        this.showAction = showAction;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        CustomerItemBinding  binding=CustomerItemBinding.inflate(inflater,parent,false);
        return new CustomerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer=customerList.get(position);
        holder.binding.setCustomer(customer);
        Picasso.get().load(customer.getImage()).into(holder.binding.imgView);

holder.binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        customerViewModel.deletCustomer(customer);
        ((android.app.Activity)context).runOnUiThread(() -> {

            customerList.remove(customer);
            notifyDataSetChanged();
            Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
        });
    }
});
holder.binding.updatBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(context, CustomerAddActivity.class);
        intent.putExtra("id",customer.getId());
        context.startActivity(intent);
    }
});
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        customerClick.onClick(position);
    }
});
if (showAction){
    holder.binding.updatBtn.setVisibility(View.VISIBLE);
    holder.binding.deleteBtn.setVisibility(View.VISIBLE);
}else {
    holder.binding.updatBtn.setVisibility(View.INVISIBLE);
    holder.binding.deleteBtn.setVisibility(View.INVISIBLE);
}
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }
}
