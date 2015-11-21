package selesdepselesnul.sipakerserver.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

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



    private void init() {
        readyButton.setOnAction(actionEvent -> {
            Button button = (Button) actionEvent.getSource();
            if (button.getText().equals("Mulai")) {
                memberIdTextField.setDisable(false);
                policeNumberTextField.setDisable(false);
                readyButton.setText("Simpan");
            } else if (readyButton.getText().equals("Simpan")) {
                memberIdTextField.setDisable(true);
                policeNumberTextField.setDisable(true);
                readyButton.setText("Selesai");
                this.parkingAreaImageView.setImage(new Image(Resource.Image.lock));
            } else {
                this.parkingAreaImageView.setImage(new Image(Resource.Image.unlock));
                readyButton.setText("Mulai");
            }
        });

        if (this.parkingAreaImageView.getImage().equals(Resource.Image.lock)) {
            readyButton.setText("Mulai");
        } else {
            readyButton.setText("Selesai");
        }


    }

    public void setParkingAreaImageView(ImageView parkingAreaImageView) {
        this.parkingAreaImageView = parkingAreaImageView;
        init();
    }
}
