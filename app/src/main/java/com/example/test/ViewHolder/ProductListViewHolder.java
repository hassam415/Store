package com.example.test.ViewHolder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.databinding.ItemProductlistBinding;

public class ProductListViewHolder extends RecyclerView.ViewHolder {
    public ItemProductlistBinding binding;
    public ProductListViewHolder(ItemProductlistBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }
}
