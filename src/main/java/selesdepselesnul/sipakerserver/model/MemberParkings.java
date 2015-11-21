package selesdepselesnul.sipakerserver.model;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public interface MemberParkings {
    int length();
    void store(MemberParking memberParking);
    void dropAll();
    void update(MemberParking memberParking);
}
