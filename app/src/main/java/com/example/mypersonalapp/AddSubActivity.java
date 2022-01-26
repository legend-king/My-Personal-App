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
                        Intent intent = new Intent(AddSubActivity.this, AddSubActivity.class);
                        startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.gotoUsers){
            Intent intent = new Intent(AddSubActivity.this, UserDisplayActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId()==R.id.gotoLogout){
            String user = db.getCurrentUser();
            db.updatePasswords(user, 0);
            Intent intent = new Intent(AddSubActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if (item.getItemId()==R.id.gotoFolders){
            Intent intent = new Intent(AddSubActivity.this, DisplayFoldersActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId()==R.id.deleteAccount){
            String user = db.getCurrentUser();
            new AlertDialog.Builder(AddSubActivity.this)
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete your account. " +
                            "It will permanently delete the account, all the folders and codes will" +
                            " be deleted and you won't be able to recover the account back.")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deletePasswords(user);
                            db.deleteCodes(user);
                            db.deleteSubs();
                            Intent intent = new Intent(AddSubActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).setNeutralButton("Help", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(AddSubActivity.this, "Pressing the yes " +
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