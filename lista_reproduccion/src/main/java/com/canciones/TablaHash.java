package com.canciones;

import java.util.HashSet;
import java.util.Set;

public class TablaHash {
    private HashMap<Integer, Cancion> tabla;
    private int size;

    public TablaHash() {
        this.tabla = new HashMap<>();
        this.size = 0;
    }

    public void agregarCancion(Cancion cancion) {
        tabla.put(cancion.getId(), cancion);
        size++;
    }

    public void eliminarCancion(Cancion cancion) {
        tabla.remove(cancion.getId());
        size--;
    }

    public int size() {
        return this.size;
    }
}