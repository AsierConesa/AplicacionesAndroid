package com.example.aplicaciontrabajo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class NuevoElemento extends AppCompatActivity {

    ConstraintLayout lay_background;
    TextView textView;
    Button btn_meeting;

    SQLiteDatabase bbdd;
    bbddGrupos conexion;

    private List<Grupo> grupos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_elemento);

        lay_background = findViewById(R.id.lay_backgroundelemento);
        textView = findViewById(R.id.textViewElemento);

        btn_meeting = findViewById(R.id.btnNewMeet);

        //consulta a la base de datos un cursor con todos los grupos
        //si el cursor trae contenido, desbloquea el boton meeting, si no ocultalo

        String[] camposMostrar = new String[]{"nombregrupo","descripciongrupo"};
        grupos = new ArrayList<>();

        if (bbdd != null){
            //Tabla-Campos-Where-ValorWhere-GroupBy-Having-OrderBy
            Cursor c1 = bbdd.query("Grupos",camposMostrar,null,null,null,null,null);

            if(c1.moveToFirst()){
                btn_meeting.setVisibility(View.VISIBLE);
            }
            else{
                btn_meeting.setVisibility(View.INVISIBLE);
            }
        }

        loadPref();
    }

    public void crearNuevoGrupo(View view){

        Intent i = new Intent(this, NuevoGrupo.class);
        startActivity(i);

    }

    private void loadPref(){
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean dark;
        dark = mySharedPreferences.getBoolean("theme", false);

        if(dark){
            lay_background.setBackgroundColor(Color.BLACK);
            textView.setTextColor(Color.WHITE);
        }
        else{
            lay_background.setBackgroundColor(Color.WHITE);
            textView.setTextColor(Color.BLACK);
        }
    }

    public void crearNuevaQuedada(View view){

        Intent i = new Intent(this, NuevaQuedada.class);
        startActivity(i);

    }

}