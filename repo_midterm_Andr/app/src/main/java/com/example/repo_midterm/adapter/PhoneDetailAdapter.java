package com.example.repo_midterm.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.repo_midterm.R;
import com.example.repo_midterm.model.Phone;

//Trần Anh Thư 22110431

public class PhoneDetailAdapter {
    private Context context;
    private Phone phone;

    public PhoneDetailAdapter(Context context, Phone phone) {
        this.context = context;
        this.phone = phone;
    }

    public void showPhoneDetail() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_detail_phone);

        ImageView imageView = dialog.findViewById(R.id.imagePhoneDetail);
        TextView txtName = dialog.findViewById(R.id.tvPhoneNameDetail);
        TextView txtDescription = dialog.findViewById(R.id.tvPhoneDescriptionDetail);
        TextView txtPrice = dialog.findViewById(R.id.tvPhonePriceDetail);
        Button btnClose = dialog.findViewById(R.id.btnClose);

        txtName.setText(phone.getPhoneName());
        txtDescription.setText(phone.getDescription());
        txtPrice.setText(String.format("%s VND", phone.getPrice()));
        Glide.with(context)
                .load(phone.getImage())
                .into(imageView);

        btnClose.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}
