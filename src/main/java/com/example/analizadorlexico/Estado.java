package com.example.analizadorlexico;

import javafx.util.Pair;

import java.util.*;


public class Estado {
    static String[] delimitadores = {" ", "\n", "\t" };

    private Map<String, Estado> transiciones = new TreeMap<>();
    private String nombre;
    private boolean esFinal;
    private TipoToken tipoToken;

    Estado(String nombre, boolean esFinal, TipoToken tipoToken) {
        this.nombre = nombre;
        this.esFinal = esFinal;
        this.tipoToken = tipoToken;
    }

    public void agregarTransicion(String condicion, Estado estado){
        transiciones.put(condicion, estado);
    }

    public Pair<TipoToken, Integer> buscarTransicion(String sufijo, int depth) {
        // Condición de paro
        if(sufijo.length() == 0)
            return new Pair(esFinal ? tipoToken : TipoToken.ERROR, depth);

        // Verificar si el caracter es un delimitador
        final char caracter = sufijo.charAt(0);
        for (String delimitador : delimitadores) {
            if (caracter == delimitador.charAt(0)) {
                return new Pair(esFinal ? tipoToken : TipoToken.ERROR, depth);
            }
        }
        // Buscar transición adecuada
        String[] condiciones = transiciones.keySet().toArray(new String[0]);
        for (int i = 0; i < condiciones.length; i++) {
            if (evaluarCondicion(condiciones[i], caracter)) {
                return transiciones.get(condiciones[i]).buscarTransicion(sufijo.substring(1, sufijo.length()), ++depth);
            }
        }
        // Si no se encontró ninguna transición, se regresa un error
        // Debemos regresar la profundidad hasta encontrar un delimitador
        // Ya que toda la palabra es un error
        for(int i = 0; i < sufijo.length(); i++){
            for (String delimitador : delimitadores) {
                if (sufijo.substring(i, i+1).equals(delimitador)) {
                    return new Pair(TipoToken.ERROR, depth);
                }
            }
            depth++;
        }
        // Si no se encontró un delimitador, se encontró el final del texto
        return new Pair(TipoToken.ERROR, depth);
    }

    private boolean evaluarCondicion(String condicion, char caracter){
        switch (condicion){
            case "LETRAS":
                return Character.isLetter(caracter);
            case "DIGITOS":
                return Character.isDigit(caracter);
            case "TODO":
                return true;
            default:
                return condicion.charAt(0) == caracter;
        }
    }
}
