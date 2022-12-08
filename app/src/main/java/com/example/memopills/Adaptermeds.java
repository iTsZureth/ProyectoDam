package com.example.memopills;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memopills.Objetos.Medicamentos;
import com.example.memopills.Objetos.Tratamientos;

import java.util.ArrayList;


public class Adaptermeds extends RecyclerView.Adapter<Adaptermeds.ViewHolder> {
    private ArrayList<Medicamentos> listafilas;
    public Adaptermeds(ArrayList<Medicamentos> dataSet){
        listafilas = dataSet;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        //Atributos
        private final TextView nombre;
        private final TextView tiempo;
        private final TextView observaciones;
        private final TextView idmed;


        public ViewHolder(View itemView) {

            super(itemView);
            idmed = (TextView) itemView.findViewById(R.id.idlista);
            nombre = (TextView) itemView.findViewById(R.id.nomlistahist);
            tiempo = (TextView) itemView.findViewById(R.id.tiemplista);
            observaciones = (TextView) itemView.findViewById(R.id.observacioneslist);
        }
        public TextView getIdmed(){return idmed;}
        public TextView getNombre() {return nombre;}
        public TextView getTiempo() { return tiempo;}
        public TextView getObservaciones(){ return observaciones;}

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filahist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //conseguir datos del dataset
        holder.getIdmed().setText(listafilas.get(position).getId());
        holder.getNombre().setText(listafilas.get(position).getNombre());
        holder.getTiempo().setText(listafilas.get(position).getTiempo() + "h aprox.");
        holder.getObservaciones().setText(listafilas.get(position).getObservaciones());



    }

    @Override
    public int getItemCount() {
        return listafilas.size();
    }

}

