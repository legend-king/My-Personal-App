package com.example.mypersonalapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mypersonalapp.databinding.ActivityCopyUpdateCodeBinding;

public class CopyUpdateCode extends AppCompatActivity {
    ActivityCopyUpdateCodeBinding binding;
    String code, codename;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCopyUpdateCodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        code = intent.getStringExtra("code_data");
        codename = intent.getStringExtra("codename_data");

        binding.textView.setText(code);
        db = new DBHelper(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.code_update_delete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.updateCode){
            boolean x = db.updateCodes(codename, code);
            if (x){
                code = binding.textView.getText().toString();
                Toast.makeText(getApplicationContext(), "Changes saved successfully",
                        Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Not able to save the changes",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else if (item.getItemId()==R.id.copyCodeET){
            ClipboardManager clipboard = (ClipboardManager) getSystemService(
                    Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("simple text", code);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Code copied to clipboard",
                    Toast.LENGTH_SHORT).show();
        }
        else if (item.getItemId()==R.id.deleteCode){
            new AlertDialog.Builder(CopyUpdateCode.this)
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want delete this code. This action cannot be undone")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean x = db.deleteSpecificCode(codename);
                            if (!x) {
                                Toast.makeText(getApplicationContext(), "Not able to delete the code",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else {
                                finish();
                                finish();
                            }
                        }
                    }).setNeutralButton("Help", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(CopyUpdateCode.this, "Pressing the yes " +
                            "button will delete the code", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        if (!code.equals(binding.textView.getText().toString())){
            new AlertDialog.Builder(CopyUpdateCode.this)
                    .setIcon(R.drawable.ic_launcher_foreground)
                    .setTitle("Changes not saved")
                    .setMessage("Are you sure you want to go back without saving the changes")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNeutralButton("Help", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(CopyUpdateCode.this, "Pressing the yes " +
                            "button will exit without saving changes", Toast.LENGTH_SHORT).show();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
        else{
            super.onBackPressed();
        }
    }
}