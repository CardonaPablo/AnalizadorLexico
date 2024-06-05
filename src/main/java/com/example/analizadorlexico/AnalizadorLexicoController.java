package com.example.analizadorlexico;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.stage.FileChooser;
import javafx.util.Pair;

import java.io.File;
import java.io.FileReader;

public class AnalizadorLexicoController {

    public Estado estadoInicial;
    @FXML
    private TextArea editor;
    @FXML
    private TitledPane identificadoresPane;
    @FXML
    private TitledPane palabrasReservadasPane;
    @FXML
    private TitledPane operadoresRelacionalesPane;
    @FXML
    private TitledPane operadoresLogicosPane;
    @FXML
    private TitledPane operadoresAritmeticosPane;
    @FXML
    private TitledPane numeroEnteroPane;
    @FXML
    private TitledPane numeroDecimalPane;
    @FXML
    private TitledPane cadenaCaracteresPane;
    @FXML
    private TitledPane comentarioPane;
    @FXML
    private TitledPane comentarioLineaPane;
    @FXML
    private TitledPane singularesPane;
    @FXML
    private TitledPane errorPane;

    @FXML
    protected void onOpenFile() {
        // Open a file selector for the user
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open TXT File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT Files", "*.txt")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            // Read the file contents and set the contents to the editor
            try {
                FileReader fileReader = new FileReader(file);
                StringBuilder fileContent = new StringBuilder();
                int character;
                while ((character = fileReader.read()) != -1) {
                    fileContent.append((char) character);
                }
                editor.setText(fileContent.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void onQuit() {
        System.exit(0);
    };

    @FXML
    protected void onClickAnalyze() {

        limpiarContadores();

        // Obtenemos el texto del editor
        String text = editor.getText();

        Pair<TipoToken, Integer> resultado;
        TipoToken tipoToken;
        int maximaProfundidad;

        while(text.length() > 0) {
            // Buscamos la transición para el texto actual
            resultado = estadoInicial.buscarTransicion(text, 0);
            maximaProfundidad = resultado.getValue();
            tipoToken = resultado.getKey();

            // Si la profundidad es 0, significa que se encontró un delimitador inmediatamente
            // Lo ignoramos y seguimos con el siguiente caracter
            if(maximaProfundidad == 0) {
                text = text.substring(1);
                continue;
            }

            // Buscamos si la palabra es una palabra reservada
            if (tipoToken == TipoToken.IDENTIFICADOR) {
                String palabra = text.substring(0, maximaProfundidad);
                boolean esPalabraReservada = false;
                for (String palabraReservada : AnalizadorLexico.palabrasReservadas) {
                    if (palabra.equals(palabraReservada)) {
                        esPalabraReservada = true;
                        break;
                    }
                }
                if (esPalabraReservada)
                    tipoToken = TipoToken.PALABRA_RESERVADA;
            }

            actualizarInterfaz(tipoToken, maximaProfundidad, text);

            // Actualizamos el texto para remover la palabra que ya se procesó
            text = text.substring(maximaProfundidad);
        }
    }

    private void actualizarInterfaz(TipoToken tipoToken, int maximaProfundidad, String text) {
        String palabra = text.substring(0, maximaProfundidad);
        int indiceDeItem = 4;
        // Extract thie inner ListView from the TitledPane
        TitledPane titledPane = null;
        switch (tipoToken) {
            case IDENTIFICADOR:
                titledPane = identificadoresPane;
                break;
            case PALABRA_RESERVADA:
                titledPane = palabrasReservadasPane;
                break;
            case OPERADOR_RELACIONAL:
                titledPane = operadoresRelacionalesPane;
                break;
            case OPERADOR_LOGICO:
                titledPane = operadoresLogicosPane;
                break;
            case OPERADOR_ARITMETICO:
                titledPane = operadoresAritmeticosPane;
                break;
            case NUMERO_ENTERO:
                titledPane = numeroEnteroPane;
                break;
            case NUMERO_DECIMAL:
                titledPane = numeroDecimalPane;
                break;
            case CADENA_CARACTERES:
                titledPane = cadenaCaracteresPane;
                break;
            case COMENTARIO:
                titledPane = comentarioPane;
                break;
            case COMENTARIO_LINEA:
                titledPane = comentarioLineaPane;
                break;
            case INCREMENTO:
                indiceDeItem = 0;
            case DECREMENTO:
                indiceDeItem = indiceDeItem < 1 ? indiceDeItem : 1;
            case ASIGNACION:
                indiceDeItem = indiceDeItem < 2 ? indiceDeItem : 2;
            case PARENTESIS:
                indiceDeItem = indiceDeItem < 3 ? indiceDeItem : 3;
            case LLAVE:
                indiceDeItem = indiceDeItem < 4 ? indiceDeItem : 4;

                ListView<String> listView = (ListView<String>) singularesPane.getContent();
                String item = listView.getItems().get(indiceDeItem);
                listView.getItems().set(indiceDeItem, obtenerNuevoTitulo(item));
                return;
            case ERROR:
                titledPane = errorPane;
                break;
            default:
                break;
        }
        ListView<String> listView = (ListView<String>) titledPane.getContent();
        // Actualizar el titulo del TitledPane
        titledPane.setText(obtenerNuevoTitulo(titledPane.getText()));
        // Añadir the palabra to the ListView
        listView.getItems().add(palabra);
        // titledPane.setContent(listView);
    }

    private void limpiarContadores() {
        // Clear the list views
        identificadoresPane.setContent(new ListView<String>());
        identificadoresPane.setText("Identificadores: 0");
        palabrasReservadasPane.setContent(new ListView<String>());
        palabrasReservadasPane.setText("Palabras reservadas: 0");
        operadoresRelacionalesPane.setContent(new ListView<String>());
        operadoresRelacionalesPane.setText("Operadores relacionales: 0");
        operadoresLogicosPane.setContent(new ListView<String>());
        operadoresLogicosPane.setText("Operadores lógicos: 0");
        operadoresAritmeticosPane.setContent(new ListView<String>());
        operadoresAritmeticosPane.setText("Operadores aritméticos: 0");
        numeroEnteroPane.setContent(new ListView<String>());
        numeroEnteroPane.setText("Números enteros: 0");
        numeroDecimalPane.setContent(new ListView<String>());
        numeroDecimalPane.setText("Números decimales: 0");
        cadenaCaracteresPane.setContent(new ListView<String>());
        cadenaCaracteresPane.setText("Cadenas de caracteres: 0");
        comentarioPane.setContent(new ListView<String>());
        comentarioPane.setText("Comentarios: 0");
        comentarioLineaPane.setContent(new ListView<String>());
        comentarioLineaPane.setText("Comentarios de línea: 0");
        singularesPane.setContent(new ListView<String>(
                FXCollections.observableArrayList(
                        "Incremento: 0",
                        "Decremento: 0",
                        "Asignación: 0",
                        "Parentesis: 0",
                        "Llave: 0"
                )
        ));
        errorPane.setContent(new ListView<String>());
        errorPane.setText("Errores: 0");
    }

    private String obtenerNuevoTitulo(String title) {
        int indice = title.indexOf(":") + 2;
        String numero = title.substring(indice);
        int conteo = Integer.parseInt(numero) + 1;
        return title.substring(0, indice) + conteo;
    }
}