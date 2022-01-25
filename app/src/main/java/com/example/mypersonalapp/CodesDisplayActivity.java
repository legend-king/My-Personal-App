package com.example.mypersonalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.database.Cursor;
import android.os.Bundle;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCodesDisplayBinding.inflate(getLayoutInflater());
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

        Cursor res = db.getCodes();
        while (res.moveToNext()){
            int a = res.getColumnIndex("name");
            int b = res.getColumnIndex("code");
            Codes pd = new Codes(res.getString(a), res.getString(b));
            userArrayList.add(pd);
        }

        recyclerViewAdapter = new RCCodesAdapter(CodesDisplayActivity.this, userArrayList);
        binding.recyclerView.setAdapter(recyclerViewAdapter);

    }
}