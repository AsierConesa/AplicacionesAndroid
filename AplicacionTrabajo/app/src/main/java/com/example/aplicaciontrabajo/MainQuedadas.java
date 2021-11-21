package com.example.aplicaciontrabajo;

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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainQuedadas extends AppCompatActivity {

    SQLiteDatabase bbdd;
    bbddGrupos conexion;

    private List<Quedada> quedadas;

    private RecyclerView.LayoutManager llm;

    private RVAdapterQuedada adapter;

    ConstraintLayout lay_mainQuedadas;

    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_quedadas);

        lay_mainQuedadas = findViewById(R.id.lay_mainQuedadas);

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
        adapter = new RVAdapterQuedada(this, quedadas);

        rv.setAdapter(adapter);

        this.loadPref();
        //Se llama a la ventana de preferencias con activityResult
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
            lay_mainQuedadas.setBackgroundColor(Color.BLACK);
        else
            lay_mainQuedadas.setBackgroundColor(Color.WHITE);
    }

    //se infla el menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    //cada una de las opciones de el menú y sus acciones:
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

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

                Intent intent5 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com/"));
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

    //se llama a la activity nuevo elemento
    public void crearNuevoElemento(View view){

        Intent i = new Intent(this, NuevoElemento.class);
        startActivity(i);

    }
    //se llama a a la activity main grupos
    public void pasarAGrupos(View view){

        Intent i = new Intent(this, MainGrupos.class);
        startActivity(i);

    }

    //se sacan los datos de la bbdd y se meten en la RecyclerView
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
                    String par3 = "";
                    if(c2.moveToFirst())
                        par3 = c2.getString(0);
                    quedadas.add(new Quedada(par1, par2, par3));
                }while (c1.moveToNext());
            }
        }
    }

}
