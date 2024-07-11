package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;

public class Main extends Application {

    private TextField textField = new TextField();
    private long num1 = 0;
    private long num2;
    private String op = "";
    private boolean start = true;

    @Override
    public void start(Stage primaryStage) throws Exception {

        textField.setFont(Font.font(20));
        textField.setPrefHeight(50);
        textField.setAlignment(Pos.CENTER_RIGHT);
        textField.setEditable(false);

        StackPane stackPane = new StackPane();
        stackPane.setPadding(new Insets(10));
        stackPane.getChildren().add(textField);

        TilePane tile = new TilePane();
        tile.setHgap(10);
        tile.setVgap(10);
        tile.setAlignment(Pos.TOP_CENTER);
        tile.getChildren().addAll(
            createButtonForNumber("7"),
            createButtonForNumber("8"),
            createButtonForNumber("9"),
            createButtonForOperators("/"),

            createButtonForNumber("4"),
            createButtonForNumber("5"),
            createButtonForNumber("6"),
            createButtonForOperators("X"),

            createButtonForNumber("1"),
            createButtonForNumber("2"),
            createButtonForNumber("3"),
            createButtonForOperators("-"),

            createButtonForNumber("0"),
            createButtonForClear("C"),
            createButtonForOperators("="),
            createButtonForOperators("+")
        );

        BorderPane root = new BorderPane();
        root.setTop(stackPane);
        root.setCenter(tile);
        Scene scene = new Scene(root, 250, 310);
        primaryStage.setScene(scene);
        primaryStage.setTitle("My Calculator");
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    private Button createButtonForNumber(String ch) {
        Button button = new Button(ch);
        button.setFont(Font.font(18));
        button.setPrefSize(50, 50);
        button.setOnAction(this::processNumbers);
        return button;
    }

    private Button createButtonForOperators(String ch) {
        Button button = new Button(ch);
        button.setFont(Font.font(18));
        button.setPrefSize(50, 50);
        button.setOnAction(this::processOperators);
        return button;
    }

    private Button createButtonForClear(String ch) {
        Button button = new Button(ch);
        button.setFont(Font.font(18));
        button.setPrefSize(50, 50);
        button.setOnAction(e -> {
            textField.setText("");
            op = "";
            start = true;
            num1 = 0;
            num2 = 0;
        });
        return button;
    }

    private void processNumbers(ActionEvent e) {
        if (start) {
            textField.setText("");
            start = false;
        }
        String value = ((Button) e.getSource()).getText();
        textField.setText(textField.getText() + value);
    }

    private void processOperators(ActionEvent e) {
        String value = ((Button) e.getSource()).getText();
        if (!value.equals("=")) {
            if (!op.isEmpty()) {
                return;
            }
            op = value;
            num1 = Long.parseLong(textField.getText());
            textField.setText("");
        } else {
            if (op.isEmpty()) {
                return;
            }
            num2 = Long.parseLong(textField.getText());
            float result = Calculate(num1, num2, op);
            textField.setText(String.valueOf(result));
            start = true;
            op = "";
        }
    }

    private float Calculate(long num1, long num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "X":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    return 0;
                } else {
                    return num1 / num2;
                }
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
