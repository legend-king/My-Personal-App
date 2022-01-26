package com.example.mypersonalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.os.Bundle;

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
}