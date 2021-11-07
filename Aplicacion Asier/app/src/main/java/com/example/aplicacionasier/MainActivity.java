package com.example.aplicacionasier;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_proximas_citas);

    }
    public void btnNuevoGrupo(View v){
        if(v.equals(findViewById(R.id.btnNuevoGrupo))){
            //Intent intent = new Intent((R.layout.activity_nuevo_grupo));
        }
    }
}