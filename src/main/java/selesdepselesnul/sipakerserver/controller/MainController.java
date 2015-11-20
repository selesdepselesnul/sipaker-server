package selesdepselesnul.sipakerserver.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import selesdepselesnul.sipakerserver.model.ParkingArea;
import selesdepselesnul.sipakerserver.model.ParkingAreas;
import selesdepselesnul.sipakerserver.model.ParkingAreasKVStore;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class MainController implements Initializable {
    @FXML
    private FlowPane parkingAreaFlowPane;

    @FXML
    final private ParkingAreas parkingAreas = new ParkingAreasKVStore();

    @FXML
    private Button increasingParkingSizeButton;

    @FXML
    private Button decreasingParkingSizeButton;

    @FXML
    private ComboBox<Runnable> displayedParkingAreasModeComboBox;

    private class DisplayAllParkingAreas implements Runnable {
        @Override
        public void run() {
            makeParkingAreas();
        }

        @Override
        public String toString() {
            return "Tampilkan Semua";
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.makeParkingAreas();
        this.displayedParkingAreasModeComboBox.getItems().setAll(new DisplayAllParkingAreas());
        Consumer<Runnable> updateSize = x -> {
            x.run();
            makeParkingAreas();
        };
        this.increasingParkingSizeButton.setOnAction(x -> updateSize.accept(() -> this.parkingAreas.increase()));
        this.decreasingParkingSizeButton.setOnAction(x -> updateSize.accept(() -> this.parkingAreas.decerease()));
    }


    private void makeParkingAreas() {
        this.parkingAreaFlowPane.getChildren().clear();
        this.parkingAreas.stream().forEach(x -> {
            String image = Resource.Image.unlock;
            if (!x.isAvailable) {
                image = Resource.Image.lock;
            }
            final ImageView parkingImageView = new ImageView(new Image(image));
            parkingImageView.setFitHeight(80);
            parkingImageView.setFitWidth(80);
            parkingImageView.setCursor(Cursor.CLOSED_HAND);
            final Text parkingAreaIdText = new Text(String.valueOf(x.id));
            parkingAreaIdText.setCursor(Cursor.CLOSED_HAND);
            parkingAreaFlowPane.getChildren().add(new VBox(parkingImageView, parkingAreaIdText));
        });
    }
}
