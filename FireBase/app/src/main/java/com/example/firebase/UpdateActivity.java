package com.example.firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateActivity extends AppCompatActivity {

    EditText lblInfDat, lblEtiqInfDat;

    Button btnGuardar;

    private DatabaseReference dbPrediccion;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dbPrediccion = FirebaseDatabase.getInstance("https://fir-clase-88551-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference()
                .child("Avisos");

        lblInfDat = (EditText) findViewById(R.id.lblInfDat);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbPrediccion.child("Informática")
                                .setValue(lblInfDat.getText().toString());
                    }
                }
        );
    }
}
