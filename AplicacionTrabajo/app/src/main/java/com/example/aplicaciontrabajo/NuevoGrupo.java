package com.example.aplicaciontrabajo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class NuevoGrupo extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private List<Grupo> grupos;
    private SQLiteDatabase bbdd;
    private bbddGrupos conexion;

    ConstraintLayout lay_background;
    TextView title;
    TextView tvname;
    TextView tvdesc;
    EditText etname;
    EditText etdesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_grupo);

        lay_background = findViewById(R.id.lay_backgroundgrupo);

        editTextNombre = findViewById(R.id.et_name);
        editTextDescripcion = findViewById(R.id.et_desc);
        title = findViewById(R.id.tv_title);
        tvname = findViewById(R.id.tv_name);
        tvdesc = findViewById(R.id.tv_desc);
        etname = findViewById(R.id.et_name);
        etdesc = findViewById(R.id.et_desc);
        loadPref();
    }

    public void añadirNuevoGrupo(View view){

        //crear base de datos:
        conexion = new bbddGrupos(this,"bbddEmpresa",null,1);
        bbdd = conexion.getWritableDatabase(); //modo escritura



        //inserta grupo

        String nombreGrupo = editTextNombre.getText().toString();
        String descripcionGrupo = editTextDescripcion.getText().toString();

        if(bbdd!=null){
            Cursor c1 = bbdd.query("Grupos",new String[]{"cod_grupo"},null,null,null,null,null);

            int codigoGrupo = c1.getCount();

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





    }
    private void loadPref(){
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean dark;
        dark = mySharedPreferences.getBoolean("theme", false);

        if(dark){
            lay_background.setBackgroundColor(Color.BLACK);
            title.setTextColor(Color.WHITE);
            tvname.setTextColor(Color.WHITE);
            tvdesc.setTextColor(Color.WHITE);
            etname.setTextColor(Color.WHITE);
            etdesc.setTextColor(Color.WHITE);
        }
        else{
            lay_background.setBackgroundColor(Color.WHITE);
            title.setTextColor(Color.BLACK);
            tvname.setTextColor(Color.BLACK);
            tvdesc.setTextColor(Color.BLACK);
            etname.setTextColor(Color.BLACK);
            etdesc.setTextColor(Color.BLACK);
        }

    }


}