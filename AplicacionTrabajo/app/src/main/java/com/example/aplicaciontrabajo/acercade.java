package com.example.aplicaciontrabajo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class acercade extends AppCompatActivity {

    private ValueEventListener eventListener;
    private DatabaseReference dbPrediccion;

    private static String TAGLOG = "firebase-db";

    TextView lbldate;
    TextView lblMainAcercaDe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acercade);

        //se coge el json
        dbPrediccion = FirebaseDatabase.getInstance("https://aplicaciontrabajofirebase-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference()
                .child("values");

        lbldate = findViewById(R.id.lbldateVariable);
        lblMainAcercaDe = findViewById(R.id.lblmain);

        //se inserta el texto del acercade, desde un archivo de recursos

        InputStream is = this.getResources().openRawResource(R.raw.textoacercade);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);

        String content = "";
        try {
            content = br.readLine();
        } catch (IOException e) {
            Log.i("info","el archivo no tenía texto");
        }

        lblMainAcercaDe.setText(content);

        //listener onchange
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lbldate.setText("");
                //se cambia el texto del textview a lo obtenido de firebase
                if(dataSnapshot.child("date").exists()){
                    lbldate.setText(dataSnapshot.child("date").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAGLOG,"Error", databaseError.toException());
            }
        };

        dbPrediccion.addValueEventListener(eventListener);

    }


}