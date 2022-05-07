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
import com.example.mypersonalapp.Adapters.RCUsersAdapter;
import com.example.mypersonalapp.databinding.ActivityCodesDisplayBinding;
import com.example.mypersonalapp.databinding.ActivityUserDisplayBinding;

import java.util.ArrayList;

public class CodesDisplayActivity extends AppCompatActivity {
    ActivityCodesDisplayBinding binding;
    private ArrayList<Codes> userArrayList;
    private RCCodesAdapter recyclerViewAdapter;
    DBHelper db;
    String subname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCodesDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = new DBHelper(this);
        Intent intent = getIntent();
        subname = intent.getStringExtra("sub_name_data");
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userArrayList = new ArrayList<>();


        Cursor res = db.getCodes(subname);
        while (res.moveToNext()){
            int a = res.getColumnIndex("codename");
            int b = res.getColumnIndex("code");
            int c = res.getColumnIndex("subname");
            Codes pd = new Codes(res.getString(a), res.getString(b), res.getString(c));
            userArrayList.add(pd);
        }

        recyclerViewAdapter = new RCCodesAdapter(CodesDisplayActivity.this, userArrayList);
        binding.recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_sub_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.gotoCodes){
            Intent intent = new Intent(CodesDisplayActivity.this, MainPage.class);
            intent.putExtra("sub_name_data", subname);
            startActivity(intent);
            finish();
        }
        else if (item.getItemId()==R.id.deleteFolder){
            String user = db.getCurrentUser();
            new AlertDialog.Builder(CodesDisplayActivity.this)
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete this folder. " +
                            "It will permanently delete the folder and " +
                            "you won't be able to recover the folder back.")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deleteCodes(user, subname);
                            db.deleteSubs(subname);
                            finish();
                        }
                    }).setNeutralButton("Help", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(CodesDisplayActivity.this, "Pressing the yes " +
                            "button will delete the folder", Toast.LENGTH_SHORT).show();
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