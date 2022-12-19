package com.example.pert10_sharedpreferences;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBmain extends SQLiteOpenHelper {
    public static final String DBNAME="lagu.db";
    public static final String TABLENAME="daftar_lagu";
    public static final int VER=1;
    public DBmain(@Nullable Context context) {
        super(context, DBNAME, null, VER);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table "+TABLENAME+"(id integer primary key, judul_lagu TEXT, artis TEXT, tahun TEXT, avatar blob)";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "drop table if exists "+TABLENAME+"";
        db.execSQL(query);
        onCreate(db);
    }
}

