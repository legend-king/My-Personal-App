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

import com.example.mypersonalapp.Codes;
import com.example.mypersonalapp.CopyUpdateCode;
import com.example.mypersonalapp.CopyUserActivity;
import com.example.mypersonalapp.R;
import com.example.mypersonalapp.Users;

import java.util.List;

public class RCCodesAdapter extends RecyclerView.Adapter<RCCodesAdapter.ViewHolder>{
    private Context context;
    private List<Codes> pd;

    public RCCodesAdapter(Context context, List<Codes> pd) {
        this.context = context;
        this.pd = pd;
    }

    @NonNull
    @Override
    public RCCodesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new RCCodesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RCCodesAdapter.ViewHolder holder, int position) {
        Codes password = pd.get(position);
        holder.nameOfPass.setText("Code Name : "+password.getCodename());
    }

    @Override
    public int getItemCount() {
        return pd.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView nameOfPass;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            nameOfPass = itemView.findViewById(R.id.nameOfPass);
        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            Codes password = pd.get(position);
            String name = password.getCode();

            Intent intent = new Intent(context, CopyUpdateCode.class);
            intent.putExtra("code_data",name);
            intent.putExtra("codename_data", password.getCodename());
            context.startActivity(intent);
        }
    }
}
