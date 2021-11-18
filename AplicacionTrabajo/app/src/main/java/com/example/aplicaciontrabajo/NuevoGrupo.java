package com.example.aplicaciontrabajo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class NuevoGrupo extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private List<Grupo> grupos;
    private SQLiteDatabase bbdd;
    private bbddGrupos conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_grupo);

        editTextNombre = findViewById(R.id.editTextNameGrupo);
        editTextDescripcion = findViewById(R.id.editTextGroupDescription2);

    }

    public void añadirNuevoGrupo(View view){

        //crear base de datos:
        conexion = new bbddGrupos(this,"bbddEmpresa",null,1);
        bbdd = conexion.getWritableDatabase(); //modo escritura



        //inserta grupo de ejemplo
        int codigoGrupo = 0;
        String nombreGrupo = editTextNombre.getText().toString();
        String descripcionGrupo = editTextDescripcion.getText().toString();

        if(bbdd!=null){
            String sql = ("INSERT INTO GRUPOS(COD_GRUPO,NOMBREGRUPO,DESCRIPCIONGRUPO) VALUES(?,?,?)");

            SQLiteStatement statement = bbdd.compileStatement(sql);

            statement.clearBindings();

            statement.bindLong(1,codigoGrupo);
            statement.bindString(2,nombreGrupo);
            statement.bindString(3,descripcionGrupo);

            statement.executeInsert();
        }

        Intent i = new Intent(this, MainGrupos.class);
        startActivity(i);



        //cerrar activity
        //mostrar popup mostrando que se ha generado el grupo exitósamente
        //actualizar recyclerview


    }



}