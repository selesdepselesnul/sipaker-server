package selesdepselesnul.sipakerserver.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import selesdepselesnul.sipakerserver.Manager.KVStoreManager;
import selesdepselesnul.sipakerserver.Manager.Resource;
import selesdepselesnul.sipakerserver.TimeString;
import selesdepselesnul.sipakerserver.model.MemberParkings;
import selesdepselesnul.sipakerserver.model.ParkingArea;
import selesdepselesnul.sipakerserver.model.ParkingAreasKVStore;
import selesdepselesnul.sipakerserver.model.MemberParkingsKVStore;

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
    final private MemberParkings memberParkings = new MemberParkingsKVStore(new KVStoreManager());
    private ImageView parkingAreaImageView;
    private ParkingArea parkingArea;


    private void init() {
        checkAvailability();
        initForm();
        readyButton.setOnAction(actionEvent -> {
            Button button = (Button) actionEvent.getSource();
            if (button.getText().equals("Mulai")) {
                checkInTextField.setDisable(false);
                checkInTextField.setText(TimeString.now());
                checkInTextField.setDisable(true);
                memberIdTextField.setDisable(false);
                policeNumberTextField.setDisable(false);
                readyButton.setText("Simpan");
            } else if (readyButton.getText().equals("Simpan")) {
                memberIdTextField.setDisable(true);
                policeNumberTextField.setDisable(true);
                readyButton.setText("Selesai");
                this.parkingAreaImageView.setImage(new Image(Resource.Image.lock));
                this.checkOutTextField.clear();
                updateDatabase(false);
            } else {
                checkOutTextField.setText(TimeString.now());
                this.parkingAreaImageView.setImage(new Image(Resource.Image.unlock));
                readyButton.setText("Mulai");
                updateDatabase(true);
            }
        });
    }

    private void initForm() {
        this.memberIdTextField.setText(String.valueOf(this.parkingArea.memberParking.memberId));
        this.policeNumberTextField.setText(this.parkingArea.memberParking.policeNumber);
        this.checkInTextField.setText(this.parkingArea.memberParking.checkIn);
        this.checkOutTextField.setText(this.parkingArea.memberParking.checkOut);
    }

    private void checkAvailability() {
        if (this.parkingArea.isAvailable) {
            this.readyButton.setText("Mulai");
        } else {
            this.readyButton.setText("Selesai");
        }
    }

    private void updateDatabase(boolean isAvailable) {
        final ParkingArea parkingArea = new ParkingArea(
                this.parkingArea.id,
                isAvailable,
                Integer.parseInt(memberIdTextField.getText()),
                policeNumberTextField.getText(),
                checkInTextField.getText(),
                checkOutTextField.getText()
        );

        this.parkingAreasKVStore.store(parkingArea);

        if(checkOutTextField.getText().equals(""))
            this.memberParkings.store(parkingArea.memberParking);
        else
            this.memberParkings.update(parkingArea.memberParking);
    }

    public void setParkingAreaImageView(ImageView parkingAreaImageView) {
        this.parkingAreaImageView = parkingAreaImageView;
        init();
    }

    public void setParkingArea(ParkingArea parkingArea) {
        this.parkingArea = parkingArea;
    }
}
