package selesdepselesnul.sipakerserver.controller;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import selesdepselesnul.sipakerserver.model.ParkingAreas;
import selesdepselesnul.sipakerserver.model.ParkingAreasKVStore;
import java.util.function.Consumer;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class MainController {
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
    private Stage primaryStage;

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
    }


    private void makeParkingAreas() {
        this.parkingAreaFlowPane.getChildren().clear();
        this.parkingAreas.stream().forEach(x -> {
            String image = Resource.Image.unlock;
            if (!x.isAvailable) {
                image = Resource.Image.lock;
            }
            final ImageView parkingImageView = new ImageView(new Image(image));
            parkingImageView.setOnMouseEntered(e -> {
                PopOver popOver = new PopOver();
                VBox layout = new VBox();

                TextField memberIdTextField = new TextField();
                memberIdTextField.setText(x.id + "");
                memberIdTextField.setDisable(true);
                Label idLabel = new Label("Id Anggota    : ");
                idLabel.setLabelFor(memberIdTextField);
                HBox memberIdLayout = new HBox(idLabel, memberIdTextField);

                TextField policeNumberTextField = new TextField();
                policeNumberTextField.setText(x.policeNumber);
                policeNumberTextField.setDisable(true);
                Label policeNumberLabel = new Label("Nopol    :    ");
                policeNumberLabel.setLabelFor(policeNumberTextField);
                HBox policeNumberLayout = new HBox(policeNumberLabel, policeNumberTextField);


                Button readyButton = new Button();
                readyButton.setOnAction(actionEvent -> {
                    Button button = (Button)actionEvent.getSource();
                    if (button.getText().equals("Mulai")) {
                        memberIdTextField.setDisable(false);
                        policeNumberTextField.setDisable(false);
                        readyButton.setText("Simpan");
                    } else if(readyButton.getText().equals("Simpan")) {
                        memberIdTextField.setDisable(true);
                        policeNumberTextField.setDisable(true);
                        readyButton.setText("Selesai");
                        parkingImageView.setImage(new Image(Resource.Image.lock));
                    } else {
                        parkingImageView.setImage(new Image(Resource.Image.unlock));
                        readyButton.setText("Mulai");
                    }
                });
                if(x.isAvailable) {
                    readyButton.setText("Mulai");
                } else {
                    readyButton.setText("Selesai");
                }



                layout.getChildren().setAll(memberIdLayout, policeNumberLayout, readyButton);
                popOver.setContentNode(layout);
                popOver.show(parkingImageView);
            });
            parkingImageView.setFitHeight(80);
            parkingImageView.setFitWidth(80);
            parkingImageView.setCursor(Cursor.CLOSED_HAND);
            final Text parkingAreaIdText = new Text(String.valueOf(x.id));
            parkingAreaIdText.setCursor(Cursor.CLOSED_HAND);
            parkingAreaFlowPane.getChildren().add(new VBox(parkingImageView, parkingAreaIdText));
        });
    }
}
