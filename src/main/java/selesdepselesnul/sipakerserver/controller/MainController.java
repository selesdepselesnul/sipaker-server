package selesdepselesnul.sipakerserver.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import selesdepselesnul.sipakerserver.KVStoreManager;
import selesdepselesnul.sipakerserver.model.ParkingArea;
import selesdepselesnul.sipakerserver.model.ParkingAreas;
import selesdepselesnul.sipakerserver.model.ParkingAreasKVStore;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class MainController {
    @FXML
    private FlowPane parkingAreaFlowPane;

    @FXML
    private Button increasingParkingSizeButton;

    @FXML
    private Button decreasingParkingSizeButton;

    @FXML
    private ComboBox<Runnable> displayedParkingAreasModeComboBox;

    private Stage primaryStage;

    final private ParkingAreas parkingAreas = new ParkingAreasKVStore(new KVStoreManager());

    public void setMainStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        init();
    }

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

    private void init() {
        this.makeParkingAreas();
        this.displayedParkingAreasModeComboBox.getItems().setAll(new DisplayAllParkingAreas());
        Consumer<Runnable> updateSize = x -> {
            x.run();
            makeParkingAreas();
        };
        this.increasingParkingSizeButton.setOnAction(x -> updateSize.accept(() -> this.parkingAreas.increase()));
        this.decreasingParkingSizeButton.setOnAction(x -> updateSize.accept(() -> this.parkingAreas.decerease()));

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                            System.out.println("I'm working !");
                        }
                );
            }
        }, 1000l, 1000l);
    }

    private void makeParkingAreas() {
        this.parkingAreaFlowPane.getChildren().clear();
        this.parkingAreas.stream().forEach(x -> {
            String image = Resource.Image.unlock;

            if (!x.isAvailable) {
                image = Resource.Image.lock;
            }

            final Text parkingAreaIdText = new Text(String.valueOf(x.id));
            parkingAreaIdText.setCursor(Cursor.CLOSED_HAND);

            final ImageView parkingAreaImageView = new ImageView(new Image(image));
            parkingAreaImageView.setFitHeight(80);
            parkingAreaImageView.setFitWidth(80);
            parkingAreaImageView.setCursor(Cursor.CLOSED_HAND);
            parkingAreaImageView.setId(String.valueOf(x.id));

            parkingAreaImageView.setOnMouseClicked(this::onParkingAreaClicked);

            parkingAreaFlowPane.getChildren().add(new VBox(parkingAreaImageView, parkingAreaIdText));
        });
    }

    private void onParkingAreaClicked(MouseEvent mouseEvent) {
        try {
            ImageView parkingAreaImageView = (ImageView) mouseEvent.getSource();
            FXMLLoader fxmlLoader = new FXMLLoader(Resource.Ui.MEMBER_LAYOUT);
            AnchorPane contentNode = fxmlLoader.load();
            MemberParkingController memberParkingController = fxmlLoader.getController();
             final ParkingArea parkingArea = this.parkingAreas.get(
                        Integer.parseInt(parkingAreaImageView.getId())
                    ).get();
            memberParkingController.setParkingArea(parkingArea);
            System.out.println("Selected ParkingArea = " + parkingArea);
            memberParkingController.setParkingAreaImageView(parkingAreaImageView);
            PopOver popOver = new PopOver(contentNode);
            popOver.show(parkingAreaImageView);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
