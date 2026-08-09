package com.example.test.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Activities.AddCategoryActivity;
import com.example.test.AppDatabase;
import com.example.test.Category;
import com.example.test.CategoryViewModel;
import com.example.test.ViewHolder.CategoryViewHolder;
import com.example.test.databinding.CategoryItemBinding;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    Context context;
    List<Category> categories;
    AppDatabase database;

CategoryViewModel categoryViewModel;

    public CategoryAdapter(Context context, List<Category> categories, AppDatabase database, CategoryViewModel categoryViewModel) {
        this.context = context;
        this.categories = categories;
        this.database = database;
        this.categoryViewModel = categoryViewModel;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        CategoryItemBinding binding=CategoryItemBinding.inflate(inflater,parent,false);

        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
    Category category=categories.get(position);
    holder.binding.setCategory(category);
    holder.binding.updateBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
     Intent intent=new Intent(context, AddCategoryActivity.class);
     intent.putExtra("id",category.getId());
     context.startActivity(intent);
    }
});
holder.binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ExecutorService executorService= Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            categoryViewModel.deletCategory(category);
            ((android.app.Activity)context).runOnUiThread(() -> {
                categories.remove(position);
                notifyDataSetChanged();

            });
        });


    }
});
    }



    @Override
    public int getItemCount() {

        return categories.size();
    }
}
