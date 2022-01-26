package com.example.mypersonalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mypersonalapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DBHelper(this);

        binding.login.setOnClickListener(new View.OnClickListener() {
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
                else{
                    Cursor cursor = db.getPasswordsWithUser(user);
                    if (!(cursor.getCount() >0)){
                        Toast.makeText(getApplicationContext(), "User is not registered",
                                Toast.LENGTH_SHORT).show();
                    }
                    if(db.checkPasswordCorrect(user, pass)){
                        Intent intent = new Intent(MainActivity.this, AddSubActivity.class);
                        startActivity(intent);
                        db.updatePasswords(user, 1);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Credentials are wrong",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String x = db.getCurrentUser();
        if (x!=null){
            Intent intent = new Intent(MainActivity.this, AddSubActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.gotoUsersActivity){
            Intent intent = new Intent(MainActivity.this, UserDisplayActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}