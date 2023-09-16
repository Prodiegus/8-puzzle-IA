import java.util.ArrayList;

/**
  * crearemos una clase que será la encargada
  * de ser el arbol de movimientos de la busqueda
  */

public class Arbol{
    ArrayList<movimiento> cadenaMovimientos;
    ArrayList<Arbol> hijos;
    Arbol padre;
    int profundidad;
    boolean explorado;
    Arbol(){
        this.cadenaMovimientos = new ArrayList<>();
        this.hijos = new ArrayList<>();
        this.profundidad = 0;
        this.explorado = false;
    }
    
    // agregamos una funcion para agregar un movimiento al arbol
    public void addMovimiento(movimiento movimiento){
        cadenaMovimientos.add(movimiento);
    }

    // agregamos una funcion para agregar un hijo al arbol
    public void addHijo(movimiento movimiento, int profundidad){
        Arbol hijArbol = new Arbol();
        hijArbol.padre = this;
        hijArbol.profundidad = profundidad;
        if (!cadenaMovimientos.isEmpty()) {
         for (movimiento mov : cadenaMovimientos) {
            hijArbol.addMovimiento(mov);
          } 
        }
        hijArbol.addMovimiento(movimiento);
        hijos.add(hijArbol);
    }

    // agregamos una funcion para obtener los movimientos del nodo
    public ArrayList<movimiento> getMovimientos(){
        return cadenaMovimientos;
    }

    // agregamos una funcion para obtener los hijos del nodo
    public ArrayList<Arbol> getHijos(){
        return hijos;
    }

    // agregamos una funcion para obtener el padre del nodo
    public Arbol getPadre(){
        return padre;
    }

    public boolean explorado(){
        return this.explorado;
    }
    public void explorando(){
        this.explorado = true;
    }
}
