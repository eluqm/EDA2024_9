package com.canciones;

import java.util.Iterator;

public class Lista<T> implements Iterable<T> {
    NodoLista<T> head;

    public Lista() {
        this.head = null;
    }
    public void agregar(T dato) {
        NodoLista<T> n = new NodoLista<T>(dato);
        if (this.head == null) {
            this.head = n;
        } else {
            NodoLista<T> temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = n;
        }

    }
    private int size() {
        int size = 0;
        NodoLista<T> temp = head;
        while (temp != null) {
            size++;
            temp = temp.next;
        }
        return size;
    }
    public T[] toArray(T[] array) {
        if (array.length < size()) {
            // Si el array proporcionado es demasiado pequeño, se crea uno nuevo
            array = (T[]) java.lang.reflect.Array.newInstance(array.getClass().getComponentType(), size());
        }

        NodoLista<T> temp = head;
        int i = 0;
        while (temp != null) {
            array[i++] = temp.dato;
            temp = temp.next;
        }

        if (array.length > size()) {
            array[size()] = null;
        }

        return array;
    }
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private NodoLista<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T data = current.dato;
                current = current.next;
                return data;
            }
        };
    }

}