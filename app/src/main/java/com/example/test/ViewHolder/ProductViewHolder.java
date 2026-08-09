package com.example.test.ViewHolder;

import androidx.recyclerview.widget.RecyclerView;

import com.example.test.databinding.ItemProductBinding;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    public ItemProductBinding binding;
    public ProductViewHolder(ItemProductBinding binding) {
        super(binding.getRoot());
        this.binding=binding;

    }
}
