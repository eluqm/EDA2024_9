package com.canciones;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaEnlazada implements Iterable<Cancion> {
    private Nodo cabeza, cola;
    private int size;

    public ListaEnlazada() {
        this.cabeza = null;
        this.cola = null;
        this.size = 0;
    }

    public ListaEnlazada(ListaEnlazada otraLista) {
        this();
        for (Cancion elemento : otraLista) {
            agregarCancion(elemento);
        }
    }

    public Cancion agregarCancion(Cancion dato) {
        Nodo nodo = new Nodo(dato);
        if (this.cabeza == null) {
            cabeza = cola = nodo;
        } else {
            cola.siguiente = nodo;
            nodo.anterior = cola;
            cola = nodo;
        }
        size++;
        return nodo.dato;
    }

    public void eliminar(Cancion dato) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.dato.equals(dato)) {
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

    public int size() {
        return size;
    }

    public Cancion get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Nodo actual = cabeza;
        for (int i = 0; i < index; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }

    public void set(int index, Cancion cancion) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Nodo actual = cabeza;
        for (int i = 0; i < index; i++) {
            actual = actual.siguiente;
        }
        actual.dato = cancion;
    }

    public void clear() {
        cabeza = null;
        cola = null;
        size = 0;
    }

    @Override
    public Iterator<Cancion> iterator() {
        return new Iterator<Cancion>() {
            private Nodo actual = cabeza;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public Cancion next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Cancion cancion = actual.dato;
                actual = actual.siguiente;
                return cancion;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}