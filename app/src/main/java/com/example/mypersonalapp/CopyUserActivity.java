package com.example.mypersonalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mypersonalapp.databinding.ActivityCopyUserBinding;

public class CopyUserActivity extends AppCompatActivity {
    ActivityCopyUserBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCopyUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        String user = intent.getStringExtra("user_data");
        binding.textView2.setText(user);

        binding.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(
                        Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", user);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "User Name copied to clipboard",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}