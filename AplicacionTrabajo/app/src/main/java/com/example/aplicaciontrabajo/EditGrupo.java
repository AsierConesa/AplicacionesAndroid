package com.example.aplicaciontrabajo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditGrupo extends AppCompatActivity {

    SQLiteDatabase bbdd;
    bbddGrupos conexion;
    EditText editTextNombreGrupo, editTextDescripcionGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_grupo);

        editTextNombreGrupo = findViewById(R.id.et_name);
        editTextDescripcionGrupo = findViewById(R.id.et_desc);

        //crear base de datos:
        conexion = new bbddGrupos(this,"bbddEmpresa",null,1);
        bbdd = conexion.getReadableDatabase(); //modo lectura

        //variables
        Bundle bundle = getIntent().getExtras();
        String nombre = bundle.getString("nombre");
        String desc = bundle.getString("desc");

        editTextNombreGrupo.setText(nombre);
        editTextDescripcionGrupo.setText(desc);
    }
    public void UpdateRegister(View view){

        Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();

    }
}
