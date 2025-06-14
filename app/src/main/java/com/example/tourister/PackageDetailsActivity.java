package com.example.tourister;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.tourister.models.PackageModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class PackageDetailsActivity extends AppCompatActivity {

    private ImageView packageImage;
    private TextView packageTitle, packagePrice, packageDuration, packageItinerary, packageInclusions, packageDetails;
    private Button bookNowButton;

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Initialize UI elements
        packageImage = findViewById(R.id.packageImage);
        packageTitle = findViewById(R.id.packageTitle);
        packagePrice = findViewById(R.id.packagePrice);
        packageDuration = findViewById(R.id.packageDuration);
        packageItinerary = findViewById(R.id.packageItinerary);
        packageInclusions = findViewById(R.id.packageInclusions);
        packageDetails = findViewById(R.id.packageDetails);
        bookNowButton = findViewById(R.id.bookNowButton);

        // Get package data from intent
        PackageModel packageModel = (PackageModel) getIntent().getSerializableExtra("package");

        if (packageModel != null) {
            // Load Cloudinary image URL
            Glide.with(this)
                    .load(packageModel.getImageUrl()) // should be a full Cloudinary image URL
                    .placeholder(R.drawable.placeholder)
                    .into(packageImage);

            // Set text fields
            packageTitle.setText(packageModel.getTitle());
            packagePrice.setText("Price: ₹" + packageModel.getPrice());
            packageDuration.setText("Duration: " + packageModel.getDuration());
            packageItinerary.setText("Itinerary: " + packageModel.getItinerary());
            packageInclusions.setText("Inclusions: " + packageModel.getInclusions());
            packageDetails.setText("Details: " + packageModel.getDetails());

            // Handle booking button click
            bookNowButton.setOnClickListener(v -> sendBookingRequest(packageModel));
        }
    }

    private void sendBookingRequest(PackageModel model) {
        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "Please login first!", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = auth.getCurrentUser().getUid();

        // Create booking object
        BookingModel booking = new BookingModel(
                userId,
                model.getId(),
                model.getTitle(),
                model.getPrice(),
                "Pending"
        );

        db.collection("bookingRequests")
                .add(booking)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(PackageDetailsActivity.this, "Booking request sent!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(PackageDetailsActivity.this, "Failed to send request!", Toast.LENGTH_SHORT).show();
                });
    }
}
