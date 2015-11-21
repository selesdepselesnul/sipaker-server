package selesdepselesnul.sipakerserver;
/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import selesdepselesnul.sipakerserver.controller.MainController;
import selesdepselesnul.sipakerserver.Manager.Resource;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader mainLayoutLoader = new FXMLLoader(Resource.Ui.MAIN_LAYOUT);
            GridPane mainLayout = mainLayoutLoader.load();
            MainController mainController = mainLayoutLoader.getController();
            mainController.setMainStage(primaryStage);
            primaryStage.setTitle("Sipaker - Server -");
            primaryStage.setScene(new Scene(mainLayout));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
