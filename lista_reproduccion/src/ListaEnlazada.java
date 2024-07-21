public class ListaEnlazada {
    private Nodo cabeza, cola;
    public ListaEnlazada(){
        this.cabeza=null;
        this.cola = null;
    }
    public T insertar(T dato){
        Nodo nodo = new Nodo(dato);
        if(this.cabeza == null){
            cabeza = cola = nodo;
        }
        else {
            cola.siguiente = nodo;
            nodo.anterior = cola;
            cola = nodo;
        }
        return n.dato;
     }
}