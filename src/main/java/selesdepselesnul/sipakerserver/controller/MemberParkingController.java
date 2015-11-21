package selesdepselesnul.sipakerserver.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import selesdepselesnul.sipakerserver.DateTimeParser;
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


    final private ParkingAreasKVStore parkingAreasKVStore = new ParkingAreasKVStore(new KVStoreManager());
    private ImageView parkingAreaImageView;
    private ParkingArea parkingArea;
//    private int parkingAreaId;
//    private boolean isAvailable;


    private void init() {
        checkAvailability();
        initForm();
        readyButton.setOnAction(actionEvent -> {
            Button button = (Button) actionEvent.getSource();
            if (button.getText().equals("Mulai")) {
                checkInTextField.setDisable(false);
                checkInTextField.setText(LocalDateTime.now().format(DateTimeParser.D_T_FORMATTER_READABLE));
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
                checkOutTextField.setText(LocalDateTime.now().format(DateTimeParser.D_T_FORMATTER_READABLE));
                this.parkingAreaImageView.setImage(new Image(Resource.Image.unlock));
                readyButton.setText("Mulai");
                updateParkingArea(true);
            }
        });
    }

    private void initForm() {
        this.memberIdTextField.setText(String.valueOf(this.parkingArea.memberId));
        this.policeNumberTextField.setText(this.parkingArea.policeNumber);
        this.checkInTextField.setText(DateTimeParser.toReadable(this.parkingArea.checkIn));
        this.checkOutTextField.setText(DateTimeParser.toReadable(this.parkingArea.checkOut));
    }

    private void checkAvailability() {
        if (this.parkingArea.isAvailable) {
            this.readyButton.setText("Mulai");
        } else {
            this.readyButton.setText("Selesai");
        }
    }

    private void updateParkingArea(boolean isAvailable) {
        final ParkingArea parkingArea = new ParkingArea(
                this.parkingArea.id,
                isAvailable,
                Integer.parseInt(memberIdTextField.getText()),
                policeNumberTextField.getText(),
                DateTimeParser.toUrlFriendly(checkInTextField.getText()),
                DateTimeParser.toUrlFriendly(checkOutTextField.getText())
        );
        parkingAreasKVStore.store(parkingArea);
        parkingAreasKVStore.log(parkingArea);
        System.out.println("Status of parking Area = " + parkingArea);
    }

    public void setParkingAreaImageView(ImageView parkingAreaImageView) {
        this.parkingAreaImageView = parkingAreaImageView;
        init();
    }

//    public void setParkingAreaId(int parkingAreaId) {
//        this.parkingAreaId = parkingAreaId;
//    }
//
//
//    public void isAvailable(boolean isAvailable) {
//        this.isAvailable = isAvailable;
//    }

    public void setParkingArea(ParkingArea parkingArea) {
        this.parkingArea = parkingArea;
    }
}
