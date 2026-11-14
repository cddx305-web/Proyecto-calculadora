import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalculadoraArboles extends JFrame {
    
    private JTextField cajaEntrada;
    private JLabel etiquetaResultado;
    private String expresion;
    private int posicion;

    public CalculadoraArboles() {
        crearInterfaz();
    }

    private void crearInterfaz() {
        // Configuracion de la ventana
        setTitle("Calculadora con arbol binario");
        setSize(420, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        // Etiqueta de instruccion
        JLabel etiquetaTexto = new JLabel("Escribe tu expresion");
        etiquetaTexto.setBounds(130, 10, 200, 20);
        add(etiquetaTexto);

        // Caja de texto
        cajaEntrada = new JTextField("(3 + 5) * 2");
        cajaEntrada.setBounds(20, 40, 280, 30);
        add(cajaEntrada);

        // Boton calcular
        JButton botonCalcular = new JButton("Calcular");
        botonCalcular.setBounds(310, 40, 85, 30);
        botonCalcular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calcularResultado();
            }
        });
        add(botonCalcular);

        // Etiqueta del resultado
        etiquetaResultado = new JLabel("Resultado");
        etiquetaResultado.setBounds(150, 90, 200, 30);
        etiquetaResultado.setFont(new Font("Arial", Font.BOLD, 16));
        etiquetaResultado.setForeground(new Color(0, 50, 90));
        add(etiquetaResultado);
    }

    private void calcularResultado() {
        try {
            expresion = cajaEntrada.getText();
            posicion = 0;

            Nodo raiz = leerSuma();
            saltarEspacios();

            if (posicion != expresion.length()) {
                throw new Exception("Expresion mal escrita");
            }

            double respuesta = Analizador.evaluar(raiz);
            etiquetaResultado.setText("Resultado " + respuesta);

        } catch (Exception error) {
            etiquetaResultado.setText("Revisa tu expresion");
            JOptionPane.showMessageDialog(null, "Expresion incorrecta",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Nodo leerSuma() {
        Nodo izq = leerMultiplicacion();

        while (true) {
            saltarEspacios();
            if (posicion >= expresion.length()) return izq;

            char op = expresion.charAt(posicion);
            if (op != '+' && op != '-') return izq;

            posicion++;
            Nodo nuevo = new Nodo(String.valueOf(op));
            nuevo.izquierda = izq;
            nuevo.derecha = leerMultiplicacion();
            izq = nuevo;
        }
    }

    private Nodo leerMultiplicacion() {
        Nodo izq = leerNumero();

        while (true) {
            saltarEspacios();
            if (posicion >= expresion.length()) return izq;

            char op = expresion.charAt(posicion);
            if (op != '*' && op != '/') return izq;

            posicion++;
            Nodo nuevo = new Nodo(String.valueOf(op));
            nuevo.izquierda = izq;
            nuevo.derecha = leerNumero();
            izq = nuevo;
        }
    }

    private Nodo leerNumero() {
        saltarEspacios();
        if (posicion >= expresion.length()) {
            throw new IllegalArgumentException("Falta un numero");
        }

        char c = expresion.charAt(posicion);

        if (c == '(') {
            posicion++;
            Nodo interior = leerSuma();
            saltarEspacios();
            if (posicion >= expresion.length() || expresion.charAt(posicion) != ')') {
                throw new IllegalArgumentException("Falta cerrar parentesis");
            }
            posicion++;
            return interior;
        }

        int inicio = posicion;
        while (posicion < expresion.length()) {
            c = expresion.charAt(posicion);
            if (Character.isDigit(c) || c == '.') {
                posicion++;
            } else {
                break;
            }
        }

        if (inicio == posicion) {
            throw new IllegalArgumentException("Se esperaba un numero");
        }

        String numeroTexto = expresion.substring(inicio, posicion);
        return new Nodo(numeroTexto);
    }

    private void saltarEspacios() {
        while (posicion < expresion.length() && Character.isWhitespace(expresion.charAt(posicion))) {
            posicion++;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculadoraArboles().setVisible(true);
        });
    }
}
