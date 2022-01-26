package com.example.mypersonalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
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
}