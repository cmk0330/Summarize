package com.cmk.app.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by cmk on 2018/11/26.
 */

public  class SearchHistoryDB extends SQLiteOpenHelper {

    public final static String DB_NAME = "SearchHistory_db";
    public final static String TABLE_NAME = "SearchHistory";
    public final static String CREATE_TABLE = "create table if not exists " + TABLE_NAME + " ("
            + "id integer primary key autoincrement, "
            + "history text)";

    public SearchHistoryDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SearchHistoryDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * c查询全部
     */
    public ArrayList<String> queryAllHistory() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, "id desc");
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String string = cursor.getString(1);
            list.add(string);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 插入一条数据
     */
    public void insertHistory(String key) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("history", key);
        db.insert(TABLE_NAME, null, cv);
        db.close();
        Log.e("插入数据::", key);
    }

    /**
     * 删除一条数据
     */
    public void deleteHistory(String key) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "history=?", new String[]{key});
        db.close();
    }

    /**
     * 删除全部数据
     */
    public void removeAllHistory() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
        db.close();
    }
}
