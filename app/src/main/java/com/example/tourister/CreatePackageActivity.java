package com.example.tourister;

import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tourister.R;
import com.example.tourister.models.PackageModel;
import com.example.tourister.utils.FirebaseRepository;
import com.example.tourister.utils.ImageUploader;
import java.util.UUID;

public class CreatePackageActivity extends AppCompatActivity {

    private EditText etTitle, etDescription, etPrice;
    private Spinner seasonSpinner;
    private ImageView ivPreview;
    private Uri selectedImageUri;
    private final FirebaseRepository repository = new FirebaseRepository();
    private final ImageUploader imageUploader = new ImageUploader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_package);

        initializeViews();
        setupSpinner();
    }

    private void initializeViews() {
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        etPrice = findViewById(R.id.etPrice);
        seasonSpinner = findViewById(R.id.spinnerSeason);
        ivPreview = findViewById(R.id.ivPreview);

        findViewById(R.id.btnUploadImage).setOnClickListener(v -> pickImage());
        findViewById(R.id.btnSave).setOnClickListener(v -> validateAndSave());
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.seasons_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seasonSpinner.setAdapter(adapter);
    }

    private void pickImage() {
        // Implement image picker intent
    }

    private void validateAndSave() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String season = seasonSpinner.getSelectedItem().toString();

        if (validateInputs(title, description, price)) {
            uploadImageAndCreatePackage(title, description, price, season);
        }
    }

    private boolean validateInputs(String title, String description, String price) {
        // Implement validation logic
        return true;
    }

    private void uploadImageAndCreatePackage(String title, String description,
                                             String price, String season) {
        imageUploader.uploadImage(selectedImageUri, new ImageUploader.UploadCallback() {
            @Override
            public void onSuccess(String imageUrl) {
                PackageModel newPackage = new PackageModel(
                        UUID.randomUUID().toString(),
                        title,
                        description,
                        imageUrl,
                        price,
                        season,
                        true
                );

                repository.addPackage(newPackage, new FirebaseRepository.DataCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        // Handle error
                    }
                });
            }

            @Override
            public void onError(String error) {
                // Handle error
            }
        });
    }
}