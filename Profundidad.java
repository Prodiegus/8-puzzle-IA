import java.util.ArrayList;

/**
 * En esta clase implementaremos un algoritmo de busqueda en anchura
 * para resolver el puzzle, guardaremos los nodos (movimientos) en una
 * lista de movimientos para al final mostrarlos en orden correcto
 * El algoritmo de busqueda en anchura es un algoritmo de busqueda
 * no informada que expande y examina todos los nodos de un nivel
 * antes de pasar al siguiente nivel de nodos  
 */
public class Profundidad {
    ArrayList<movimiento> solucion;
    Arbol arbolMovimientos;
    puzzle puzzle;
    int movimientosMaximos;
    int profundidad;


    Profundidad(int movimientosMaximos, puzzle puzzle){
        this.movimientosMaximos = movimientosMaximos;
        this.solucion = new ArrayList<>();
        this.profundidad = 0;
        this.arbolMovimientos = new Arbol();
        this.puzzle = puzzle;
    }
    Profundidad(puzzle puzzle){
        this.solucion = new ArrayList<>();
        this.movimientosMaximos = 50;
        this.profundidad = 0;
        this.arbolMovimientos = new Arbol();
        this.puzzle = puzzle;
    }

    // haremos una funcion para ejecutar los movimientos de la solucion
    public void ejecutarSolucion(ArrayList<movimiento> solucion){
        for (movimiento movimiento : solucion) {
            puzzle.mover(movimiento.xDestino, movimiento.yDestino, movimiento.xOrigen, movimiento.yOrigen);
        }
        puzzle.movimientos = 0;
    }

    // haremos una funcion para volver la matriz a su estado original
    public void volverEstadoOriginal(ArrayList<movimiento> solucion){
        for (int i = solucion.size() - 1; i >= 0; i--) {
            movimiento movimiento = solucion.get(i);
            puzzle.mover(movimiento.xOrigen, movimiento.yOrigen, movimiento.xDestino, movimiento.yDestino);
        }
        puzzle.movimientos = 0;
    }

    /**
     * Para implementar el algoritmo de busqueda en anchura en este caso
     * primero buscaremos cuales son los movimientos validos que se pueden
     * ejecutar en el siguiente nivel de profundidad, para ello crearemos
     * una funcion que nos devuelva los movimientos validos que se pueden
     * ejecutar en el siguiente nivel de profundidad
     * 
     */

    public ArrayList<movimiento> getMovimientosValidos(Arbol nodo){
        ArrayList<movimiento> movimientosValidos = new ArrayList<>();
        // obtenemos la matriz actual
        ejecutarSolucion(nodo.getMovimientos());
        //System.out.println("Profundidad: "+nodo.profundidad+" Movimientos: "+nodo.getMovimientos().size()+" Movimientos Maximos: "+movimientosMaximos+"");
        //puzzle.showMatriz();
        String[][] matriz = puzzle.getMatriz();
        // recorremos la matriz
        for (int i = 0; i < matriz.length; i++) {
            String[] fila = matriz[i];
            for (int j = 0; j < fila.length; j++) {
                String bloque = fila[j];
                // verificamos si el bloque es el vacio
                if (bloque != null){
                    // si el bloque no es el vacio verificamos si se puede mover
                    // hacia arriba
                    if (i - 1 >= 0){
                        if (puzzle.esValido(j, i - 1, j, i)){
                            // agregamos el movimiento a la lista de movimientos validos
                            movimientosValidos.add(new movimiento(j, i - 1, j, i));
                        }
                    }
                    // hacia abajo
                    if (i + 1 < matriz.length){
                        if (puzzle.esValido(j, i + 1, j, i)){
                            // agregamos el movimiento a la lista de movimientos validos
                            movimientosValidos.add(new movimiento(j, i + 1, j, i));
                        }
                    }
                    // hacia la izquierda
                    if (j - 1 >= 0){
                        if (puzzle.esValido(j - 1, i, j, i)){
                            // agregamos el movimiento a la lista de movimientos validos
                            movimientosValidos.add(new movimiento(j - 1, i, j, i));
                        }
                    }
                    // hacia la derecha
                    if (j + 1 < fila.length){
                        if (puzzle.esValido(j + 1, i, j, i)){
                            // agregamos el movimiento a la lista de movimientos validos
                            movimientosValidos.add(new movimiento(j + 1, i, j, i));
                        }
                    }
                }
            }
        }
        volverEstadoOriginal(nodo.getMovimientos());
        //puzzle.showMatriz();
        return movimientosValidos;
    }

    /**
     * Ahora agregaremos al un nivel de profundidad usando la funcion
     * anterior, para ello crearemos una funcion que agregue un nivel
     * de profundidad al arbol de movimientos
     *
     */
    public void addProfundidad(Arbol arbol, int profundidad){
        // aumentamos la profundidad
        //System.out.println("Profundidad: "+profundidad+" Movimientos: "+arbol.getMovimientos().size()+" Movimientos Maximos: "+movimientosMaximos+"");
        profundidad++;
        if (profundidad > movimientosMaximos) return;
        // obtenemos los movimientos validos
        ArrayList<movimiento> movimientosValidos = getMovimientosValidos(arbol);
        // agregamos los movimientos validos al arbol
        for (movimiento movimiento : movimientosValidos) {
            arbol.addHijo(movimiento, profundidad);
        }
        // si la profundidad es menor al maximo de movimientos
        // verificamos si alguno de los hijos del arbol es la solucion
        if (verificarCadenas(arbol)) return;
    }

    /**
     * Ahora verificaremos cada una de las cadenas de movimientos
     * para cada uno de los nuevos hijos del arbol
     */
    public boolean verificarCadenas(Arbol arbolMovimientos){
        //System.out.print("Verificando cadenas | ");
        // obtenemos los hijos del arbol
        ArrayList<Arbol> hijos = arbolMovimientos.getHijos();
        // verificamos si el arbol tiene hijos
        // recorremos los hijos
        //System.out.println("Hijos: "+hijos.size());
        for (Arbol hijo : hijos) {
            // obtenemos los movimientos del hijo
            ArrayList<movimiento> movimientos = hijo.getMovimientos();
            //System.out.println("Profundidad: "+hijo.profundidad+" Movimientos: "+movimientos.size()+" Movimientos Maximos: "+movimientosMaximos+"");
            // recorremos los movimientos
            for (movimiento movimiento : movimientos) {
                // movemos el bloque
                //System.out.println("Moviendo: "+movimiento.xOrigen+", "+movimiento.yOrigen+" -> "+movimiento.xDestino+", "+movimiento.yDestino);
                puzzle.mover(movimiento.xDestino, movimiento.yDestino, movimiento.xOrigen, movimiento.yOrigen);
            }
            // verificamos si el puzzle esta resuelto
            if(movimientos.size()>1){
                //puzzle.showMatriz();
                //System.out.println("esFinal: "+puzzle.esFinal());
            }
            if (puzzle.esFinal()){
                // si el puzzle esta resuelto guardamos la solucion
                // System.out.println("Solucion encontrada");
                solucion = movimientos;
                // salimos del ciclo
                return true;
            }
            // si el puzzle no esta resuelto agregamos un nivel de profundidad
            for (int i = movimientos.size() - 1; i >= 0; i--) {
                movimiento movimiento = movimientos.get(i);
                puzzle.mover(movimiento.xOrigen, movimiento.yOrigen, movimiento.xDestino, movimiento.yDestino);
            }
            puzzle.movimientos = 0;
            

        }
        // si ninguno de los hijos es la solucion agregamos un nivel de profundidad por cada hijo
        for (Arbol hijo : hijos) {
            addProfundidad(hijo, hijo.profundidad);
        }
        return false;
    }

    /**
     * Ahora crearemos una funcion que nos retorne la solucion del puzzle
     * ademas de ser el encargado de ejecutar el algoritmo de busqueda
     */
    public ArrayList<movimiento> getSolucion(){
        // agregamos el primer nivel de profundidad
        addProfundidad(arbolMovimientos, arbolMovimientos.profundidad);
        // retornamos la solucion
        return solucion;
    }

}