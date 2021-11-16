package com.example.firebase;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class InsertVariosCamposActivity extends AppCompatActivity {

    EditText lblInfDat, lblFormDat, lblMantDat;
    Button btnGuardar;

    private DatabaseReference dbPrediccion;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_insert_varios_campos);

        dbPrediccion = FirebaseDatabase.getInstance("https://fir-clase-88551-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference();

        lblFormDat = (EditText) findViewById(R.id.lblFormDat);
        lblInfDat = (EditText) findViewById(R.id.lblInfDat);
        lblMantDat = (EditText) findViewById(R.id.lblMantDat);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
    }

    public void GuardarConObjeto (View view){
        Noticias noticia = new Noticias(
                lblFormDat.getText().toString(),
                lblInfDat.getText().toString(),
                lblMantDat.getText().toString()
        );

        dbPrediccion.push().setValue(noticia, new DatabaseReference.CompletionListener(){
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast toast;
                String mensaje;

                if(databaseError == null){
                    String uniqueKey = databaseReference.getKey();
                    mensaje = "Insertado registro con key "+uniqueKey;
                }
                else{
                    mensaje = "Error al insertar: "+databaseError.getMessage();
                }

                toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void GuardarConHashMap (View vista) {

        Map<String, Object> VariosAvisos = new HashMap<String,Object>();
        VariosAvisos.put("/Avisos/Aviso2/Formación", lblFormDat.getText().toString());
        VariosAvisos.put("/Avisos/Aviso2/Informática", lblInfDat.getText().toString());
        VariosAvisos.put("/Avisos/Aviso2/Mantenimiento", lblMantDat.getText().toString());

        dbPrediccion.updateChildren(VariosAvisos);
    }

    public void GuardarConHashMap2 (View vista) {

        Map<String, String> VariosAvisos = new HashMap<>();
        VariosAvisos.put("Informática", lblInfDat.getText().toString());
        VariosAvisos.put("Formación", lblFormDat.getText().toString());
        VariosAvisos.put("Mantenimiento", lblMantDat.getText().toString());

        dbPrediccion.child("-KzzH9KGSwc-uTcGj-cv").setValue(VariosAvisos);
    }

}
