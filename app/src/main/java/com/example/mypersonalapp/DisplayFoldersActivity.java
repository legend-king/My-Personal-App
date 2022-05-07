package com.example.mypersonalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mypersonalapp.Adapters.RCCodesAdapter;
import com.example.mypersonalapp.Adapters.RCFolderAdapter;
import com.example.mypersonalapp.databinding.ActivityDisplayFoldersBinding;

import java.util.ArrayList;

public class DisplayFoldersActivity extends AppCompatActivity {
    ActivityDisplayFoldersBinding binding;
    private ArrayList<Subs> userArrayList;
    private RCFolderAdapter recyclerViewAdapter;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayFoldersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = new DBHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userArrayList = new ArrayList<>();

        String username = db.getCurrentUser();
        Cursor res = db.getSubs();
        while (res.moveToNext()){
            int a = res.getColumnIndex("subname");
            Subs pd = new Subs(res.getString(a));
            userArrayList.add(pd);
        }

        recyclerViewAdapter = new RCFolderAdapter(DisplayFoldersActivity.this, userArrayList);
        binding.recyclerView.setAdapter(recyclerViewAdapter);

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
            Intent intent = new Intent(DisplayFoldersActivity.this, UserDisplayActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId()==R.id.gotoLogout){
            String user = db.getCurrentUser();
            db.updatePasswords(user, 0);
            Intent intent = new Intent(DisplayFoldersActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if (item.getItemId()==R.id.gotoFolders){
            Intent intent = new Intent(DisplayFoldersActivity.this, AddSubActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId()==R.id.deleteAccount){
            String user = db.getCurrentUser();
            new AlertDialog.Builder(DisplayFoldersActivity.this)
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
                            Intent intent = new Intent(DisplayFoldersActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).setNeutralButton("Help", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(DisplayFoldersActivity.this, "Pressing the yes " +
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