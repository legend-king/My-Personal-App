package com.example.mypersonalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mypersonalapp.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DBHelper db = new DBHelper(this);

        binding.change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = binding.userET.getText().toString();
                String pass = binding.passwordET.getText().toString();
                String repass = binding.repasswordET.getText().toString();
                Cursor cursor = db.getPasswordsWithUser(user);
                if (user.equals("")){
                    binding.userET.setError("User name can't be left empty");
                }
                else if (pass.equals("")){
                    binding.passwordET.setError("Password can't be left empty");
                }
                else if(repass.equals("")){
                    binding.repasswordET.setError("Password should not be empty");
                }
                else if (pass.length()<6){
                    binding.passwordET.setError("Password should contain at least 6 characters");
                }
                else if (!(cursor.getCount()>0)){
                    binding.userET.setError("User is not registered");
                }
                else if (!pass.equals(repass)){
                    binding.repasswordET.setError("Password mismatch");
                }
                else{
                    boolean res = db.updatePasswords(user, pass);
                    if (res){
                        Toast.makeText(getApplicationContext(), "Password updated successfully",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Unable to update password", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}