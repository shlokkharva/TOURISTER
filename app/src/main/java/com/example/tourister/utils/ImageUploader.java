package com.example.tourister.utils;

import android.net.Uri;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.UUID;

public class ImageUploader {

    private final StorageReference storageRef;

    public interface UploadCallback {
        void onSuccess(String imageUrl);
        void onError(String error);
    }

    public ImageUploader() {
        storageRef = FirebaseStorage.getInstance().getReference();
    }

    public void uploadImage(Uri imageUri, UploadCallback callback) {
        String filename = UUID.randomUUID().toString() + ".jpg";
        StorageReference imageRef = storageRef.child("package_images/" + filename);

        imageRef.putFile(imageUri)
                .continueWithTask(task -> imageRef.getDownloadUrl())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(task.getResult().toString());
                    } else {
                        callback.onError(task.getException().getMessage());
                    }
                });
    }
}