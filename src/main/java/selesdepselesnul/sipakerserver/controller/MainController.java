package selesdepselesnul.sipakerserver.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        List<VBox> parkingAreasVboxList = IntStream.rangeClosed(1, this.parkingAreas.size()).mapToObj(
                i -> {

                    Image image = new Image(Resource.Image.unlock());
                    final Optional<ParkingArea> parkingAreaOptional = this.parkingAreas.get(i);
                    System.out.println(i + " status is = " + parkingAreaOptional.get().isAvailable);
                    if (parkingAreaOptional.isPresent()) {
                        if (!parkingAreaOptional.get().isAvailable) {
                            image = new Image(Resource.Image.lock());
                        }
                    }
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(80);
                    imageView.setFitWidth(80);
                    VBox parkingAreaVBox = new VBox(imageView, new Text(String.valueOf(i)));
                    return parkingAreaVBox;
                }
        ).collect(Collectors.toList());
        parkingAreaFlowPane.getChildren().setAll(parkingAreasVboxList);
    }
}
