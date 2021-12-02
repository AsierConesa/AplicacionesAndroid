package com.example.aplicaciontrabajo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RVAdapterGroup extends RecyclerView.Adapter<RVAdapterGroup.ViewHolder> {

    private LayoutInflater inflador;

    protected List<Grupo> grupos;

    private static Context contexto;

    public RVAdapterGroup(Context contexto, List<Grupo> grupos){
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.grupos = grupos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView nombreGrupo;
        TextView nMiembros;
        ImageView groupPhoto;

        ViewHolder(View itemView){
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            nombreGrupo = (TextView) itemView.findViewById(R.id.person_name);
            nMiembros = (TextView) itemView.findViewById(R.id.miembros);
            groupPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
        }
    }




    //1º CREAMOS LA VISTA SIN PERSONALIZAR CON DATOS
    //Ya devuelve un ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = inflador.inflate(R.layout.elemento_individual_grupo, null);

        ViewHolder pvh = new ViewHolder(v);

        return pvh;
    }

    //2º RELLENAR LOS DATOS DEL VIEWHOLDER
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        final int posicion=i;

        viewHolder.nombreGrupo.setText(grupos.get(posicion).nombre);
        viewHolder.nMiembros.setText(String.valueOf(grupos.get(posicion).descripcion));
        viewHolder.groupPhoto.setImageResource(grupos.get(posicion).photoId);

    //Asignamos un listener
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                Intent i = new Intent(view.getContext(), EditGrupo.class);
                i.putExtra("nombre",viewHolder.nombreGrupo.getText().toString());
                i.putExtra("desc",viewHolder.nMiembros.getText().toString());
                view.getContext().startActivity(i);

            }
        });

    }

    //3º INDICAMOS EL NÚMERO DE ELEMENTOS A VISUALIZAR
    @Override
    public int getItemCount() {
        return grupos.size();
    }
}
