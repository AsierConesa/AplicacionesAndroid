package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class bbddEmpresa extends SQLiteOpenHelper {
    /**
     *
     * @param contexto
     * @param nombre nombre de la base de datos
     * @param factory
     * @param version version de la base de datos
     */
    public bbddEmpresa(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version){
        super(contexto,nombre,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE empleados(codigo INTEGER, nombre TEXT, edad INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Empleados");
        db.execSQL("CREATE TABLE empleados(codigo INTEGER, nombre TEXT, edad INTEGER)");
    }
}
