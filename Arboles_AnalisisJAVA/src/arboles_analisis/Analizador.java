public class Analizador {

    public static double evaluar(Nodo nodo) {
        // Si es un numero
        if (nodo.izquierda == null && nodo.derecha == null) {
            return Double.parseDouble(nodo.valor);
        }

        // Calcular ramas
        double izquierda = evaluar(nodo.izquierda);
        double derecha = evaluar(nodo.derecha);

        // Realizar operacion
        switch (nodo.valor) {
            case "+": return izquierda + derecha;
            case "-": return izquierda - derecha;
            case "*": return izquierda * derecha;
            case "/": return izquierda / derecha;
            default: throw new IllegalArgumentException("Operador desconocido");
        }
    }
}
