package com.example.aplicaciontrabajo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class NuevaQuedada extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private List<Quedada> quedadas;
    private SQLiteDatabase bbdd;
    private bbddGrupos conexion;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_quedada);

        editTextNombre = findViewById(R.id.editTextNameGrupo);
        editTextDescripcion = findViewById(R.id.editTextGroupDescription2);
        rv = findViewById(R.id.rv);

    }

    public void añadirNuevaQuedada(View view){

        //crear base de datos:
        conexion = new bbddGrupos(this,"bbddEmpresa",null,1);
        bbdd = conexion.getWritableDatabase(); //modo escritura



        //inserta quedada
        int codigoQuedada = 0;
        String nombrequedada = editTextNombre.getText().toString();
        String descripcionquedada = editTextDescripcion.getText().toString();
        int idgrupo = 0;

        if(bbdd!=null){
            String sql = ("INSERT INTO QUEDADAS(COD_QUEDADA, NOMBREQUEDADA, DESCRIPCIONQUEDADA, IDGRUPO) VALUES(?,?,?,?)");

            SQLiteStatement statement = bbdd.compileStatement(sql);

            statement.clearBindings();

            statement.bindLong(1,codigoQuedada);
            statement.bindString(2,nombrequedada);
            statement.bindString(3,descripcionquedada);
            statement.bindLong(4,idgrupo);

            statement.executeInsert();
        }

        Intent i = new Intent(this, MainQuedadas.class);
        startActivity(i);



        //cerrar activity
        //mostrar popup mostrando que se ha generado el grupo exitósamente
        //actualizar recyclerview


    }

}