package com.canciones;
public class Nodo <T>{
    T dato;
    Nodo<T> siguiente, anterior;

    public Nodo(T dato){
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }
}