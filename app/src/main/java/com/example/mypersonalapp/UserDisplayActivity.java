package com.example.mypersonalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.os.Bundle;

import com.example.mypersonalapp.Adapters.RCUsersAdapter;
import com.example.mypersonalapp.databinding.ActivityUserDisplayBinding;

import java.util.ArrayList;

public class UserDisplayActivity extends AppCompatActivity {
    ActivityUserDisplayBinding binding;
    private ArrayList<Users> userArrayList;
    private RCUsersAdapter recyclerViewAdapter;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = new DBHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userArrayList = new ArrayList<>();

        Cursor res = db.getPasswords();
        while (res.moveToNext()){
            int a = res.getColumnIndex("status");
            int b = res.getColumnIndex("username");
            Users pd = new Users(res.getInt(a), res.getString(b));
            userArrayList.add(pd);
        }

        recyclerViewAdapter = new RCUsersAdapter(UserDisplayActivity.this, userArrayList);
        binding.recyclerView.setAdapter(recyclerViewAdapter);

    }
}