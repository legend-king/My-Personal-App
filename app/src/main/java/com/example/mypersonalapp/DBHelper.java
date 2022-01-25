package com.example.mypersonalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Passwords(username TEXT primary key not null, password TEXT not null, status Integer default 0)");
        DB.execSQL("create Table Codes(name TEXT not null, code TEXT, username TEXT references Passwords(username))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists Passwords");
        DB.execSQL("drop Table if exists Codes");
    }

    public boolean insertPasswords(String username, String pass){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",pass);
        long result = DB.insert("Passwords",null,contentValues);
        return result != -1;
    }

    public boolean updatePasswords(String username, String pass){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password",pass);

        Cursor cursor = DB.rawQuery("Select * from Passwords where username = ?",new String[] {username});
        if (cursor.getCount()>0) {
            long result = DB.update("Passwords", contentValues, "username=?", new String[]{username});
            return result != -1;
        }
        else{
            return false;
        }
    }

    public boolean updatePasswords(String username, int status){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status",status);
        Cursor cursor = DB.rawQuery("Select * from Passwords where username = ?",new String[] {username});
        if (cursor.getCount()>0) {
            long result = DB.update("Passwords", contentValues, "username=?", new String[]{username});
            return result != -1;
        }
        else{
            return false;
        }
    }

    public boolean deletePasswords(String username){
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Passwords where username = ?",new String[] {username});
        if (cursor.getCount()>0){
            long result = DB.delete("Passwords","username=?", new String[]{username});
            return result != -1;
        }
        else{
            return false;
        }
    }

    public boolean deleteCodes(String username){
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Codes where username = ?",
                new String[] {username});
        if (cursor.getCount()>0){
            long result = DB.delete("Codes","username=?", new String[]{username});
            return result != -1;
        }
        else{
            return false;
        }
    }

    public Cursor getPasswords(){
        SQLiteDatabase DB=this.getReadableDatabase();
        return DB.rawQuery("Select * from Passwords",null);
    }

    public Cursor getPasswordsWithUser(String username){
        SQLiteDatabase DB = this.getReadableDatabase();
        return DB.rawQuery("Select * from Passwords where username = ?",new String[] {username});
    }

    public boolean checkPasswordCorrect(String username, String pass){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Passwords where username = ? and password = ?",
                new String[] {username,pass});
        return cursor.getCount()>0;
    }

    public String getCurrentUser(){
        SQLiteDatabase DB=this.getReadableDatabase();
        Cursor res = DB.rawQuery("Select * from Passwords where status = 1",null);
        if (res.getCount()>0) {
            res.moveToNext();
            return res.getString(0);
        }
        return null;
    }

    public boolean addCode(String codename, String code){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",codename);
        contentValues.put("code",code);
        contentValues.put("username", getCurrentUser());
        long result = DB.insert("Codes",null,contentValues);
        return result != -1;
    }

    public boolean checkCodeNameExists(String codename){
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Codes where name = ?",
                new String[] {codename});
        return cursor.getCount()>0;
    }

    public Cursor getCodes(){
        SQLiteDatabase DB = this.getReadableDatabase();
        return DB.rawQuery("Select * from Codes where username = ?",
                new String[] {getCurrentUser()});
    }

    public boolean updateCodes(String codename, String code){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("code",code);
        Cursor cursor = DB.rawQuery("Select * from Codes where username = ? and name = ?",
                new String[] {getCurrentUser(), codename});
        if (cursor.getCount()>0) {
            long result = DB.update("Codes", contentValues, "username=? and name=?",
                    new String[]{getCurrentUser(), codename});
            return result != -1;
        }
        else{
            return false;
        }
    }

    public boolean deleteSpecificCode(String codename){
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Codes where username = ? and name = ?",
                new String[] {getCurrentUser(), codename});
        if (cursor.getCount()>0){
            long result = DB.delete("Codes","username=? and name=?",
                    new String[]{getCurrentUser(), codename});
            return result != -1;
        }
        else{
            return false;
        }
    }

}
