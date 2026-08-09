package com.example.test.ViewHolder;

import androidx.recyclerview.widget.RecyclerView;

import com.example.test.databinding.CategoryItemBinding;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    public CategoryItemBinding  binding;
    public CategoryViewHolder(CategoryItemBinding category) {
        super(category.getRoot());
        this.binding=category;

    }
}
