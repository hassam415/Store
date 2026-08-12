package com.example.test.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Activities.ProductActivity;
import com.example.test.Activities.ProductListActivity;
import com.example.test.AppDatabase;
import com.example.test.Category;
import com.example.test.CategoryRepository;
import com.example.test.CategoryViewModel;
import com.example.test.OnAdapterclick;
import com.example.test.Product;
import com.example.test.Activities.ProductAddActivity;
import com.example.test.ProductViewModel;
import com.example.test.ViewHolder.ProductViewHolder;
import com.example.test.databinding.ItemProductBinding;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    Context context;
    List<Product>productList;
    AppDatabase productDatabase;
ProductViewModel productViewModel;

CategoryViewModel categoryViewModel;
OnAdapterclick onAdapterclick;

   //public void setOnAdapterclick(OnAdapterclick onAdapterclick) {
     //   this.onAdapterclick = onAdapterclick;
  //  }

    boolean showIcon;

    public ProductAdapter(Context context, List<Product> productList, AppDatabase productDatabase, ProductViewModel productViewModel, CategoryViewModel categoryViewModel, boolean showIcon) {
        this.context = context;
        this.productList = productList;
        this.productDatabase = productDatabase;
        this.productViewModel = productViewModel;
        this.categoryViewModel = categoryViewModel;
        this.showIcon = showIcon;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        ItemProductBinding binding=ItemProductBinding.inflate(inflater,parent,false);

        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Product product=productList.get(position);
        holder.binding.setProduct(product);
        Picasso.get().load(product.getImage()).into(holder.binding.ImageView);
        categoryViewModel.getid(product.getCategoryid(), new CategoryRepository.CategoryCallback1() {
            @Override
            public void onResult(Category category) {
                Category  category1=(Category) category;
                ((android.app.Activity)context).runOnUiThread(() -> {
                    if (category1 != null) {
                        holder.binding.textCategory.setText(category1.getName());
                    } else {
                     holder.binding.textCategory.setText("No Category");
                    }
                });
            }
        });
   if (showIcon){
    holder.binding.updatBtn.setVisibility(View.VISIBLE);
    holder.binding.deleteBtn.setVisibility(View.VISIBLE);
   }else {
    holder.binding.updatBtn.setVisibility(View.INVISIBLE);
    holder.binding.deleteBtn.setVisibility(View.INVISIBLE);
   }







holder.binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

            productViewModel.deleteProduct(product);
            ((android.app.Activity)context).runOnUiThread(() -> {
                productList.remove(product);
                notifyDataSetChanged();
            });




    }
});
holder.binding.updatBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       Intent  intent=new Intent(context, ProductAddActivity.class);
       intent.putExtra("id",product.getId());
context.startActivity(intent);

    }
});
   // holder.itemView.setOnClickListener(new View.OnClickListener() {
    //  @Override
    //  public void onClick(View v) {
     // onAdapterclick.Onclick(position);
   //  }
   //});

    }



    @Override
    public int getItemCount() {
        return  productList.size();
    }
}
