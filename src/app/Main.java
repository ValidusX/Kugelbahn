package app;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("kugelbahn.fxml")));
        primaryStage.setTitle("Kugelbahn");
        primaryStage.setResizable(false);
        Scale scale = new Scale();
        scale.setY(-1);
        scale.pivotYProperty().bind(Bindings.createDoubleBinding(() -> 350.0,
                root.boundsInLocalProperty()));
        root.getTransforms().add(scale);

        primaryStage.setScene(new Scene(root, 1500, 700)); //Feld 130 x 70 m
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
