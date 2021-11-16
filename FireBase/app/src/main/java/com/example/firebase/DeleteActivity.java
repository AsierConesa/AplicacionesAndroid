package com.example.firebase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteActivity extends AppCompatActivity {

    Button btnEliminar, btnNulo;
    private DatabaseReference dbPrediccion;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        dbPrediccion = FirebaseDatabase.getInstance("https://fir-clase-88551-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference()
                .child("Avisos");

        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnNulo = (Button) findViewById(R.id.btnNulo);

        btnEliminar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbPrediccion.child("Informática").removeValue();
                    }
                }
        );
        btnNulo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dbPrediccion.child("Informática").setValue(null);
                    }
                }
        );
    }
}
