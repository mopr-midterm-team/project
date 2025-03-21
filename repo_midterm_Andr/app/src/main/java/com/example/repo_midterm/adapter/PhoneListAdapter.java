package com.example.repo_midterm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.repo_midterm.R;
import com.example.repo_midterm.model.Phone;


import java.util.List;


//Trần Anh Thư 22110431

public class PhoneListAdapter extends RecyclerView.Adapter<PhoneListAdapter.MyViewHolder> {
    Context context;
    List<Phone> array;

    public PhoneListAdapter(Context context, List<Phone> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull


    // Bổ sung import PhoneDetailAdapter

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Phone phone = array.get(position);
        holder.tenSp.setText(phone.getPhoneName());

        Glide.with(context)
                .load(phone.getImage())
                .into(holder.images);

        holder.itemView.setOnClickListener(v -> {
            if (position != RecyclerView.NO_POSITION) {
                showPhoneDetailDialog(phone);
            }
        });
    }

    private void showPhoneDetailDialog(Phone phone) {
        PhoneDetailAdapter phoneDetailAdapter = new PhoneDetailAdapter(context, phone);
        phoneDetailAdapter.showPhoneDetail();
    }




    @Override
    public int getItemCount() {
        return array == null ? 0: array.size() ;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_phone_detail, null);
        MyViewHolder myViewHolder =new MyViewHolder(view);
        return myViewHolder;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView images;
       public TextView tenSp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            images = (ImageView) itemView.findViewById(R.id.image_phone);
            tenSp= itemView.findViewById(R.id.tvNamePhone);

        }
    }

    public void updateList(List<Phone> newList) {
        this.array = newList;
        notifyDataSetChanged();
    }
}



