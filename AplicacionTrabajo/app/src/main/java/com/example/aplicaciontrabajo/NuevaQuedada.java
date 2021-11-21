package com.example.aplicaciontrabajo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class NuevaQuedada extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextDescripcion;
    private List<Quedada> quedadas;
    private SQLiteDatabase bbdd;
    private bbddGrupos conexion;
    private RecyclerView rv;


    ConstraintLayout lay_background;
    TextView title;
    TextView tvname;
    TextView tvdesc;
    EditText etname;
    EditText etdesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_quedada);

        lay_background = findViewById(R.id.lay_backgroundquedada);

        title = findViewById(R.id.tv_title);
        tvname = findViewById(R.id.tv_name);
        tvdesc = findViewById(R.id.tv_desc);
        etname = findViewById(R.id.et_name);
        etdesc = findViewById(R.id.et_desc);

        editTextNombre = findViewById(R.id.et_name);
        editTextDescripcion = findViewById(R.id.et_desc);
        rv = findViewById(R.id.rv);

        loadPref();

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
            etname.setHintTextColor(Color.GRAY);
            etdesc.setHintTextColor(Color.GRAY);
        }
        else{
            lay_background.setBackgroundColor(Color.WHITE);
            title.setTextColor(Color.BLACK);
            tvname.setTextColor(Color.BLACK);
            tvdesc.setTextColor(Color.BLACK);
            etname.setTextColor(Color.BLACK);
            etdesc.setTextColor(Color.BLACK);
            etname.setHintTextColor(Color.GRAY);
            etdesc.setHintTextColor(Color.GRAY);
        }

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





    }

}