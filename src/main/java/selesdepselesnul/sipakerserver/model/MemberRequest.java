package selesdepselesnul.sipakerserver.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class MemberRequest {

    private SimpleIntegerProperty queueNumber;
    private SimpleIntegerProperty memberId;
    private SimpleStringProperty requestTime;

    public MemberRequest(int queueNumber, int memberId, String requestTime) {
        this.queueNumber = new SimpleIntegerProperty(queueNumber);
        this.memberId = new SimpleIntegerProperty(memberId);
        this.requestTime = new SimpleStringProperty(requestTime);
    }

    public int getQueueNumber() {
        return queueNumber.get();
    }



    public int getMemberId() {
        return memberId.get();
    }


    public String getRequestTime() {
        return requestTime.get();
    }



}
