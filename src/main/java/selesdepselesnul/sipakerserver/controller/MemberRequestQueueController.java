package selesdepselesnul.sipakerserver.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import selesdepselesnul.sipakerserver.Manager.KVStoreManager;
import selesdepselesnul.sipakerserver.Manager.Resource;
import selesdepselesnul.sipakerserver.TimeString;
import selesdepselesnul.sipakerserver.model.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class MemberRequestQueueController {
    @FXML
    private TableView<MemberRequest> memberRequestTableView;

    @FXML
    private TableColumn<MemberRequest, Integer> queueNumberTableColumn;

    @FXML
    private TableColumn<MemberRequest, Integer> memberIdTableColumn;

    @FXML
    private TableColumn<MemberRequest, String> requestTimeTableColumn;

    @FXML
    private ComboBox<ParkingArea> parkingAreaNumberComboBox;
    private Stream<ParkingArea> parkingAreasStream;
    private List<ImageView> parkingAreaImageViews;

    private void init() {
        this.parkingAreaNumberComboBox.getItems().setAll(
                parkingAreasStream.collect(Collectors.toList()));

        this.queueNumberTableColumn.setCellValueFactory(new PropertyValueFactory<MemberRequest, Integer>("queueNumber"));
        this.memberIdTableColumn.setCellValueFactory(new PropertyValueFactory<MemberRequest, Integer>("memberId"));
        this.requestTimeTableColumn.setCellValueFactory(new PropertyValueFactory<MemberRequest, String>("requestTime"));

        this.memberRequestTableView.getColumns().setAll(queueNumberTableColumn, memberIdTableColumn, requestTimeTableColumn);

        this.memberRequestTableView.getItems().addAll(
                Arrays.asList(
                        new MemberRequest(1, 2, TimeString.now()),
                        new MemberRequest(2, 3, TimeString.now())
                )
        );

        this.parkingAreaNumberComboBox.setOnAction(e -> {
            ParkingArea selectedParkingArea = parkingAreaNumberComboBox.getSelectionModel().getSelectedItem();
            this.parkingAreaImageViews.stream()
                    .filter(p -> Integer.parseInt(p.getId()) == selectedParkingArea.id)
                    .forEach(x -> x.setImage(new Image(Resource.Image.lock)));
            MemberRequest selectedMemberRequest = this.memberRequestTableView.getItems().remove(0);
            ParkingArea parkingArea = new ParkingArea(
                    selectedParkingArea.id,
                    false,
                    selectedMemberRequest.getMemberId(),
                    "",
                    TimeString.now(),
                    ""
            );
            new ParkingAreasKVStore(new KVStoreManager()).store(parkingArea);
            new MemberParkingsKVStore(new KVStoreManager()).store(parkingArea.memberParking);
        });
    }

    public void setParkingAreasStream(Stream<ParkingArea> parkingAreasStream) {
        this.parkingAreasStream = parkingAreasStream;
        init();
    }

    public void setParkingAreaImageViews(List<ImageView> parkingAreaImageViews) {
        this.parkingAreaImageViews = parkingAreaImageViews;
    }
}
