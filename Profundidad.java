import java.util.ArrayList;

/**
 * En esta clase implementaremos un algoritmo de busqueda en profundidad
 * para resolver el puzzle, guardaremos los nodos (movimientos) en una
 * lista de movimientos para al final mostrarlos en orden correcto
 * El algoritmo de busqueda en amplitud es un algoritmo de busqueda
 * que expande la profundidad total de cada nodo y examina todos los
 * nodos de un nivel desde la mayor profundidad a la
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
     * Abriremos el arbol en su totalidad para luego buscarle la solucion
     * @param arbol
     * @param profundidad
     */
    public void abrirArbol(Arbol arbol, int profundidad){
        if (profundidad<=this.movimientosMaximos){
            ArrayList<movimiento> movimientosHijo = getMovimientosValidos(arbol);
            for (movimiento movimientoHijo : movimientosHijo) {
                arbol.addHijo(movimientoHijo, profundidad);
            }
            ArrayList<Arbol> hijos = arbol.getHijos();
            for (Arbol hijo : hijos) {
                abrirArbol(hijo, profundidad+1);
            }
            arbol.explorando();
            if(this.solucion.isEmpty() && comprobarCadena(arbol)){
                this.solucion = arbol.getMovimientos();
            }
            arbol.explorado();
        }
    }

    /**
     * Verificaremos si una cadena de movimientos de un arbol es meta
     * @param arbol
     * @return true o false dependiendo de la cadena
     */
    public boolean comprobarCadena(Arbol arbol){

        ArrayList<movimiento> movimientos = arbol.getMovimientos();
        // recorremos los movimientos
        for (movimiento movimiento : movimientos) {
            // movemos el bloque
            //System.out.println("Moviendo: "+movimiento.xOrigen+", "+movimiento.yOrigen+" -> "+movimiento.xDestino+", "+movimiento.yDestino);
            puzzle.mover(movimiento.xDestino, movimiento.yDestino, movimiento.xOrigen, movimiento.yOrigen);
        }
        // verificamos si el puzzle esta resuelto
        if(movimientos.size()>1){
            //puzzle.showMatriz();
        }
        if (puzzle.esFinal()){
            // salimos del ciclo
            //System.out.println("Solucion encontrada");
            // devolvemos el puzzle a su origen
            for (int i = movimientos.size() - 1; i >= 0; i--) {
                movimiento movimiento = movimientos.get(i);
                puzzle.mover(movimiento.xOrigen, movimiento.yOrigen, movimiento.xDestino, movimiento.yDestino);
            }
            puzzle.movimientos = 0;
            return true;
        }
        // devolvemos el puzzle a su origen
        for (int i = movimientos.size() - 1; i >= 0; i--) {
            movimiento movimiento = movimientos.get(i);
            puzzle.mover(movimiento.xOrigen, movimiento.yOrigen, movimiento.xDestino, movimiento.yDestino);
        }
        puzzle.movimientos = 0;
        //puzzle.showMatriz();
        return false;
    }

    /**
     * Ahora crearemos una funcion que nos retorne la solucion del puzzle
     * ademas de ser el encargado de ejecutar el algoritmo de busqueda
     */
    public ArrayList<movimiento> getSolucion(){
        // agregamos el primer nivel de profundidad
        abrirArbol(arbolMovimientos, arbolMovimientos.profundidad+1);
        // retornamos la solucion
        return solucion;
    }

}