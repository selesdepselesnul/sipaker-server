package selesdepselesnul.sipakerserver;/**
 * Created by morrisseymarr on 11/20/15.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            FXMLLoader mainLayoutLoader = new FXMLLoader();
            GridPane mainLayout = mainLayoutLoader.load(ClassLoader.getSystemResource("fxml/main.fxml"));
            primaryStage.setScene(new Scene(mainLayout));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
