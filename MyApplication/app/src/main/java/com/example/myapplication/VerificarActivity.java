package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class VerificarActivity extends AppCompatActivity {

    TextView pregunta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar);

        Bundle extras = getIntent().getExtras();
        String s = extras.getString("usuario");
        s = "¡Hola "+s+"! ¿Aceptas las condiciones?";
        pregunta = findViewById(R.id.textViewPregunta);
        pregunta.setText(s);

    }

    public void Unificar (View view){
        Intent i = new Intent();
        if(view.getId()==R.id.buttonAceptar){
            i.putExtra("resultado","OK");
        }
        else if(view.getId()==R.id.buttonRechazar){
            i.putExtra("resultado","No aceptadas");
        }
        setResult(RESULT_OK, i);
        finish();
    }

    public void Aceptar (View view){
        Intent i = new Intent();
        i.putExtra("resultado","OK");
        setResult(RESULT_OK, i);
        finish();
    }
    public void Rechazar (View view){
        Intent i = new Intent();
        i.putExtra("resultado","No aceptadas");
        setResult(RESULT_OK, i);
        finish();
    }
    public void Cancelar (View view){
        Intent i = new Intent();
        setResult(RESULT_CANCELED, i);
        finish();
    }
}