package com.example.tfg;

public class Equipo{
    private int nombre;

    private int imagenId;
    public Equipo(int nombre, int imagenResId) {
        this.nombre = nombre;
        this.imagenId = imagenResId;
    }

    public int getNombre() {
        return nombre;
    }
    public int getImagenResId() {
        return imagenId;
    }
}
