package com.example.aplicaciontrabajo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NuevoElemento extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_elemento);
    }

    public void crearNuevoGrupo(View view){

        Intent i = new Intent(this, NuevoGrupo.class);
        startActivity(i);

    }

    public void crearNuevaQuedada(View view){

        Intent i = new Intent(this, NuevaQuedada.class);
        startActivity(i);

    }

}