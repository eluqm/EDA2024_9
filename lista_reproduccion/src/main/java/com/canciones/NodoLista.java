package com.canciones;

public class NodoLista<T> {
    T dato;
    NodoLista<T> next;

    public NodoLista(T dato) {
        this.dato = dato;
        this.next = null;
    }
}
