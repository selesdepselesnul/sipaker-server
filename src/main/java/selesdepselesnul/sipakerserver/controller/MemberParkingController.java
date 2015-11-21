package selesdepselesnul.sipakerserver.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import selesdepselesnul.sipakerserver.KVStoreManager;
import selesdepselesnul.sipakerserver.model.ParkingArea;
import selesdepselesnul.sipakerserver.model.ParkingAreasKVStore;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class MemberParkingController {
    @FXML
    private TextField memberIdTextField;

    @FXML
    private TextField policeNumberTextField;

    @FXML
    private TextField checkInTextField;

    @FXML
    private TextField checkOutTextField;

    @FXML
    private Button readyButton;


    private static final DateTimeFormatter DATE_TIME_FORMATER = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMATER_URL_FRIENDLY = DateTimeFormatter.ofPattern("HHmmssddMMyyyy");
    private ImageView parkingAreaImageView;
    private int parkingAreaId;
    final private ParkingAreasKVStore parkingAreasKVStore = new ParkingAreasKVStore(new KVStoreManager());
    private boolean isAvailable;


    private void init() {
        checkAvailability();
        readyButton.setOnAction(actionEvent -> {
            Button button = (Button) actionEvent.getSource();
            if (button.getText().equals("Mulai")) {
                checkInTextField.setDisable(false);
                checkInTextField.setText(LocalDateTime.now().format(DATE_TIME_FORMATER));
                checkInTextField.setDisable(true);
                memberIdTextField.setDisable(false);
                policeNumberTextField.setDisable(false);
                readyButton.setText("Simpan");
            } else if (readyButton.getText().equals("Simpan")) {
                memberIdTextField.setDisable(true);
                policeNumberTextField.setDisable(true);
                readyButton.setText("Selesai");
                this.parkingAreaImageView.setImage(new Image(Resource.Image.lock));
                updateParkingArea(false);
            } else {
                checkOutTextField.setText(LocalDateTime.now().format(DATE_TIME_FORMATER));
                this.parkingAreaImageView.setImage(new Image(Resource.Image.unlock));
                readyButton.setText("Mulai");
                updateParkingArea(true);
            }
        });
    }

    private void checkAvailability() {
        if (this.isAvailable) {
            this.readyButton.setText("Mulai");
        } else {
            this.readyButton.setText("Selesai");
        }
    }

    private void updateParkingArea(boolean isAvailable) {
        String checkOut = "";
        if (isAvailable)
            checkOut = LocalDateTime.parse(checkOutTextField.getText(), DATE_TIME_FORMATER)
                    .format(DATE_TIME_FORMATER_URL_FRIENDLY);

        final ParkingArea parkingArea = new ParkingArea(
                parkingAreaId,
                isAvailable,
                Integer.parseInt(memberIdTextField.getText()),
                policeNumberTextField.getText(),
                LocalDateTime.parse(checkInTextField.getText(), DATE_TIME_FORMATER).format(DATE_TIME_FORMATER_URL_FRIENDLY),
                checkOut
        );
        parkingAreasKVStore.store(parkingArea);
        parkingAreasKVStore.log(parkingArea);
    }

    public void setParkingAreaImageView(ImageView parkingAreaImageView) {
        this.parkingAreaImageView = parkingAreaImageView;
        init();
    }

    public void setParkingAreaId(int parkingAreaId) {
        this.parkingAreaId = parkingAreaId;
    }


    public void isAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
