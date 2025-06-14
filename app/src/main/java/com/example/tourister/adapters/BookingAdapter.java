package com.example.tourister.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tourister.R;
import com.example.tourister.models.BookingModel;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    public interface OnStatusChangeListener {
        void onStatusChanged(BookingModel booking, String newStatus);
    }

    private final List<BookingModel> bookingList;
    private final OnStatusChangeListener listener;

    public BookingAdapter(List<BookingModel> bookingList, OnStatusChangeListener listener) {
        this.bookingList = bookingList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        BookingModel booking = bookingList.get(position);
        holder.packageName.setText(booking.getPackageTitle());
        holder.userName.setText(booking.getUserName());
        holder.status.setText(booking.getStatus());

        holder.approveBtn.setOnClickListener(v -> listener.onStatusChanged(booking, "approved"));
        holder.rejectBtn.setOnClickListener(v -> listener.onStatusChanged(booking, "rejected"));
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView packageName, userName, status;
        Button approveBtn, rejectBtn;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            packageName = itemView.findViewById(R.id.packageNameText);
            userName = itemView.findViewById(R.id.userNameText);
            status = itemView.findViewById(R.id.statusText);
            approveBtn = itemView.findViewById(R.id.approveBtn);
            rejectBtn = itemView.findViewById(R.id.rejectBtn);
        }
    }
}