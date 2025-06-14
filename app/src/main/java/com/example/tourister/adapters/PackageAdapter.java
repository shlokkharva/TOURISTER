package com.example.tourister.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.tourister.R;
import com.example.tourister.models.PackageModel;
import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.ViewHolder> {

    public interface PackageClickListener {
        void onPackageClick(PackageModel pkg);
    }

    private final List<PackageModel> packages;
    private final PackageClickListener listener;

    public PackageAdapter(List<PackageModel> packages, PackageClickListener listener) {
        this.packages = packages;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_package, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PackageModel pkg = packages.get(position);
        holder.title.setText(pkg.getTitle());
        holder.price.setText(pkg.getPrice());
        Glide.with(holder.itemView.getContext())
                .load(pkg.getImageUrl())
                .into(holder.image);

        holder.itemView.setOnClickListener(v -> listener.onPackageClick(pkg));
    }

    @Override
    public int getItemCount() {
        return packages.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ivPackageImage);
            title = itemView.findViewById(R.id.tvPackageTitle);
            price = itemView.findViewById(R.id.tvPackagePrice);
        }
    }
}