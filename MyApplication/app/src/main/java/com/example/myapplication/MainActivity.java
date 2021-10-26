package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText nombreUsuario;
    TextView resultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreUsuario = findViewById(R.id.editTextPersonName);
        resultado = findViewById(R.id.textViewResultado);
    }

    public void lanzarAcercaDe(View view){

        Intent i = new Intent(this, AcercaDeActivity.class);
        startActivity(i);
    }

    public void lanzarVerifica(View view){

        Intent i = new Intent(this, VerificarActivity.class);

        String usuarioValue = nombreUsuario.getText().toString();

        i.putExtra("usuario",usuarioValue);

        startActivityForResult(i, 1234);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        resultado.setText("");
        if(requestCode == 1234 && resultCode == RESULT_OK){
            String res = data.getExtras().getString("resultado");
            resultado.setText(res);
        }
    }
}