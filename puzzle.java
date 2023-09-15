public class puzzle {

    String[][] matriz;
    String[][] matrizFinal;
    int movimientos;

    puzzle(){
        matriz = new String[3][3];
        matriz = initMatriz();
        matrizFinal = initMatriz();
        movimientos = 0;
    }

    // llenamos la matriz
    public String[][] initMatriz(){
        String[][] matriz = {{"1", "2", "3"}, {"8", null, "4", }, {"7", "6", "5"}};
        return matriz;
    }
    
    /**
     * haremos una funcion para desordenar la matriz con movimientos
     * aleatorios y admisibles para el juego
     * se podra elegir la cantidad de movimientos que se quieren hacer
     * para desordenar la matriz
     */
    public void desordenar(int movimientos){
        int xOrigen = 0;
        int yOrigen = 0;
        int xDestino = 0;
        int yDestino = 0;
        for (int i = 0; i < movimientos; i++){
            // generamos un numero aleatorio entre 0 y 2 para cada coordenada
            
            while(!esValido(xDestino, yDestino, xOrigen, yOrigen)) {
                xOrigen = (int) (Math.random() * 3);
                yOrigen = (int) (Math.random() * 3);
                xDestino = (int) (Math.random() * 3);
                yDestino = (int) (Math.random() * 3);        
            }
            mover(xDestino, yDestino, xOrigen, yOrigen);
        }
        this.movimientos = 0;
        if (esFinal()) {
            desordenar(movimientos);
        }
    }

    // mostramos la matriz
    public void showMatriz(){   
        System.out.println("Movimientos: " + movimientos);
        System.out.println("_______");
        for (String[] row : matriz) {
            for (String col : row) {
                if (col == null) {
                    System.out.print(" " + " ");    
                }else{
                    System.out.print(col + " ");
                }
                
            }
            System.out.println(); 
        }
        System.out.println("______");
    }
    
    /**
     * Cada celda de la matriz representa un lugar
     * en este juego los unicos movimientos admisibles son
     * arriba, abajo, izquierda y derecha. Siempre que la casilla
     * a la que uno se quiere mover contenga un espacio vacio
     * el cual se intercambiara con la casilla inicial movida
     * crearemos una funcion para verificar si un movimiento es valido
     */

    boolean esValido(int xDestino, int yDestino, int xOrigen, int yOrigen){
        if (!(matriz[yDestino][xDestino] == null)){
            //System.err.println("Movimiento: "+matriz[yOrigen][yDestino]+" a "+ matriz[yDestino][xDestino] +" invalido");
            return false;
        }else{
            // verificamos que el movimiento no se salga de la matriz
            if (xDestino > 2 || yDestino > 2 || xOrigen > 2 || yOrigen > 2){
                //System.err.println("Movimiento fuera de rango");
                return false;
            }
            // verificaremos si el movimiento es de un bloque en las direcciones permitidas
            if (yDestino == yOrigen && xDestino == xOrigen + 1){
                // movimiento a la derecha
                return true;
            }else if (yDestino == yOrigen && xDestino == xOrigen - 1){
                // movimiento a la izquierda
                return true;
            }else if (yDestino == yOrigen + 1 && xDestino == xOrigen){
                // movimiento hacia abajo
                return true;
            }else if (yDestino == yOrigen - 1 && xDestino == xOrigen){
                // movimiento hacia arriba
                return true;
            }else{
                //System.err.println("Movimiento invalido");
                return false;
            }
        }
    }

    // funcion para mover un bloque
    void mover(int xDestino, int yDestino, int xOrigen, int yOrigen){
        if (esValido(xDestino, yDestino, xOrigen, yOrigen)){
            // Mostraremos el destino
            //System.out.println("Moviendo " + matriz[yOrigen][xOrigen] + " a " + matriz[yDestino][xDestino]);
            matriz[yDestino][xDestino] = matriz[yOrigen][xOrigen];
            matriz[yOrigen][xOrigen] = null;
            movimientos++;
        }
    }

    // verificamos si estamos ante la matriz final
    public boolean esFinal(){
        for ( int i = 0; i < matrizFinal.length; i++) {
            String[] fila = matrizFinal[i];
            for ( int j = 0; j < fila.length; j++) {
                String bloque = fila[j];
                if (bloque != matriz[i][j]){
                    return false;
                }
            }
            
        }
        return true;
    }
    // obtenemos la matriz
    public String[][] getMatriz(){
        return matriz;
    }
}