package com.example.tourister.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tourister.R;
import com.example.tourister.adapters.PackageAdapter;
import com.example.tourister.models.PackageModel;
import com.example.tourister.utils.FirebaseRepository;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PackageAdapter.PackageClickListener {

    private RecyclerView recyclerView;
    private PackageAdapter adapter;
    private FirebaseRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new FirebaseRepository();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadPackages();
    }

    private void loadPackages() {
        repository.getAllPackages(new FirebaseRepository.DataCallback<List<PackageModel>>() {
            @Override
            public void onSuccess(List<PackageModel> result) {
                adapter = new PackageAdapter(result, MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(String error) {
                // Handle error
            }
        });
    }

    @Override
    public void onPackageClick(PackageModel pkg) {
        // Start PackageDetailsActivity with selected package
    }
}