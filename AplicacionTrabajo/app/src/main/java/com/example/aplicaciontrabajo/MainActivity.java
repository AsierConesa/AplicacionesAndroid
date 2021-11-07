package com.example.aplicaciontrabajo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Grupo> grupos;

    private RecyclerView.LayoutManager llm;

    private RVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);

        rv.setHasFixedSize(true);

        llm = new LinearLayoutManager(this);
        //llm = new GridLayoutManager(this, 2);

        rv.setLayoutManager(llm);

        inicializarDatosEjemplo();

        adapter = new RVAdapter(this, grupos);

        rv.setAdapter(adapter);
    }

    private void inicializarDatosEjemplo(){
        grupos = new ArrayList<>();

        grupos.add(new Grupo("JLA", 10, R.drawable.ic_adb_64));
        grupos.add(new Grupo("Los Mosca", 5, R.drawable.ic_alarm_add_64));
        grupos.add(new Grupo("DAM 2", 21, R.drawable.ic_android_64));
        grupos.add(new Grupo("Grupo Madrid", 7, R.drawable.ic_adb_64));
        grupos.add(new Grupo("Los VGA", 7, R.drawable.ic_alarm_add_64));
        grupos.add(new Grupo("Quedadas Londres", 6, R.drawable.ic_android_64));
        grupos.add(new Grupo("Amigos Infancia", 70, R.drawable.ic_adb_64));
        grupos.add(new Grupo("Piso", 3, R.drawable.ic_alarm_add_64));
        grupos.add(new Grupo("Purr Group", 16, R.drawable.ic_android_64));
        grupos.add(new Grupo("Quedada Domingo", 14, R.drawable.ic_adb_64));
        grupos.add(new Grupo("Navidad en el pueblo", 7, R.drawable.ic_alarm_add_64));
        grupos.add(new Grupo("Los Mejores", 12, R.drawable.ic_android_64));

    }
}