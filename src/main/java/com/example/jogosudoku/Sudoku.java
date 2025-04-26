package com.example.jogosudoku;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Sudoku extends Application {

    private static final int SIZE = 9;
    private TextField[][] cells = new TextField[SIZE][SIZE];

    private int[][] initialBoard = {
            {2, 6, 0, 0, 7, 0, 0, 0, 0},
            {4, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {7, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {8, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 3, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 5, 1, 9, 0, 0, 4},
            {0, 0, 1, 0, 8, 0, 0, 9, 0}
    };

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(2);
        grid.setVgap(2);

        for (int linha = 0; linha < SIZE; linha++) {
            for (int coluna = 0; coluna < SIZE; coluna++) {
                TextField tf = new TextField();
                tf.setPrefWidth(40);
                tf.setPrefHeight(40);
                tf.setStyle("-fx-alignment: center; -fx-font-size: 16;");

                int valor = initialBoard[linha][coluna];
                if (valor != 0) {
                    tf.setText(String.valueOf(valor));
                    tf.setDisable(true);
                    tf.setStyle("-fx-background-color: #D3D3D3; -fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 16;");
                } else {
                    tf.textProperty().addListener((obs, oldValue, newValue) -> {
                        if (!newValue.matches("[1-9]?")) {
                            tf.setText(oldValue);
                        }
                    });
                }

                cells[linha][coluna] = tf;
                grid.add(tf, coluna, linha);
            }
        }

        Button verificarBtn = new Button("Verificar");
        verificarBtn.setOnAction(e -> verificarSudoku());

        BorderPane root = new BorderPane();
        root.setCenter(grid);
        root.setBottom(verificarBtn);
        BorderPane.setMargin(verificarBtn, new Insets(10));
        BorderPane.setAlignment(verificarBtn, javafx.geometry.Pos.CENTER);

        Scene scene = new Scene(root, 390, 420);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sudoku JavaFX");
        primaryStage.show();
    }

    private void verificarSudoku() {
        int[][] board = new int[SIZE][SIZE];

        // Lê o tabuleiro atual
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                String texto = cells[i][j].getText();
                board[i][j] = texto.isEmpty() ? 0 : Integer.parseInt(texto);
            }
        }

        if (tabuleiroValido(board)) {
            mostrarAlerta("Shooow!", "Jogo completo.");
        } else {
            mostrarAlerta("Errooou", "Tá errado, corrija.");
        }
    }

    private boolean tabuleiroValido(int[][] b) {

        for (int i = 0; i < SIZE; i++) {
            boolean[] linha = new boolean[SIZE + 1];
            boolean[] coluna = new boolean[SIZE + 1];

            for (int j = 0; j < SIZE; j++) {
                int valL = b[i][j];
                int valC = b[j][i];

                if (valL != 0) {
                    if (linha[valL]) return false;
                    linha[valL] = true;
                }

                if (valC != 0) {
                    if (coluna[valC]) return false;
                    coluna[valC] = true;
                }
            }
        }

        for (int blocoLinha = 0; blocoLinha < SIZE; blocoLinha += 3) {
            for (int blocoColuna = 0; blocoColuna < SIZE; blocoColuna += 3) {
                boolean[] bloco = new boolean[SIZE + 1];
                for (int i = blocoLinha; i < blocoLinha + 3; i++) {
                    for (int j = blocoColuna; j < blocoColuna + 3; j++) {
                        int val = b[i][j];
                        if (val != 0) {
                            if (bloco[val]) return false;
                            bloco[val] = true;
                        }
                    }
                }
            }
        }

        return true;
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
