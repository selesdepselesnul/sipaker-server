package selesdepselesnul.sipakerserver.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import selesdepselesnul.sipakerserver.Manager.KVStoreManager;
import selesdepselesnul.sipakerserver.Manager.Resource;
import selesdepselesnul.sipakerserver.model.ParkingArea;
import selesdepselesnul.sipakerserver.model.ParkingAreas;
import selesdepselesnul.sipakerserver.model.ParkingAreasKVStore;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class MainController implements Initializable{
    @FXML
    private FlowPane parkingAreaFlowPane;

    @FXML
    private Button increasingParkingSizeButton;

    @FXML
    private Button decreasingParkingSizeButton;

    @FXML
    private ComboBox<Runnable> displayedParkingAreasModeComboBox;

    @FXML
    private TextField patternTextField;

    @FXML
    private ComboBox<String> filteringByPatternComboBox;

    @FXML
    private Button memberRequestQueueButton;

    final private ParkingAreas parkingAreas = new ParkingAreasKVStore(new KVStoreManager());
    private List<ParkingArea> inMemoryParkingAreas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    private final class FilteringByPattern implements Runnable {
        @Override
        public String toString() {
            return "Berdasarkan pola";
        }

        @Override
        public void run() {
            setVisibleFilteringByPattern(true);
            makeParkingAreas(p -> true);
        }
    }

    private final class DisplayAvailable implements Runnable {

        @Override
        public void run() {
            setVisibleFilteringByPattern(false);
            makeParkingAreas(p -> p.isAvailable);
        }

        @Override
        public String toString() {
            return "Kosong";
        }

    }

    private final class DisplayNotAvailable implements Runnable {

        @Override
        public void run() {
            setVisibleFilteringByPattern(false);
            makeParkingAreas(p -> !p.isAvailable);
        }

        @Override
        public String toString() {
            return "Dipakai";
        }
    }

    private final class DisplayAllParkingAreas implements Runnable {
        @Override
        public void run() {
            setVisibleFilteringByPattern(false);
            makeParkingAreas(p -> true);
        }

        @Override
        public String toString() {
            return "Tampilkan Semua";
        }
    }

    private void init() {
        this.inMemoryParkingAreas = this.parkingAreas.stream().collect(Collectors.toList());
        this.makeParkingAreas(p -> true);
        final DisplayAllParkingAreas displayAllParkingAreas = new DisplayAllParkingAreas();
        this.displayedParkingAreasModeComboBox.getItems().setAll(
                displayAllParkingAreas, new DisplayAvailable(), new DisplayNotAvailable(), new FilteringByPattern());
        this.displayedParkingAreasModeComboBox.setValue(displayAllParkingAreas);
        this.displayedParkingAreasModeComboBox.setOnAction(
                e -> displayedParkingAreasModeComboBox.getSelectionModel().getSelectedItem().run());
        Consumer<Runnable> updateSize = x -> {
            x.run();
            makeParkingAreas(p -> true);
        };
        this.increasingParkingSizeButton.setOnAction(e -> updateSize.accept(() -> this.parkingAreas.increase()));
        this.decreasingParkingSizeButton.setOnAction(e -> updateSize.accept(() -> this.parkingAreas.decrease()));
        this.memberRequestQueueButton.setOnAction(e -> {
            try {
                PopOver popOver = new PopOver();
                FXMLLoader fxmlLoader = new FXMLLoader(Resource.Ui.MEMBER_REQUEST_QUEUE_LAYOUT);
                AnchorPane memberRequestQueueLayout = fxmlLoader.load();
                MemberRequestQueueController memberRequestQueueController = fxmlLoader.getController();
                memberRequestQueueController.setParkingAreas(inMemoryParkingAreas);
                popOver.setContentNode(memberRequestQueueLayout);
                popOver.show(memberRequestQueueButton);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

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

    private void setVisibleFilteringByPattern(boolean isVisible) {
        this.patternTextField.setVisible(isVisible);
        this.filteringByPatternComboBox.setVisible(isVisible);
    }

    private void makeParkingAreas(Predicate<ParkingArea> predicate) {
        this.parkingAreaFlowPane.getChildren().clear();
        this.parkingAreas.stream().filter(predicate).forEach(x -> {
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
