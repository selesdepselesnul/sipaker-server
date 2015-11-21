package selesdepselesnul.sipakerserver.model;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class MemberParking {

    public final int id;
    public final int memberId;
    public final int parkingAreaId;
    public final String policeNumber;
    public final String checkIn;
    public final String checkOut;

    public MemberParking(int id, int memberId, int parkingAreaId, String policeNumber,String checkIn, String checkOut) {
        this.id = id;
        this.memberId = memberId;
        this.parkingAreaId = parkingAreaId;
        this.policeNumber = policeNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}
