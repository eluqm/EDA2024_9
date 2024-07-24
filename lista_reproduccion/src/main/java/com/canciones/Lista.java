package com.canciones;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Lista<T> implements Iterable<T> {
    NodoLista<T> head;

    public Lista() {
        this.head = null;
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