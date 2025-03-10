package com.example.tfg;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EquipoAdapter extends RecyclerView.Adapter<EquipoAdapter.ViewHolder> {
    private List<Equipo> equipoList;

    private Context context;

    public EquipoAdapter(List<Equipo> equipoList, Context context) {
        this.equipoList = (equipoList != null) ? equipoList : new ArrayList<> ();
        this.context =  context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.equipo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.equipoNombre.setText(equipoList.get(position).getNombre());
        holder.equipoImagen.setImageResource(equipoList.get(position).getImagenResId());
        holder.itemView.setOnClickListener(v -> {

            if (position == 0) {

                context.startActivity(new Intent(context, AnadirJugador.class));
            } else {
                context.startActivity(new Intent(context, GestionarEquipo.class));
            }

        });
    }

    @Override
    public int getItemCount() {
        return equipoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView equipoImagen;
        TextView equipoNombre;


        public ViewHolder(View itemView) {
            super(itemView);
            equipoImagen = itemView.findViewById(R.id.equipo_imagen);
            equipoNombre = itemView.findViewById(R.id.equipo_nombre);
        }
    }
}