import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CircleApp extends Application {

    private ObservableList<CircleData> circleList = FXCollections.observableArrayList();
    private Scene scene;

    @Override
    public void start(Stage stage) {
        Label blueLabel = new Label("JavaFX UI");
        blueLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        blueLabel.setStyle("-fx-text-fill: white;");
        VBox blueBox = new VBox(blueLabel);
        blueBox.setStyle("-fx-background-color: #0078D7; -fx-padding: 15; -fx-background-radius: 10;");
        blueBox.setAlignment(Pos.CENTER);

        Label greenLabel = new Label("JavaFX Events");
        greenLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        greenLabel.setStyle("-fx-text-fill: white;");
        VBox greenBox = new VBox(greenLabel);
        greenBox.setStyle("-fx-background-color: #28a745; -fx-padding: 15; -fx-background-radius: 10;");
        greenBox.setAlignment(Pos.CENTER);

        HBox colorBoxes = new HBox(15, blueBox, greenBox);
        colorBoxes.setAlignment(Pos.CENTER);

        TextField radiusField = new TextField();
        radiusField.setPromptText("Enter radius (cm)");
        radiusField.setFont(Font.font("Segoe UI", 14));
        radiusField.setMaxWidth(200);

        Button addButton = createButton("Add Circle", "#0078D7", "#005A9E");
        Button exportButton = createButton("Export to File", "#28a745", "#1e7e34");
        ToggleButton darkModeToggle = new ToggleButton("Dark Mode");

        TableView<CircleData> table = new TableView<>();
        table.setItems(circleList);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(250);

        TableColumn<CircleData, Double> radiusCol = new TableColumn<>("Radius (cm)");
        radiusCol.setCellValueFactory(cell -> cell.getValue().radiusProperty().asObject());

        TableColumn<CircleData, Double> areaCol = new TableColumn<>("Area (cm²)");
        areaCol.setCellValueFactory(cell -> cell.getValue().areaProperty().asObject());

        radiusCol.setStyle("-fx-alignment: CENTER;");
        areaCol.setStyle("-fx-alignment: CENTER;");

        table.getColumns().addAll(radiusCol, areaCol);

        addButton.setOnAction(e -> {
            try {
                double radius = Double.parseDouble(radiusField.getText());
                if (radius <= 0) {
                    showAlert("Error", "Radius must be positive.");
                    return;
                }
                circleList.add(new CircleData(radius));
                radiusField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Error", "Invalid number.");
            }
        });

        exportButton.setOnAction(e -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("circles.txt"))) {
                for (CircleData circle : circleList) {
                    writer.write(String.format("Radius: %.2f, Area: %.2f%n", circle.getRadius(), circle.getArea()));
                }
                showInfo("Success", "File saved successfully.");
            } catch (IOException ex) {
                showAlert("Error", "Failed to save file.");
            }
        });

        darkModeToggle.setOnAction(e -> {
            if (darkModeToggle.isSelected()) {
                if (!scene.getStylesheets().contains(getClass().getResource("dark-theme.css").toExternalForm())) {
                    scene.getStylesheets().add(getClass().getResource("dark-theme.css").toExternalForm());
                }
            } else {
                scene.getStylesheets().clear();
            }
        });

        HBox buttonsBox = new HBox(15, addButton, exportButton, darkModeToggle);
        buttonsBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(20, colorBoxes, radiusField, buttonsBox, table);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);

        scene = new Scene(root, 480, 480);
        stage.setScene(scene);
        stage.setTitle("Circle Manager - Dark Mode Test");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    private Button createButton(String text, String bgColor, String hoverColor) {
        Button button = new Button(text);
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        button.setStyle("-fx-background-color: " + bgColor + "; -fx-text-fill: white; -fx-background-radius: 8;");
        button.setPrefWidth(130);
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + hoverColor + "; -fx-text-fill: white; -fx-background-radius: 8;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + bgColor + "; -fx-text-fill: white; -fx-background-radius: 8;"));
        return button;
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
