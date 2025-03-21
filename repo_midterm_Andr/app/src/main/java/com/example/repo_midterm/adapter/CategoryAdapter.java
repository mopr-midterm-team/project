package com.example.repo_midterm.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.repo_midterm.R;
import com.example.repo_midterm.api.RetrofitClient;
import com.example.repo_midterm.model.Category;
import com.example.repo_midterm.model.Phone;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Nguyễn Văn Hoài - 22110327
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    List<Category> array;

    public CategoryAdapter(Context context, List<Category> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView images;
        public TextView tenSp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.image_cate);
            tenSp = itemView.findViewById(R.id.tvNameCategory);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    showProductsDialog(array.get(position).getId());
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category category = array.get(position);
        holder.tenSp.setText(category.getName());

        Glide.with(context)
                .load(category.getImageUrl())
                .into(holder.images);
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }


    private void showProductsDialog(int categoryId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Danh sách sản phẩm");

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_products, null);
        RecyclerView recyclerView = dialogView.findViewById(R.id.rc_products);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);

        List<Phone> phoneList = new ArrayList<>();
        PhoneListAdapter phoneListAdapter = new PhoneListAdapter(context, phoneList);
        recyclerView.setAdapter(phoneListAdapter);

        builder.setView(dialogView);
        builder.setNegativeButton("Đóng", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();

        // Biến kiểm soát việc tải dữ liệu
        final int[] currentPage = {1};
        final boolean[] isLoading = {false};

        // Gọi API để lấy dữ liệu trang đầu tiên
        loadPhones(categoryId, currentPage[0], phoneList, phoneListAdapter);

        // Lắng nghe sự kiện cuộn để tải thêm dữ liệu
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading[0] && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    isLoading[0] = true;
                    currentPage[0]++;
                    loadPhones(categoryId, currentPage[0], phoneList, phoneListAdapter, () -> isLoading[0] = false);
                }
            }
        });
    }

    // Hàm gọi API để lấy danh sách sản phẩm theo trang
    private void loadPhones(int categoryId, int page, List<Phone> phoneList, PhoneListAdapter phoneListAdapter) {
        loadPhones(categoryId, page, phoneList, phoneListAdapter, null);
    }

    private void loadPhones(int categoryId, int page, List<Phone> phoneList, PhoneListAdapter phoneListAdapter, Runnable onComplete) {
        RetrofitClient.getApi().getPhonesByCategory(categoryId, page).enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(Call<List<Phone>> call, Response<List<Phone>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    phoneList.addAll(response.body());
                    phoneListAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Không có thêm dữ liệu!", Toast.LENGTH_SHORT).show();
                }
                if (onComplete != null) onComplete.run();
            }

            @Override
            public void onFailure(Call<List<Phone>> call, Throwable t) {
                Log.e("RetrofitError", "Lỗi khi tải dữ liệu: " + t.getMessage());
                Toast.makeText(context, "Lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                if (onComplete != null) onComplete.run();
            }
        });
    }

}
