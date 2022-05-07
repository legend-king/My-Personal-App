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

import com.example.mypersonalapp.databinding.ActivityMainPageBinding;

public class MainPage extends AppCompatActivity {

    ActivityMainPageBinding binding;
    DBHelper db;
    String subname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = new DBHelper(this);

        Intent intent = getIntent();
        subname = intent.getStringExtra("sub_name_data");

        binding.addCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = db.getCurrentUser();
                String codename = binding.cnameET.getText().toString();
                String code = binding.code.getText().toString();
                if (codename.equals("")){
                    binding.cnameET.setError("Code Name can't be left empty");
                }
                else if(db.checkCodeNameExists(codename, subname)){
                    binding.cnameET.setError("Code Name already exists");
                }
                else if (user==null){
                    Toast.makeText(getApplicationContext(), "You need to Login first",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean x = db.addCode(codename, code, subname);
                    if (x){
                        Toast.makeText(getApplicationContext(), "Code Added Successfully",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainPage.this, CodesDisplayActivity.class);
                        intent.putExtra("sub_name_data", subname);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Unable to add code",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


}