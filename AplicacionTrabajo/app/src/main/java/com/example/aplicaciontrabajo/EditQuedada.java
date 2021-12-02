package com.example.aplicaciontrabajo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditQuedada extends AppCompatActivity {

    SQLiteDatabase bbdd;
    bbddGrupos conexion;
    EditText editTextNombreQuedada, editTextDescripcionQuedada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_quedada);

        editTextNombreQuedada = findViewById(R.id.et_name);
        editTextDescripcionQuedada = findViewById(R.id.et_desc);

        //crear base de datos:
        conexion = new bbddGrupos(this,"bbddEmpresa",null,1);
        bbdd = conexion.getReadableDatabase(); //modo lectura

        //variables
        Bundle bundle = getIntent().getExtras();
        String nombre = bundle.getString("nombre");
        String desc = bundle.getString("desc");

        editTextNombreQuedada.setText(nombre);
        editTextDescripcionQuedada.setText(desc);
    }

}