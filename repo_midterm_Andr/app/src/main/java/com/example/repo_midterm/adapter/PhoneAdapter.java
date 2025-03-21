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

//Nguyễn Văn Hoài - 22110327
public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ViewHolder> {
    private Context context;
    private List<Phone> phoneList;

    public PhoneAdapter(Context context, List<Phone> phoneList) {
        this.context = context;
        this.phoneList = phoneList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_phone, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Phone phone = phoneList.get(position);
        holder.tvName.setText(phone.getPhoneName());
        holder.tvPrice.setText(String.format("$ %.2f", phone.getPrice()));

        Glide.with(context)
                .load(phone.getImage())
                .into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return phoneList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName, tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.image_phone);
            tvName = itemView.findViewById(R.id.tvPhoneName);
            tvPrice = itemView.findViewById(R.id.tvPhonePrice);
        }
    }
}
