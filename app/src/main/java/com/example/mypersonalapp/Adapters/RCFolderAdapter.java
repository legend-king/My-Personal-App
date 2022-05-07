package com.example.mypersonalapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypersonalapp.Codes;
import com.example.mypersonalapp.CodesDisplayActivity;
import com.example.mypersonalapp.CopyUpdateCode;
import com.example.mypersonalapp.MainPage;
import com.example.mypersonalapp.R;
import com.example.mypersonalapp.Subs;

import java.util.List;

public class RCFolderAdapter extends RecyclerView.Adapter<RCFolderAdapter.ViewHolder>{
    private Context context;
    private List<Subs> pd;

    public RCFolderAdapter(Context context, List<Subs> pd) {
        this.context = context;
        this.pd = pd;
    }

    @NonNull
    @Override
    public RCFolderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new RCFolderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RCFolderAdapter.ViewHolder holder, int position) {
        Subs password = pd.get(position);
        holder.nameOfPass.setText("Folder Name : "+password.getSubname());
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
            Subs password = pd.get(position);

            Intent intent = new Intent(context, CodesDisplayActivity.class);
            intent.putExtra("sub_name_data", password.getSubname());
            context.startActivity(intent);
        }
    }
}
