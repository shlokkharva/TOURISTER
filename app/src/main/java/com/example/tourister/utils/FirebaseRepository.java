package com.example.tourister.utils;

import com.example.tourister.models.PackageModel;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class FirebaseRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public interface DataCallback<T> {
        void onSuccess(T result);
        void onError(String error);
    }

    // Packages Collection Operations
    public void getAllPackages(DataCallback<List<PackageModel>> callback) {
        db.collection("packages")
                .get()
                .addOnSuccessListener(querySnapshot ->
                        callback.onSuccess(querySnapshot.toObjects(PackageModel.class))
                                .addOnFailureListener(e ->
                                        callback.onError(e.getMessage()));
    }

    public void addPackage(PackageModel pkg, DataCallback<Void> callback) {
        db.collection("packages").document(pkg.getId())
                .set(pkg)
                .addOnSuccessListener(unused -> callback.onSuccess(null))
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    // Bookings Collection Operations
    public void updateBookingStatus(String bookingId, String newStatus, DataCallback<Void> callback) {
        db.collection("bookings").document(bookingId)
                .update("status", newStatus)
                .addOnSuccessListener(unused -> callback.onSuccess(null))
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }
}