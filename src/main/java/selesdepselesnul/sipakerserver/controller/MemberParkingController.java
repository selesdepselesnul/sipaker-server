package selesdepselesnul.sipakerserver.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

import selesdepselesnul.sipakerserver.Manager.KVStoreManager;
import selesdepselesnul.sipakerserver.Manager.Resource;
import selesdepselesnul.sipakerserver.TimeString;
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

    private ImageView parkingAreaImageView;
    private ParkingArea parkingArea;
    private List<ParkingArea> parkingAreaInMemoryList;

    private static final String READY = "Mulai";
    private static final String START = "Simpan";
    private static final String FINISHED = "Selesai";

    static final private KVStoreManager kvStoreManager = new KVStoreManager();
    static final private ParkingAreasKVStore parkingAreasKVStore = new ParkingAreasKVStore(kvStoreManager);
    static final private MemberParkingsKVStore memberParkings = new MemberParkingsKVStore(kvStoreManager);


    private void init() {
        checkAvailability();
        initForm();
        readyButton.setOnAction(e -> {
            Button button = (Button) e.getSource();
            if (button.getText().equals(READY)) {
                checkInTextField.setDisable(false);
                checkInTextField.setText(TimeString.now());
                checkInTextField.setDisable(true);
                memberIdTextField.setDisable(false);
                policeNumberTextField.setDisable(false);
                readyButton.setText(START);
            } else if (readyButton.getText().equals(START)) {
                memberIdTextField.setDisable(true);
                policeNumberTextField.setDisable(true);
                readyButton.setText(FINISHED);
                this.parkingAreaImageView.setImage(new Image(Resource.Image.lock));
                this.checkOutTextField.clear();
                updateDatabaseWithAvailability(false);
            } else {
                checkOutTextField.setText(TimeString.now());
                this.parkingAreaImageView.setImage(new Image(Resource.Image.unlock));
                readyButton.setText(READY);
                updateDatabaseWithAvailability(true);
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
        if (this.parkingArea.isAvailable)
            this.readyButton.setText(READY);
        else
            this.readyButton.setText(FINISHED);
    }

    private void updateDatabaseWithAvailability(boolean isAvailable) {
        final ParkingArea parkingAreaNewData = new ParkingArea(
                this.parkingArea.id,
                isAvailable,
                Integer.parseInt(memberIdTextField.getText()),
                policeNumberTextField.getText(),
                checkInTextField.getText(),
                checkOutTextField.getText()
        );

        this.parkingAreaInMemoryList.removeIf(p -> p.id == parkingAreaNewData.id);
        this.parkingAreaInMemoryList.add(parkingAreaNewData);
        this.parkingAreaInMemoryList.sort((a, b) -> Integer.compare(a.id, b.id));
        this.parkingAreasKVStore.store(parkingAreaNewData);

        if (checkOutTextField.getText().equals(""))
            this.memberParkings.store(parkingAreaNewData.memberParking);
        else
            this.memberParkings.update(parkingAreaNewData.memberParking);
    }

    public void setParkingAreaImageView(ImageView parkingAreaImageView) {
        this.parkingAreaImageView = parkingAreaImageView;
        init();
    }

    public void setParkingArea(ParkingArea parkingArea) {
        this.parkingArea = parkingArea;
    }

    public void setParkingAreaInMemoryList(List<ParkingArea> parkingAreaInMemoryList) {
        this.parkingAreaInMemoryList = parkingAreaInMemoryList;
    }
}
