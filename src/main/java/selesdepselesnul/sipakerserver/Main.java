package selesdepselesnul.sipakerserver;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import selesdepselesnul.sipakerserver.Manager.Resource;

import java.io.IOException;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader mainLayoutLoader = new FXMLLoader(Resource.Ui.MAIN_LAYOUT);
            final GridPane mainLayout = mainLayoutLoader.load();
            primaryStage.setTitle("Sipaker - Server -");
            primaryStage.setScene(new Scene(mainLayout));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
