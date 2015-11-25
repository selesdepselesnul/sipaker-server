package selesdepselesnul.sipakerserver.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
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
import java.util.concurrent.atomic.AtomicInteger;
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
    private TableColumn<MemberRequest, String> policeNumberTableColumn;

    @FXML
    private TableColumn<MemberRequest, String> requestTimeTableColumn;

    @FXML
    private ComboBox<ParkingArea> parkingAreaNumberComboBox;

    private Stream<ParkingArea> parkingAreasStream;
    private List<ImageView> parkingAreaImageViews;
    private AtomicInteger queueLength;
    final private static KVStoreManager kvStoreManager = new KVStoreManager();

    private void init() {
        this.parkingAreaNumberComboBox.getItems().setAll(
                parkingAreasStream.collect(Collectors.toList()));

        makeMemberRequestTable();

        this.memberRequestTableView.getItems().addAll(
                new MemberRequestsKVStore(
                        new KVStoreManager()).stream()
                        .filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList())
        );

        this.parkingAreaNumberComboBox.setOnAction(__ -> {
            final ParkingArea selectedParkingArea = parkingAreaNumberComboBox.getSelectionModel().getSelectedItem();
            this.parkingAreaImageViews.stream()
                    .filter(p -> Integer.parseInt(p.getId()) == selectedParkingArea.id)
                    .forEach(x -> x.setImage(new Image(Resource.Image.lock)));
            final MemberRequest selectedMemberRequest = this.memberRequestTableView.getItems().remove(0);
            final ParkingArea parkingArea = new ParkingArea(
                    selectedParkingArea.id,
                    false,
                    selectedMemberRequest.getMemberId(),
                    selectedMemberRequest.getPoliceNumber(),
                    TimeString.now(),
                    ""
            );
            new ParkingAreasKVStore(kvStoreManager).store(parkingArea);
            new MemberParkingsKVStore(kvStoreManager).store(parkingArea.memberParking);
            new MemberRequestsKVStore(kvStoreManager).dequeu();
            this.queueLength.decrementAndGet();
        });
    }

    private void makeMemberRequestTable() {
        this.queueNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("queueNumber"));
        this.memberIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        this.policeNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("policeNumber"));
        this.requestTimeTableColumn.setCellValueFactory(new PropertyValueFactory<>("requestTime"));

        this.memberRequestTableView.getColumns().setAll(queueNumberTableColumn, memberIdTableColumn,
                policeNumberTableColumn, requestTimeTableColumn);
    }

    public void setParkingAreasStream(Stream<ParkingArea> parkingAreasStream) {
        this.parkingAreasStream = parkingAreasStream;
        init();
    }

    public void setParkingAreaImageViews(List<ImageView> parkingAreaImageViews) {
        this.parkingAreaImageViews = parkingAreaImageViews;
    }

    public void setQueueLength(AtomicInteger queueLength) {
        this.queueLength = queueLength;
    }
}
