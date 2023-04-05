package com.example.lab03_th;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DbAdapter {

    private DatabaseHandler dbHandler;
    private SQLiteDatabase sqLiteDatabase;

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "studentManager";
    private static final String DATABASE_TABLE = "students";

    private static final String KEY_ID ="id";
    private static final String KEY_NAME = "name";
    private static final String KEY_MAJOR = "major";
    private static final String KEY_AVERAGE = "average";

    private final Context context;

    public DbAdapter(Context context) {
        this.context = context;
    }

    public DbAdapter open(){
        dbHandler = new DatabaseHandler(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqLiteDatabase = dbHandler.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHandler.close();
    }

    public long addStudent(Student student){
        ContentValues inititalValues = new ContentValues();
        inititalValues.put(KEY_NAME, student.getName());
        inititalValues.put(KEY_MAJOR, student.getMajor());
        inititalValues.put(KEY_AVERAGE, student.getAverage());
        long i = sqLiteDatabase.insert(DATABASE_TABLE, null, inititalValues);
        return i;
    }

    @SuppressLint("Range")
    public Student getStudent (int id){
        Student st = new Student();
        Cursor one_row_st = sqLiteDatabase.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_NAME, KEY_MAJOR, KEY_AVERAGE}, "id = ?", new String[]{String.valueOf(id)},null,null,null);
        while( one_row_st.moveToNext()){
            st.setId(one_row_st.getInt(one_row_st.getColumnIndex("id")));
            st.setName(one_row_st.getString(one_row_st.getColumnIndex("name")));
            st.setMajor(one_row_st.getString(one_row_st.getColumnIndex("major")));
            st.setAverage(one_row_st.getFloat(one_row_st.getColumnIndex("average")));
        }
        return st;
    }

    @SuppressLint("Range")
    public List<Student> getAllStudents (){
        List<Student> list = new ArrayList<>();
        Cursor all_row_st = sqLiteDatabase.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_NAME, KEY_MAJOR, KEY_AVERAGE}, null, null, null, null, null);
        while (all_row_st.moveToNext()){
            Student st = new Student();
            st.setId(all_row_st.getInt(all_row_st.getColumnIndex("id")));
            st.setName(all_row_st.getString(all_row_st.getColumnIndex("name")));
            st.setMajor(all_row_st.getString(all_row_st.getColumnIndex("major")));
            st.setAverage(all_row_st.getFloat(all_row_st.getColumnIndex("average")));
            list.add(st);
        }
        return list;
    }

    public int UpdateStudent (Student student){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, student.getId());
        contentValues.put(KEY_NAME, student.getName());
        contentValues.put(KEY_MAJOR, student.getMajor());
        contentValues.put(KEY_AVERAGE, student.getAverage());
        int upd = sqLiteDatabase.update(DATABASE_TABLE, contentValues, "id = ?", new String[]{String.valueOf(student.getId())});
        return upd;
    }

    public void deleteStudent (Student student){
        int dele_st = sqLiteDatabase.delete(DATABASE_TABLE, "id = ?", new String[]{String.valueOf(student.getId())});
    }
}
