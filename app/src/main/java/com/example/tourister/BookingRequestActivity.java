package com.tourister;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tourister.adapters.BookingAdapter;
import com.tourister.models.BookingModel;

import java.util.ArrayList;
import java.util.List;

public class BookingRequestActivity extends AppCompatActivity implements BookingAdapter.OnStatusChangeListener {

    private RecyclerView bookingRecyclerView;
    private BookingAdapter adapter;
    private List<BookingModel> bookingList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_requests);

        bookingRecyclerView = findViewById(R.id.bookingRecyclerView);
        bookingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new BookingAdapter(bookingList, this);
        bookingRecyclerView.setAdapter(adapter);

        loadBookingRequests();
    }

    private void loadBookingRequests() {
        db.collection("booking_requests")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    bookingList.clear();
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        BookingModel model = doc.toObject(BookingModel.class);
                        if (model != null) {
                            bookingList.add(model);
                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error loading bookings: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onStatusChanged(BookingModel booking, String newStatus) {
        db.collection("booking_requests").document(booking.getId())
                .update("status", newStatus)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Status updated to " + newStatus, Toast.LENGTH_SHORT).show();
                    loadBookingRequests(); // Refresh list
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed to update status: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
