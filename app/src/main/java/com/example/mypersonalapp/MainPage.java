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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = new DBHelper(this);

        binding.addCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = db.getCurrentUser();
                String codename = binding.cnameET.getText().toString();
                String code = binding.code.getText().toString();
                if (codename.equals("")){
                    binding.cnameET.setError("Code Name can't be left empty");
                }
                else if(db.checkCodeNameExists(codename)){
                    binding.cnameET.setError("Code Name already exists");
                }
                else if (user==null){
                    Toast.makeText(getApplicationContext(), "You need to Login first",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean x = db.addCode(codename, code);
                    if (x){
                        Toast.makeText(getApplicationContext(), "Code Added Successfully",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainPage.this, MainPage.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.gotoUsers){
            Intent intent = new Intent(MainPage.this, UserDisplayActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId()==R.id.gotoLogout){
            String user = db.getCurrentUser();
            db.updatePasswords(user, 0);
            Intent intent = new Intent(MainPage.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if (item.getItemId()==R.id.gotoCodes){
            Intent intent = new Intent(MainPage.this, CodesDisplayActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId()==R.id.deleteAccount){
            String user = db.getCurrentUser();
            new AlertDialog.Builder(MainPage.this)
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete your account. " +
                            "It will permanently delete the account and " +
                            "you won't be able to recover the account back.")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deletePasswords(user);
                            db.deleteCodes(user);
                            Intent intent = new Intent(MainPage.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).setNeutralButton("Help", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainPage.this, "Pressing the yes " +
                            "button will delete the account", Toast.LENGTH_SHORT).show();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();

        }
        return super.onOptionsItemSelected(item);
    }
}