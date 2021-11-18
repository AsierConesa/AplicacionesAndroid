package com.example.aplicaciontrabajo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RVAdapterQuedada extends RecyclerView.Adapter<RVAdapterQuedada.ViewHolder> {

    private LayoutInflater inflador;

    protected List<Quedada> quedadas;

    public RVAdapterQuedada(Context contexto, List<Quedada> quedadas){
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.quedadas = quedadas;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView nombreQuedada;
        TextView descripcionQuedada;
        TextView nombreGrupo;

        ViewHolder(View itemView){
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            nombreQuedada = (TextView) itemView.findViewById(R.id.person_name);
            descripcionQuedada = (TextView) itemView.findViewById(R.id.miembros);
            nombreGrupo = (TextView) itemView.findViewById(R.id.groupName);

        }
    }

    //1º CREAMOS LA VISTA SIN PERSONALIZAR CON DATOS
    //Ya devuelve un ViewHolder

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inflador.inflate(R.layout.elemento_individual_quedada, null);

        RVAdapterQuedada.ViewHolder pvh = new RVAdapterQuedada.ViewHolder(v);

        return pvh;
    }
    //2º RELLENAR LOS DATOS DEL VIEWHOLDER
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final int posicion=i;

        viewHolder.nombreQuedada.setText(quedadas.get(posicion).nombre);
        viewHolder.descripcionQuedada.setText(String.valueOf(quedadas.get(posicion).descripcion));
        viewHolder.nombreGrupo.setText("Nombre del grupo cuyo codigo es el idgrupo");

        //Asignamos un listener
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), quedadas.get(posicion).nombre, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
