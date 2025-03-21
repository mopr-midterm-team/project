package com.example.retofit2.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retofit2.R;
import com.example.retofit2.model.Category;
import com.example.retofit2.model.Phone;

import java.util.List;

public class PhoneAdapter  extends RecyclerView.Adapter<PhoneAdapter.MyViewHolder> {
    Context context;
    List<Phone> array;

    public PhoneAdapter(Context context, List<Phone> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull


    // Bổ sung import PhoneDetailAdapter

    @Override
    public void onBindViewHolder(@NonNull PhoneAdapter.MyViewHolder holder, int position) {
        Phone phone = array.get(position);
        holder.tenSp.setText(phone.getPhoneName());

        String base64Image = phone.getImagePhone();
        if (base64Image != null && !base64Image.isEmpty()) {
            try {
                if (base64Image.contains(",")) {
                    base64Image = base64Image.split(",")[1];
                }

                byte[] decodedString = android.util.Base64.decode(base64Image, android.util.Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                Glide.with(context).load(decodedBitmap).into(holder.images);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

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
    public PhoneAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_phone, null);
        PhoneAdapter.MyViewHolder myViewHolder =new PhoneAdapter.MyViewHolder(view);
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

}



