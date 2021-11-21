package com.example.aplicaciontrabajo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainGrupos extends AppCompatActivity {

    SQLiteDatabase bbdd;
    bbddGrupos conexion;

    private List<Grupo> grupos;

    private RecyclerView.LayoutManager llm;

    private RVAdapterGroup adapter;

    ActivityResultLauncher<Intent> activityResultLauncher;

    ConstraintLayout lay_mainGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grupos);

        lay_mainGroup = findViewById(R.id.lay_mainGroup);

        Toolbar topAppBar = (Toolbar) findViewById(R.id.topAppBar);
        setSupportActionBar(topAppBar);

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

        loadPref();

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        loadPref();
                    }
                }
        );
    }
    //metodo para cargar las preferencias
    private void loadPref(){
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean dark;
        dark = mySharedPreferences.getBoolean("theme", false);

        if(dark)
            lay_mainGroup.setBackgroundColor(Color.BLACK);
        else
            lay_mainGroup.setBackgroundColor(Color.WHITE);
    }
    //inflar menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    //evento de seleccionar algo del menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
    //cada una de las opciones del menú hace una acción
        switch (id) {
            case R.id.preferences:

                Intent intent = new Intent(this, SettingsActivity.class);
                activityResultLauncher.launch(intent);

                return true;
            case R.id.search:

                Snackbar.make(findViewById(R.id.topAppBar), "Has elegido búsqueda", Snackbar.LENGTH_SHORT).show();

                return true;
            case R.id.contacts:

                Intent intent4 = new Intent(this, Providers.class);
                startActivity(intent4);

                return true;
            case R.id.web:

                Intent intent5 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com"));
                startActivity(intent5);

                return true;
            case R.id.email:

                Intent intent2 = new Intent(Intent.ACTION_SEND);
                intent2.setType("text/plain");
                intent2.putExtra(Intent.EXTRA_SUBJECT,"Sobre la aplicación My Group meetings");
                intent2.putExtra(Intent.EXTRA_TEXT,"Buenas tardes estimado desarrollador, le agradezco la creación de esta aplicación");
                intent2.putExtra(Intent.EXTRA_EMAIL,new String[] {"asierconesa2002@gmail.com"});
                startActivity(intent2);

                return true;
            case R.id.acercade:

                Intent intent3 = new Intent(this, acercade.class);
                startActivity(intent3);

                return true;
            default:
                return onOptionsItemSelected(item);
        }
    }

    //pasa a la activity nuevo elemento
    public void crearNuevoElemento(View view){

        Intent i = new Intent(this, NuevoElemento.class);
        startActivity(i);

    }

    //pasa a la activity quedadas
    public void pasarAQuedadas(View view){

        Intent i = new Intent(this, MainQuedadas.class);
        startActivity(i);

    }

    //se inicializan los datos de la recyclerView
    private void inicializarDatos(){

        String[] camposMostrar = new String[]{"nombregrupo","descripciongrupo"};
        grupos = new ArrayList<>();

        if (bbdd != null){
            //Tabla-Campos-Where-ValorWhere-GroupBy-Having-OrderBy
            Cursor c1 = bbdd.query("Grupos",camposMostrar,null,null,null,null,null);

            if(c1.moveToFirst()){
                do{
                    grupos.add(new Grupo(c1.getString(0), c1.getString(1), R.drawable.ic_adb_64));
                }while (c1.moveToNext());
            }
        }
    }
}