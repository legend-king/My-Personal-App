package com.example.mypersonalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mypersonalapp.databinding.ActivityAddSubBinding;

public class AddSubActivity extends AppCompatActivity {
    ActivityAddSubBinding binding;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddSubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = new DBHelper(this);

        binding.addSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sub = binding.subnameET.getText().toString();
                if (sub.equals("")){
                    Toast.makeText(getApplicationContext(), "Folder Name cannot be empty",
                            Toast.LENGTH_SHORT).show();
                }
                else if (db.checkSubNameExists(sub)){
                    Toast.makeText(getApplicationContext(), "Folder already exists",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean x = db.insertSub(sub);
                    if (x){
                        Toast.makeText(getApplicationContext(), "Folder created successfully",
                                Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(AddSubActivity.this, AddSubActivity.class);
//                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Unable to create folder",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


}