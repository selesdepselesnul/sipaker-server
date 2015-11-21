package selesdepselesnul.sipakerserver.model;

public class ParkingArea {
    public final int id;
    public final boolean isAvailable;
    public final int memberId;
    public final String policeNumber;
    public final String checkIn;
    public final String checkOut;

    public ParkingArea(int id, boolean isAvailable, int memberId, String policeNumber, String checkIn, String checkOut) {
        this.id = id;
        this.isAvailable = isAvailable;
        this.memberId = memberId;
        this.policeNumber = policeNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return "ParkingArea{" +
                "id=" + id +
                ", isAvailable=" + isAvailable +
                ", memberId=" + memberId +
                ", policeNumber='" + policeNumber + '\'' +
                ", checkIn='" + checkIn + '\'' +
                ", checkOut='" + checkOut + '\'' +
                '}';
    }
}
