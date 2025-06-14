package com.tourister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.tourister.models.PackageModel;
import com.tourister.utils.CloudinaryUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdatePackageActivity extends AppCompatActivity {

    private EditText nameEt, descEt;
    private Spinner seasonSpinner;
    private CheckBox availabilityCheckBox;
    private Button uploadImageBtn, updateBtn;
    private ImageView previewImage;

    private Uri selectedImageUri;
    private String currentImageUrl;
    private String packageId;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_package);

        nameEt = findViewById(R.id.packageNameEt);
        descEt = findViewById(R.id.packageDescEt);
        seasonSpinner = findViewById(R.id.seasonSpinner);
        availabilityCheckBox = findViewById(R.id.availableCheckbox);
        uploadImageBtn = findViewById(R.id.uploadImageBtn);
        updateBtn = findViewById(R.id.updatePackageBtn);
        previewImage = findViewById(R.id.previewImage);

        // Load spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.seasons_array, android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seasonSpinner.setAdapter(adapter);

        // Get package data from intent
        PackageModel model = (PackageModel) getIntent().getSerializableExtra("package_data");
        if (model != null) {
            packageId = model.getId();
            nameEt.setText(model.getName());
            descEt.setText(model.getDesc());
            currentImageUrl = model.getImageUrl();
            availabilityCheckBox.setChecked(model.isAvailable());

            // Pre-select spinner
            int pos = adapter.getPosition(model.getSeason());
            seasonSpinner.setSelection(pos);

            // Load image using Glide or similar
            Glide.with(this).load(currentImageUrl).into(previewImage);
        }

        uploadImageBtn.setOnClickListener(v -> pickImageFromGallery());

        updateBtn.setOnClickListener(v -> {
            if (selectedImageUri != null) {
                uploadImageToCloudinary();
            } else {
                updateFirestoreData(currentImageUrl);
            }
        });
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 102);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            previewImage.setImageURI(selectedImageUri);
        }
    }

    private void uploadImageToCloudinary() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading Image...");
        dialog.setCancelable(false);
        dialog.show();

        CloudinaryUtils.uploadImage(selectedImageUri, this, (url, error) -> {
            dialog.dismiss();
            if (url != null) {
                updateFirestoreData(url);
            } else {
                Toast.makeText(this, "Upload failed: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateFirestoreData(String imageUrl) {
        String name = nameEt.getText().toString().trim();
        String desc = descEt.getText().toString().trim();
        String season = seasonSpinner.getSelectedItem().toString();
        boolean available = availabilityCheckBox.isChecked();

        if (name.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("name", name);
        updatedData.put("desc", desc);
        updatedData.put("imageUrl", imageUrl);
        updatedData.put("season", season);
        updatedData.put("available", available);

        db.collection("packages").document(packageId)
                .update(updatedData)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Package updated!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}
