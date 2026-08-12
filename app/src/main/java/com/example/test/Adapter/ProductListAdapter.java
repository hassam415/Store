package com.example.test.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Activities.ProductListActivity;
import com.example.test.Product;
import com.example.test.ViewHolder.ProductListViewHolder;
import com.example.test.databinding.ItemProductlistBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListViewHolder> {
    Context context;
List<Product>productList;
    ProductListActivity activity;
    public ProductListAdapter(Context context, List<Product> productList, ProductListActivity activity) {
        this.context = context;
        this.productList = productList;
        this.activity = activity;
    }




    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        ItemProductlistBinding binding=ItemProductlistBinding.inflate(inflater,parent,false);

        return new ProductListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {
Product product=productList.get(position);
holder.binding.productname.setText(product.getName());
holder.binding.productprice.setText(product.getPrice());
holder.binding.productdetail.setText(product.getDetail());
        Picasso.get().load(product.getImage()).into(holder.binding.ImageView);
        int quantity1=0;
        holder.binding.quantityText.setText(String.valueOf(quantity1));
        double price=Double.parseDouble(product.getPrice());

        double subtotal1=price*quantity1;
        holder.binding.subTotal.setText("Subttotal:"+subtotal1);

        holder.binding.addButton.setOnClickListener(v -> {

            String text = holder.binding.quantityText.getText().toString().trim();

            int quantity = 1;

            if (!text.isEmpty()) {
                quantity = Integer.parseInt(text);
            }

            quantity++;

            holder.binding.quantityText.setText(String.valueOf(quantity));

            double price1 = Double.parseDouble(product.getPrice());

            double subtotal = price1 * quantity;

            holder.binding.subTotal.setText(
                    "Subtotal: " + subtotal
            );
            activity.grandTotal +=price1;
            activity.binding.grandtotal.setText("Total :"+activity.grandTotal);
        });
        holder.binding.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=holder.binding.quantityText.getText().toString().trim();
                int quantity=1;
                if (!text.isEmpty()){
                    quantity=Integer.parseInt(text);
                }
                if (quantity>0){
                    quantity--;
                    holder.binding.quantityText.setText(String.valueOf(quantity));
                    double price=Double.parseDouble(product.getPrice());

                    double subprice=price*quantity;
                    holder.binding.subTotal.setText("Subtotal:"+subprice);
                }
                activity.grandTotal -=price;
                activity.binding.grandtotal.setText("Total :"+activity.grandTotal);
            }
        });
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }
}
