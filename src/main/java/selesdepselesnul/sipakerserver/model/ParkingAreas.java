package selesdepselesnul.sipakerserver.model;

import java.util.Optional;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public interface ParkingAreas {

    void increase();
    void decerease();
    Optional<ParkingArea> get(int id);
    int size();
}
