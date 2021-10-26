package com.example.nuevaappclase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText precio;
    private EditText iva;

    private TextView resultado;

    private Button bCalcularIvaV2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        precio = (EditText) findViewById(R.id.editPrecio);
        iva = (EditText) findViewById(R.id.editIVA);
        resultado = (TextView) findViewById(R.id.ivaCalculado);

        bCalcularIvaV2 = findViewById(R.id.button2);

        bCalcularIvaV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calcular();

            }
        }
        );

        resultado.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                calcular();
                return true;

            }
        });

    }


    public void sePulsa (View view){

        calcular();

    }

    private void calcular(){

        double resultado_temporal = 0;

        resultado_temporal = Double.parseDouble(precio.getText().toString());
        resultado_temporal = resultado_temporal + Double.parseDouble(iva.getText().toString()) * ((Double.parseDouble(precio.getText().toString())/100));

        resultado.setText(String.valueOf(resultado_temporal));

    }
}