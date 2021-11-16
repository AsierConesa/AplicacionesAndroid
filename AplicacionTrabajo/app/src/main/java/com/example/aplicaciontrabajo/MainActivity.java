package com.example.aplicaciontrabajo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase bbdd;
    bbddGrupos conexion;

    private List<Grupo> grupos;

    private RecyclerView.LayoutManager llm;

    private RVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);

        rv.setHasFixedSize(true);

        llm = new LinearLayoutManager(this);
        //llm = new GridLayoutManager(this, 2);

        rv.setLayoutManager(llm);

        //crear base de datos:
        conexion = new bbddGrupos(this,"bbddEmpresa",null,1);
        bbdd = conexion.getWritableDatabase(); //modo escritura


        //inserta grupo de ejemplo ¡ESTO SOLO QUIERO QUE LO HAGA LA PRIMERA VEZ QUE ENTRA A LA APP!
        int codigoGrupo = 0;
        String nombreGrupo = "Grupo de ejemplo";

        if(bbdd!=null){
            String sql = ("INSERT INTO GRUPOS(COD_GRUPO,NOMBREGRUPO) VALUES(?,?)");

            SQLiteStatement statement = bbdd.compileStatement(sql);

            statement.clearBindings();

            statement.bindLong(1,codigoGrupo);
            statement.bindString(2,nombreGrupo);

            statement.executeInsert();
        }

        inicializarDatos();

        //se pasan los datos de la base de datos al adapter
        adapter = new RVAdapter(this, grupos);

        rv.setAdapter(adapter);


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