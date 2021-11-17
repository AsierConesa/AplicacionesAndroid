package com.example.aplicaciontrabajo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainGrupos extends AppCompatActivity {

    SQLiteDatabase bbdd;
    bbddGrupos conexion;

    private List<Grupo> grupos;

    private RecyclerView.LayoutManager llm;

    private RVAdapterGroup adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grupos);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);

        rv.setHasFixedSize(true);

        llm = new LinearLayoutManager(this);
        //llm = new GridLayoutManager(this, 2);

        rv.setLayoutManager(llm);

        //crear conexion a base de datos:
        conexion = new bbddGrupos(this,"bbddEmpresa",null,1);
        bbdd = conexion.getWritableDatabase(); //modo escritura


        inicializarDatos();

        //se pasan los datos de la base de datos al adapter
        adapter = new RVAdapterGroup(this, grupos);

        rv.setAdapter(adapter);


    }

    public void crearNuevoElemento(View view){

        Intent i = new Intent(this, NuevoElemento.class);
        startActivity(i);

    }

    private void inicializarDatos(){

        String[] camposMostrar = new String[]{"cod_grupo","nombregrupo"};
        grupos = new ArrayList<>();

        if (bbdd != null){
            //Tabla-Campos-Where-ValorWhere-GroupBy-Having-OrderBy
            Cursor c1 = bbdd.query("Grupos",camposMostrar,null,null,null,null,null);

            if(c1.moveToFirst()){
                do{
                    grupos.add(new Grupo(c1.getString(1), c1.getInt(0), R.drawable.ic_adb_64));
                }while (c1.moveToNext());
            }
        }
    }
}