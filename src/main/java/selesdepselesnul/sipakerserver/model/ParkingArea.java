package selesdepselesnul.sipakerserver.model;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class ParkingArea {
    public final int id;
    public final boolean isAvailable;
    public final MemberParking memberParking;

    public ParkingArea(int id, boolean isAvailable, int memberId, String policeNumber, String checkIn, String checkOut) {
        this.id = id;
        this.isAvailable = isAvailable;
        this.memberParking = new MemberParking(
                -1,
                memberId,
                id,
                policeNumber,
                checkIn,
                checkOut
        );
    }

    @Override
    public String toString() {
        return this.id + "";
    }
}
