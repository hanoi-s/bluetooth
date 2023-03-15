package com.example.SmartBox_2;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PictureActivity extends AppCompatActivity {
    private List<String> mImageUrls;
    private ImageAdapter mAdapter;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        GridView gridView = findViewById(R.id.grid_view);
        gridView.setNumColumns(1);
        mAuth = FirebaseAuth.getInstance();
        mImageUrls = new ArrayList<>();
        mAdapter = new ImageAdapter(this, mImageUrls);
        gridView.setAdapter(mAdapter);

        // Retrieve the list of image file names from Firebase Storage
        FirebaseStorage.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid())
                .child("images").listAll()
                .addOnSuccessListener(listResult -> {
                    for (StorageReference item : listResult.getItems()) {
                        item.getDownloadUrl().addOnSuccessListener(uri -> {
                            String url = uri.toString();
                            Log.d("TAG", "Image URL: " + url);
                            mImageUrls.add(url);
                            // Sort the list by file name
                            Collections.sort(mImageUrls, (s1, s2) -> {
                                String fileName1 = getFileName(s1);
                                String fileName2 = getFileName(s2);
                                return fileName2.compareToIgnoreCase(fileName1);
                            });
                            mAdapter.notifyDataSetChanged();
                        });
                    }
                })
                .addOnFailureListener(e -> Log.e("TAG", "Error getting image URLs", e));
    }

    // Helper method to extract the file name from a URL
    private String getFileName(String url) {
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }
}