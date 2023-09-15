import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        System.out.println("Puzzle 8");
        System.out.println("Maximo numero de movimientos recomendado: 18");
        if (args.length == 0) {
            System.out.println("No se ha especificado el numero de movimientos maximos -m <movimientos>");
            return;
        }
        if(args[0].equals("-m")){
            if (args.length == 1) {
                System.out.println("No se ha especificado el numero de movimientos maximos -m <movimientos>");
                return;
            }
            args[0] = args[1];
        }
        puzzle puzzle = new puzzle();
        int movimientosMaximos = Integer.parseInt(args[0]);
        // desordenamos la matriz
        puzzle.desordenar(movimientosMaximos);
        // mostramos la matriz antes de que comiense a trabajar el algoritmo de busqueda
        puzzle.showMatriz();
        // creamos una instancia de la clase Ampliada
        Ampliada ampliada = new Ampliada(movimientosMaximos, puzzle);
        // ejecutamos el algoritmo de busqueda
        ArrayList<movimiento> solucion = ampliada.getSolucion();
        // mostramos la solucion
        System.out.println("Solucion: ");
        for (movimiento mov : solucion) {
            puzzle.mover(mov.xDestino, mov.yDestino, mov.xOrigen, mov.yOrigen);
            System.out.println("Movimiento: " + mov.xOrigen + ", " + mov.yOrigen + " -> " + mov.xDestino + ", " + mov.yDestino);
            //puzzle.showMatriz();
        }
        // mostramos la matriz final
        puzzle.showMatriz();
        // verificamos si la matriz final es igual a la matriz inicial
        if (puzzle.esFinal()) {
            System.out.println("La matriz esta ordenada");
        }else{
            System.out.println("La matriz no esta ordenada, no hay solucion");
        }
    
    }
}
