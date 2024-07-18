public class Nodo {
        T key;
        Nodo left, right;
        int height;
    
        public AVLNode(T key) {
            this.key = key;
            this.left = null;
            this.right = null;
            this.height = 1;
        }
}