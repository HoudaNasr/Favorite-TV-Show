package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.EmptyStackException;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME ="people.db";
    public static final String TABLE_NAME ="people_table";
    public static final String COL1 ="ID";
    public static final String COL2 ="NAME";
    public static final String COL3 ="EMAIL";
    public static final String COL4 ="TVSHOW";

    public DataBaseHelper(@Nullable Context context) {
        super(context , DB_NAME ,null , 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " NAME TEXT, EMAIL TEXT, TVSHOW TEXT)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean addData(String name, String email, String tvshow){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2 ,name);
        contentValues.put(COL3 ,email);
        contentValues.put(COL4 ,tvshow);
        long result = db.insert(TABLE_NAME , null , contentValues);
        if(result == -1) {
            return false;
        }
        else {
            return true;
        }
    }
    public Cursor showData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME , null);
        return data;
    }
    public boolean update (String id , String name , String email , String tvshow){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1 ,id);
        contentValues.put(COL2,name);
        contentValues.put(COL3,email);
        contentValues.put(COL4,tvshow);
        db.update(TABLE_NAME , contentValues , "ID =?",new String[]{id});
        return true;
    }
    public boolean delete(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME , "ID =?" ,new String[]{id});
        return true;
    }

}
