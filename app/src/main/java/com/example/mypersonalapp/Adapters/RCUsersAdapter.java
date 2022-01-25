package com.example.mypersonalapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypersonalapp.CopyUserActivity;
import com.example.mypersonalapp.MainActivity;
import com.example.mypersonalapp.MainPage;
import com.example.mypersonalapp.R;
import com.example.mypersonalapp.UserDisplayActivity;
import com.example.mypersonalapp.Users;

import java.util.ArrayList;
import java.util.List;


public class RCUsersAdapter extends RecyclerView.Adapter<RCUsersAdapter.ViewHolder>{

    private Context context;
    private List<Users> pd;

    public RCUsersAdapter(Context context, List<Users> pd) {
        this.context = context;
        this.pd = pd;
    }

    @NonNull
    @Override
    public RCUsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RCUsersAdapter.ViewHolder holder, int position) {
        Users password = pd.get(position);
        if (password.getStatus()==1){
            holder.nameOfPass.setTextColor(Color.parseColor("#FF0000"));
        }
        holder.nameOfPass.setText("User Name : "+password.getUsername());
    }

    @Override
    public int getItemCount() {
        return pd.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView nameOfPass;
        public TextView originalPass;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            nameOfPass = itemView.findViewById(R.id.nameOfPass);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            Users password = pd.get(position);
            String name = password.getUsername();

            Intent intent = new Intent(context, CopyUserActivity.class);
            intent.putExtra("user_data",name);
            context.startActivity(intent);
        }
    }
}

