package com.example.aplicaciontrabajo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainQuedadas extends AppCompatActivity {

    SQLiteDatabase bbdd;
    bbddGrupos conexion;

    private List<Quedada> quedadas;

    private RecyclerView.LayoutManager llm;

    private RVAdapterQuedada adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quedadas);

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
        adapter = new RVAdapterQuedada(this, quedadas);

        rv.setAdapter(adapter);
    }

    public void crearNuevoElemento(View view){

        Intent i = new Intent(this, NuevoElemento.class);
        startActivity(i);

    }

    public void pasarAGrupos(View view){

        Intent i = new Intent(this, MainGrupos.class);
        startActivity(i);

    }

    private void inicializarDatos(){

        String[] camposMostrar = new String[]{"nombrequedada","descripcionquedada","idgrupo"};
        quedadas = new ArrayList<>();

        if (bbdd != null){
            //Tabla-Campos-Where-ValorWhere-GroupBy-Having-OrderBy
            Cursor c1 = bbdd.query("quedadas",camposMostrar,null,null,null,null,null);

            if(c1.moveToFirst()){
                do{
                    //SELECT NOMBREGRUPO FROM GRUPOS WHERE COD_GRUPO = 0;
                    Cursor c2 = bbdd.query("grupos",new String[]{"nombregrupo"},"cod_grupo = ?",new String[]{c1.getString(2)},null,null,null);
                    String par1 = c1.getString(0);
                    String par2 = c1.getString(1);
                    c2.moveToFirst(); //BIEEEEEN
                    String par3 = c2.getString(0);
                    quedadas.add(new Quedada(par1, par2, par3));
                }while (c1.moveToNext());
            }
        }
    }

}
