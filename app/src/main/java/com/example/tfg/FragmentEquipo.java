package com.example.tfg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentEquipo extends Fragment {
    private RecyclerView recyclerView;
    private EquipoAdapter adapter;
    private List<Equipo> equipoList;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipo, container, false);
        recyclerView = view.findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager (getContext()));

        equipoList = new ArrayList<> ();
        equipoList.add(new Equipo(R.string.anadirJugadores, R.drawable.anadir_jugador));
        equipoList.add(new Equipo(R.string.gestionarJugadores, R.drawable.gestionar_equipo));

        adapter = new EquipoAdapter(equipoList, requireContext ());
        recyclerView.setAdapter(adapter);


        return view;
    }
}