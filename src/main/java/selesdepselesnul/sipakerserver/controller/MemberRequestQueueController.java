package selesdepselesnul.sipakerserver.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import selesdepselesnul.sipakerserver.TimeString;
import selesdepselesnul.sipakerserver.model.MemberRequest;
import selesdepselesnul.sipakerserver.model.ParkingArea;

import java.util.List;
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

    private void init() {
        this.parkingAreaNumberComboBox.getItems().setAll(
                parkingAreasStream.collect(Collectors.toList()));

        this.queueNumberTableColumn.setCellValueFactory(new PropertyValueFactory<MemberRequest, Integer>("queueNumber"));
        this.memberIdTableColumn.setCellValueFactory(new PropertyValueFactory<MemberRequest, Integer>("memberId"));
        this.requestTimeTableColumn.setCellValueFactory(new PropertyValueFactory<MemberRequest, String>("requestTime"));

        this.memberRequestTableView.getColumns().setAll(queueNumberTableColumn, memberIdTableColumn, requestTimeTableColumn);

        this.memberRequestTableView.getItems().add(new MemberRequest(1, 2, TimeString.now()));
    }

    public void setParkingAreasStream(Stream<ParkingArea> parkingAreasStream) {
        this.parkingAreasStream = parkingAreasStream;
        init();
    }
}
