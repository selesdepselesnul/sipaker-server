package selesdepselesnul.sipakerserver.model;

public class ParkingArea {
    public final boolean isAvailable;
    public final int memberId;
    public final String policeNumber;
    public final int id;

    ParkingArea(int id, boolean isAvaliable, int memberId, String policeNumber) {
        this.id = id;
        this.isAvailable = isAvaliable;
        this.memberId = memberId;
        this.policeNumber = policeNumber;
    }

    @Override
    public String toString() {
        return "ParkingArea{" +
                "id=" + id +
                ", isAvailable=" + isAvailable +
                ", memberId=" + memberId +
                ", policeNumber='" + policeNumber + '\'' +
                '}';
    }
}
