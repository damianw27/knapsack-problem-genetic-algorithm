package pl.wilenskid.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class GeneralWindow extends Application {

    public GridPane mainTab;
    public GridPane configurationTab;
    public GridPane detailsTab;

    @Override
    public void start(Stage stage) throws IOException {
        URL generalView = getClass()
                .getClassLoader()
                .getResource("views/general.fxml");

        if (generalView == null) {
            return;
        }

        Parent root = FXMLLoader.load(generalView);
        Scene scene = new Scene(root, 600, 430);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
