package com.canciones;

public class HashMap<K, V> {
    private Entry<K, V>[] tabla;
    private int size;
    private int count;
    private static final int capacidad = 1000;
    private static final float factor_carga = 0.75f;
    @SuppressWarnings("unchecked")
    public HashMap (){
        this.size = capacidad;
        this.tabla = new Entry[capacidad];
    }
    private int hash(K key){
        return key.hashCode() % (size);
    }
    public int size(){
        return count;
    }
    public void put(K key, V value){
        if (count >= size * factor_carga) {
            resize();
        }
        int hash = hash(key);
        Entry<K, V> nEntry = new Entry<>(key, value);

        if(tabla[hash] == null){
            tabla[hash] = nEntry;
        }
        else{
            Entry<K, V> actual = tabla[hash];
            Entry<K, V> anterior = null;
            while (actual != null) {
                if (actual.key.equals(key)) {
                    actual.value = value;
                    return;
                }
                anterior = actual;
                actual = actual.next;
            }
            anterior.next = nEntry;
        }
        count++;
    }
    public V get(K key){
        int hash = hash(key);
        if (tabla[hash] == null) {
            return null;
        }
        else{
            Entry<K, V> actual = tabla[hash];
            while (actual != null) {
                if (actual.key.equals(key)) {
                    return actual.value;
                }
                actual = actual.next;
            }
            return null;
        }
    }

    public void remove(K key){
        int hash = hash(key);
        if (tabla[hash] == null) {
            return;
        }
        else{
            Entry<K , V> actual = tabla[hash];
            Entry<K, V> anterior = null;
            while (actual != null) {
                if (actual.key.equals(key)) {
                    if (anterior == null) {
                        tabla[hash]= actual.next;
                    }
                    else{
                        anterior.next = actual.next;
                    }
                    count--;
                    return;
                }
                anterior = actual;
                actual = actual.next;
            }
        }
    }
    @SuppressWarnings("unchecked")
    private void resize(){
        int nSize = size*2;
        Entry<K, V>[] nTabla = new Entry[nSize];
        Entry<K, V>[] aTabla = tabla;

        size = nSize;
        tabla = nTabla;
        count = 0;
        for(Entry<K, V> entry : aTabla){
            if (entry != null) {
                Entry<K, V> actual = entry;
                while (actual != null) {
                    put(actual.key, actual.value);
                    actual = actual.next;
                }
            }
        }
    }
}