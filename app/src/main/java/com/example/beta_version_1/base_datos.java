package com.example.beta_version_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class base_datos extends SQLiteOpenHelper {
    public base_datos(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    tabla table=new tabla();
    @Override
    public void onCreate(SQLiteDatabase sql ){
//        sql.execSQL("create table usuario(\n" +
//                "id integer primary key AUTOINCREMENT,\n" +
//                "nombre text(30),\n" +
//                "pass text(30)\n" +
//                ");");
        sql.execSQL("create table usuario(\n" +
                "id integer primary key AUTOINCREMENT,\n" +
                "nombre text(30) unique,\n" +
                "pass text(30),\n" +
                "gmail text(30),\n" +
                "telefono text(30),\n" +
                "fecha text(30)\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sq, int i, int i1) {

    }

}
