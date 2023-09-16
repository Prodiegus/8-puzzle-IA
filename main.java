import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        boolean DFF = true;
        boolean DFA = false;
        int movimientosMaximos = 0;
        System.out.println("Puzzle 8");
        System.out.println("Maximo numero de movimientos recomendado: 18");
        if (args.length == 0) {
            System.out.println("Faltan entradas en la linea de comandos");
            System.out.println("Numero de movimientos para desordenar el puzzle: -m <movimientos>");
            System.out.println("Tipo de busqueda defaul DFF [-DFF|-DFA]");
            return;
        }
        for(int i = 0; i<args.length; i++){
            String arg = args[i];
            if(arg.equals("-m")){
                if (args.length == 1) {
                    System.out.println("No se ha especificado el numero de movimientos maximos -m <movimientos>");
                    return;
                }
                movimientosMaximos = Integer.parseInt(args[i+1]);
            }
            if (arg.equals("-DFA")){
                DFA = true;
                DFF = false;
            }
        }
        puzzle puzzle = new puzzle();
        // desordenamos la matriz
        puzzle.desordenar(movimientosMaximos);
        // mostramos la matriz antes de que comiense a trabajar el algoritmo de busqueda
        puzzle.showMatriz();
        // creamos una instancia de la clase Ampliada
        ArrayList<movimiento> solucion = new ArrayList<>();
        if (DFA) {
            Ampliada ampliada = new Ampliada(movimientosMaximos, puzzle);
            // ejecutamos el algoritmo de busqueda
            solucion = ampliada.getSolucion();   
        }else if (DFF){
            Profundidad profundidad = new Profundidad(movimientosMaximos, puzzle);
            // ejecutamos el algoritmo de busqueda
            solucion = profundidad.getSolucion();
        }
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
