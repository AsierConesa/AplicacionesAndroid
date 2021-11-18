package com.example.aplicaciontrabajo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class bbddGrupos extends SQLiteOpenHelper {

    public bbddGrupos(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version){
        super(contexto,nombre,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE grupos(cod_grupo INTEGER, nombregrupo TEXT, descripciongrupo TEXT)");
        db.execSQL("CREATE TABLE quedadas(cod_quedada INTEGER, nombrequedada TEXT, descripcionquedada TEXT, idgrupo INTEGER)");
        db.execSQL("CREATE TABLE integrantes(cod_integrante INTEGER, nombreintegrante TEXT)");
        db.execSQL("CREATE TABLE grupos_integrantes(cod_integrante INTEGER, cod_grupo INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS grupos");
        db.execSQL("DROP TABLE IF EXISTS quedadas");
        db.execSQL("DROP TABLE IF EXISTS integrantes");
        db.execSQL("DROP TABLE IF EXISTS grupos_integrantes");
        db.execSQL("CREATE TABLE grupos(cod_grupo INTEGER, nombregrupo TEXT, descripciongrupo TEXT)");
        db.execSQL("CREATE TABLE quedadas(cod_quedada INTEGER, nombrequedada TEXT, descripcionquedada TEXT, idgrupo INTEGER)");
        db.execSQL("CREATE TABLE integrantes(cod_integrante INTEGER, nombreintegrante TEXT)");
        db.execSQL("CREATE TABLE grupos_integrantes(cod_integrante INTEGER, cod_grupo INTEGER)");
    }
}
