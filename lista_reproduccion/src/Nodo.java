public class Nodo {
    T dato;
    Nodo siguiente, anterior;

    public Nodo(T dato){
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }
}