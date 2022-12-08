package com.example.memopills;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memopills.Objetos.Pastillas;

import java.util.ArrayList;


    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements View.OnLongClickListener {
        private ArrayList<Pastillas> listafilas;
        private View.OnLongClickListener listener;
        public Adapter(ArrayList<Pastillas> dataSet){
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
            private final TextView cantidad;
            private final TextView tiempo;
            public ViewHolder(View itemView) {

                super(itemView);
                nombre = (TextView) itemView.findViewById(R.id.filanombre);
                cantidad = (TextView) itemView.findViewById(R.id.filacantidad);
                tiempo = (TextView) itemView.findViewById(R.id.filatiempo);
            }//Fin ViewHolder
            public TextView getNombre() {return nombre;}
            public TextView getCantidad() { return cantidad;}
            public TextView getTiempo(){ return tiempo;}


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
            holder.getCantidad().setText(listafilas.get(position).getCantidad());
            holder.getTiempo().setText(listafilas.get(position).getTiempo());
        }

        @Override
        public int getItemCount() {
            return listafilas.size();
        }

    }

