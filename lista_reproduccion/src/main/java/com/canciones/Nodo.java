package com.canciones;
public class Nodo{
    Cancion dato;
    Nodo siguiente, anterior;

    public Nodo(Cancion dato){
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }
}