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
    public ListaEnlazada reproduccionAleatoria() {
        ListaEnlazada listaA = new ListaEnlazada();
        Set<Integer> usados = new HashSet<>();

        for (int i = 0; i < tabla.size(); i++) {
            int random;
            do {
                random = (int) (Math.random() * tabla.size());
            } while (usados.contains(random));
            usados.add(random);
            listaA.agregarCancion(tabla.get(random));
        }
        return listaA;
    }

    public Lista<Cancion> obtenerTodasLasCanciones() {
        Lista<Cancion> lista = new Lista<>();
        for (Cancion cancion : tabla.values()) {
            lista.agregar(cancion);
        }
        return lista;
    }
}