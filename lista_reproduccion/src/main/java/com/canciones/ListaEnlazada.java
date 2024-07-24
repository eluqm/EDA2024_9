package com.canciones;
public class ListaEnlazada <T>{
    private Nodo<T> cabeza, cola;
    private int size;
    public ListaEnlazada(){
        this.cabeza=null;
        this.cola = null;
        this.size = 0;
    }
    public T insertar(T dato){
        Nodo<T> nodo = new Nodo<>(dato);
        if(this.cabeza == null){
            cabeza = cola = nodo;
        }
        else {
            cola.siguiente = nodo;
            nodo.anterior = cola;
            cola = nodo;
        }
        size++;
        return nodo.dato;
     }
     public void eliminar(T dato){
        Nodo<T> actual =cabeza;
        while (actual != null) {
            if(actual.dato.equals(dato)){
                if (actual.anterior != null) {
                    actual.anterior.siguiente = actual.siguiente;
                }
                if (actual.siguiente != null) {
                    actual.siguiente.anterior = actual.anterior;
                }
                if (actual == cabeza) {
                    cabeza = actual.siguiente;
                }
                if (actual == cola) {
                    cola = actual.anterior;
                }
                size--;
                return;
            }
            actual = actual.siguiente;
        }
    }
    public int size(){
        return size;
    }
    public T get(int index){
        if (index < 0 || index >= size) {
            return null;
        }
        Nodo<T> actual = cabeza;
        for(int i=0; i<index; i++){
            actual = actual.siguiente;
        }
        return actual.dato;
    }
}