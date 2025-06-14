package com.example.tourister.utils;

import android.net.Uri;
import android.util.Log;
import okhttp3.*;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class CloudinaryUtils {

    public interface UploadCallback {
        void onSuccess(String imageUrl);
        void onError(String error);
    }

    public static void uploadToCloudinary(Uri imageUri, String filePath, UploadCallback callback) {
        String cloudName = "darrrxsil";
        String uploadPreset = "tourister"; // (you can create unsigned preset in cloudinary settings)

        File file = new File(filePath);

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("image/*")))
                .addFormDataPart("upload_preset", uploadPreset)
                .build();

        Request request = new Request.Builder()
                .url("https://api.cloudinary.com/v1_1/" + cloudName + "/image/upload")
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                callback.onError("Failed: " + e.getMessage());
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onError("Upload error: " + response.message());
                    return;
                }

                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String imageUrl = jsonObject.getString("secure_url");
                    callback.onSuccess(imageUrl);
                } catch (Exception e) {
                    callback.onError("Parsing error: " + e.getMessage());
                }
            }
        });
    }
}
