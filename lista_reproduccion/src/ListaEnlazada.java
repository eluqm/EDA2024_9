public class ListaEnlazada {
    private Nodo cabeza, cola;
    private int size;
    public ListaEnlazada(){
        this.cabeza=null;
        this.cola = null;
        this.size = 0;
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
        size++;
        return n.dato;
     }
     public void eliminar(T dato){
        Nodo actual =cabeza;
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
}