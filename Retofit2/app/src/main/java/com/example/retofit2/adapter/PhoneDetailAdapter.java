package com.example.retofit2.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.retofit2.R;
import com.example.retofit2.model.Phone;

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

        String base64Image = phone.getImagePhone();
        if (base64Image != null && !base64Image.isEmpty()) {
            try {
                if (base64Image.contains(",")) {
                    base64Image = base64Image.split(",")[1];
                }

                byte[] decodedString = android.util.Base64.decode(base64Image, android.util.Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                Glide.with(context).load(decodedBitmap).into(imageView);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        btnClose.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}
