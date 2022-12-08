package com.example.memopills;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memopills.Objetos.Tratamientos;

import java.util.ArrayList;


    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements View.OnLongClickListener {
        private ArrayList<Tratamientos> listafilas;
        private View.OnLongClickListener listener;
        public Adapter(ArrayList<Tratamientos> dataSet){
            listafilas = dataSet;
        }

        public void setOnLongClickListener(View.OnLongClickListener listener){
            this.listener = listener;
        }

        @Override
        public boolean onLongClick(View view) {
            if(listener != null){
                listener.onLongClick(view);
            }
            return true;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{

            //Atributos
            private final TextView nombre;
            private final TextView nombremed;
            private final TextView tiempo;
            private final ImageView imagen;

            public ViewHolder(View itemView) {

                super(itemView);
                nombre = (TextView) itemView.findViewById(R.id.filanombre);
                nombremed = (TextView) itemView.findViewById(R.id.filamed);
                tiempo = (TextView) itemView.findViewById(R.id.filatiempo);
                imagen = (ImageView) itemView.findViewById(R.id.alarmaimagen);
            }//Fin ViewHolder
            public TextView getNombre() {return nombre;}
            public TextView getNombremed() { return nombremed;}
            public TextView getTiempo(){ return tiempo;}
            public ImageView getImagen(){ return imagen;}


        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila, parent, false);
            view.setOnLongClickListener(this);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            //conseguir datos del dataset
            holder.getNombre().setText(listafilas.get(position).getNombre());
            holder.getNombremed().setText(listafilas.get(position).getNombremed());
            holder.getTiempo().setText(listafilas.get(position).getTiempo() + " Horas");
            if(listafilas.get(position).getAlarma()==1){
                holder.getImagen().setImageResource(R.drawable.ic_alarmaon);
            }else{
                holder.getImagen().setImageResource(R.drawable.ic_alarmaoff);
            }

        }

        @Override
        public int getItemCount() {
            return listafilas.size();
        }

    }

