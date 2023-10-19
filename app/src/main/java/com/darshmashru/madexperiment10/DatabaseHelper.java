package com.darshmashru.madexperiment10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "Students.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table Studentdetails(s_rollno TEXT primary key, s_name TEXT, s_marks TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists Userdetails");
    }

    public Boolean addStudentData(String rollNo, String name, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("s_rollno", rollNo);
        contentValues.put("s_name", name);
        contentValues.put("s_marks", marks);
        long result = db.insert("Studentdetails", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean updateStudentData(String rollNo, String name, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("s_name", name);
        contentValues.put("s_marks", marks);
        Cursor cursor = db.rawQuery("Select * from Studentdetails where s_rollno = ?", new String[]{rollNo});
        if(cursor.getCount() > 0) {
            long result = db.update("Studentdetails", contentValues, "s_rollno=?", new String[]{rollNo});
            if (result == -1)
                return false;
            else
                return true;
        }
        else {
            return false;
        }
    }

    public Boolean removeStudentData(String rollNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Studentdetails where s_rollno = ?", new String[]{rollNo});
        if(cursor.getCount() > 0) {
            long result = db.delete("Studentdetails", "s_rollno=?", new String[]{rollNo});
            if (result == -1)
                return false;
            else
                return true;
        }
        else {
            return false;
        }
    }

    public Cursor fetchStudentData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Studentdetails", null);
        return cursor;
    }
}