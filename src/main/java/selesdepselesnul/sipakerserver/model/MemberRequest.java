package selesdepselesnul.sipakerserver.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class MemberRequest {

    private SimpleIntegerProperty queueNumber;
    private SimpleIntegerProperty memberId;
    private SimpleStringProperty policeNumber;
    private SimpleStringProperty requestTime;

    public MemberRequest(int queueNumber, int memberId, String policeNumber, String requestTime) {
        this.queueNumber = new SimpleIntegerProperty(queueNumber);
        this.memberId = new SimpleIntegerProperty(memberId);
        this.policeNumber = new SimpleStringProperty(policeNumber);
        this.requestTime = new SimpleStringProperty(requestTime);
    }

    public int getQueueNumber() {
        return queueNumber.get();
    }

    public String getPoliceNumber() {
        return policeNumber.get();
    }

    public int getMemberId() {
        return memberId.get();
    }


    public String getRequestTime() {
        return requestTime.get();
    }


}
