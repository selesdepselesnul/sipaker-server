package selesdepselesnul.sipakerserver.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import selesdepselesnul.sipakerserver.model.ParkingAreas;
import selesdepselesnul.sipakerserver.model.ParkingAreasKVStore;

import java.net.URL;
import java.util.List;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.makeParkingAreas(parkingAreas.size());
        Consumer<Runnable> updateSize = x -> {
            x.run();
            makeParkingAreas(this.parkingAreas.size());
        };
        this.increasingParkingSizeButton.setOnAction(x -> updateSize.accept(() -> this.parkingAreas.increase()));
        this.decreasingParkingSizeButton.setOnAction(x -> updateSize.accept(() -> this.parkingAreas.decerease()));
    }


    private void makeParkingAreas(int size) {
        List<VBox> parkingAreasVboxList = IntStream.rangeClosed(1, size).mapToObj(
                i -> {
                    ImageView imageView = new ImageView(new Image(Resource.Image.lock()));
                    imageView.setFitHeight(80);
                    imageView.setFitWidth(80);
                    VBox parkingAreaVBox = new VBox(imageView, new Text(String.valueOf(i)));
                    return parkingAreaVBox;
                }
        ).collect(Collectors.toList());
        parkingAreaFlowPane.getChildren().setAll(parkingAreasVboxList);
    }
}
