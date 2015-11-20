package selesdepselesnul.sipakerserver.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class MainController implements Initializable {
    @FXML
    private FlowPane parkingAreaFlowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parkingAreaFlowPane.getChildren().addAll(makeParkingAreaItems());
    }

    private List<VBox> makeParkingAreaItems() {
        return IntStream.rangeClosed(1, 100).mapToObj(
                i -> {
                    ImageView imageView = new ImageView(new Image(Resource.Image.lock()));
                    imageView.setFitHeight(80);
                    imageView.setFitWidth(80);
                    VBox parkingAreaVBox = new VBox(imageView, new Text(String.valueOf(i)));
                    return parkingAreaVBox;
                }
        ).collect(Collectors.toList());
    }
}
