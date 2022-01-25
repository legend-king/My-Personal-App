package com.example.mypersonalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mypersonalapp.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper db = new DBHelper(this);

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = binding.userET.getText().toString();
                String pass = binding.passwordET.getText().toString();
                if (user.equals("")){
                    binding.userET.setError("User name can't be left empty");
                }
                else if (pass.equals("")){
                    binding.passwordET.setError("Password can't be left empty");
                }
                else if (pass.length()<6){
                    binding.passwordET.setError("Password should contain at least 6 characters");
                }
                else{
                    Cursor cursor = db.getPasswordsWithUser(user);
                    if ((cursor.getCount() >0)){
                        Toast.makeText(getApplicationContext(), "User is already registered", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        boolean res = db.insertPasswords(user, pass);
                        if (res) {
                            Toast.makeText(getApplicationContext(), "User registered successfully",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to register user",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}