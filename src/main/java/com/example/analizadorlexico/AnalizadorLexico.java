package com.example.analizadorlexico;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AnalizadorLexico extends Application {

    static String[] palabrasReservadas = {"if", "main", "else", "switch", "case", "default", "for", "do", "while", "break", "int", "String", "double", "char", "print"};

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AnalizadorLexico.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 520);
        stage.setTitle("Analizador Léxico");
        stage.setScene(scene);
        AnalizadorLexicoController controller = fxmlLoader.getController();
        Estado[] estados = generarEstados();
        controller.estadoInicial = estados[0];
        stage.show();
    }

    public static void main(String[] args) {
        // Start the JavaFX application by calling the launch() method
        launch();
    }

    private Estado[] generarEstados() {
        Estado[] estados = new Estado[28];
        estados[0] = new Estado("Inicial", true, null);
        estados[1] = new Estado("Identificador solo letras", true, TipoToken.IDENTIFICADOR);
        estados[4] = new Estado("Intermedio AND", false, null);
        estados[5] = new Estado("Intermedio OR", false, null);
        estados[6] = new Estado("Operador aritmético +", true, TipoToken.OPERADOR_ARITMETICO);
        estados[7] = new Estado("Operador aritmético -", true, TipoToken.OPERADOR_ARITMETICO);
        estados[8] = new Estado("Operador lógico && O ||", true, TipoToken.OPERADOR_LOGICO);
        estados[9] = new Estado("Incremento", true, TipoToken.INCREMENTO);
        estados[10] = new Estado("Decremento", true, TipoToken.DECREMENTO);
        estados[11] = new Estado("Operador lógico !", true, TipoToken.OPERADOR_LOGICO);
        estados[12] = new Estado("Asignación", true, TipoToken.ASIGNACION);
        estados[13] = new Estado("Operador relacional <, >", true, TipoToken.OPERADOR_RELACIONAL);
        estados[14] = new Estado("Operador relacional con =", true, TipoToken.OPERADOR_RELACIONAL);
        estados[15] = new Estado("Entero", true, TipoToken.NUMERO_ENTERO);
        estados[16] = new Estado("Decimal incompleto", false, null);
        estados[17] = new Estado("Decimal", true, TipoToken.NUMERO_DECIMAL);
        estados[18] = new Estado("Operador aritmético *, %", true, TipoToken.OPERADOR_ARITMETICO);
        estados[19] = new Estado("Operador aritmético /", true, TipoToken.OPERADOR_ARITMETICO);
        estados[20] = new Estado("Comentario línea", true, TipoToken.COMENTARIO_LINEA);
        estados[21] = new Estado("Comentario bloque intermedio 1", false, null);
        estados[22] = new Estado("Comentario bloque intermedio 2", false, null);
        estados[23] = new Estado("Comentario bloque", true, TipoToken.COMENTARIO);
        estados[24] = new Estado("Cadena de caracteres contenido", false, null);
        estados[25] = new Estado("Cadena de caracteres", true, TipoToken.CADENA_CARACTERES);
        estados[26] = new Estado("Parentesis", true, TipoToken.PARENTESIS);
        estados[27] = new Estado("Llave", true, TipoToken.LLAVE);

        // Transiciones
        estados[0].agregarTransicion("LETRAS", estados[1]);
        estados[0].agregarTransicion("&", estados[4]);
        estados[0].agregarTransicion("|", estados[5]);
        estados[0].agregarTransicion("+", estados[6]);
        estados[0].agregarTransicion("-", estados[7]);
        estados[0].agregarTransicion("!", estados[11]);
        estados[0].agregarTransicion("=", estados[12]);
        estados[0].agregarTransicion("<", estados[13]);
        estados[0].agregarTransicion(">", estados[13]);
        estados[0].agregarTransicion("DIGITOS", estados[15]);
        estados[0].agregarTransicion("*", estados[18]);
        estados[0].agregarTransicion("%", estados[18]);
        estados[0].agregarTransicion("/", estados[19]);
        estados[0].agregarTransicion("\"", estados[24]);
        estados[0].agregarTransicion("(", estados[26]);
        estados[0].agregarTransicion(")", estados[26]);
        estados[0].agregarTransicion("{", estados[27]);
        estados[0].agregarTransicion("}", estados[27]);

        estados[1].agregarTransicion("LETRAS", estados[1]);
        estados[1].agregarTransicion("_", estados[1]);

        estados[4].agregarTransicion("&", estados[8]);

        estados[5].agregarTransicion("|", estados[8]);

        estados[6].agregarTransicion("DIGITOS", estados[15]);
        estados[6].agregarTransicion("+", estados[9]);

        estados[7].agregarTransicion("DIGITOS", estados[15]);
        estados[7].agregarTransicion("-", estados[10]);

        estados[11].agregarTransicion("=", estados[14]);

        estados[12].agregarTransicion("=", estados[14]);

        estados[13].agregarTransicion("=", estados[14]);

        estados[15].agregarTransicion("DIGITOS", estados[15]);
        estados[15].agregarTransicion(".", estados[16]);

        estados[16].agregarTransicion("DIGITOS", estados[17]);

        estados[17].agregarTransicion("DIGITOS", estados[17]);

        estados[19].agregarTransicion("/", estados[20]);
        estados[19].agregarTransicion("*", estados[21]);

        estados[20].agregarTransicion("TODO", estados[20]);

        estados[21].agregarTransicion("*", estados[22]);
        estados[21].agregarTransicion("TODO", estados[21]);

        estados[22].agregarTransicion("/", estados[23]);
        estados[22].agregarTransicion("*", estados[22]);
        estados[22].agregarTransicion("TODO", estados[21]);

        estados[24].agregarTransicion("\"", estados[25]);
        estados[24].agregarTransicion("TODO", estados[24]);

        return estados;
    }
}