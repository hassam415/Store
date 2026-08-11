package com.example.test.ViewHolder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.databinding.CustomerItemBinding;

public class CustomerViewHolder extends RecyclerView.ViewHolder {
    public CustomerItemBinding binding;
    public CustomerViewHolder(CustomerItemBinding binding) {

        super(binding.getRoot());
        this.binding=binding;
    }
}
