package selesdepselesnul.sipakerserver.model;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public interface ParkingAreas {

    void increase();
    void decerease();
    Optional<ParkingArea> get(int id);
    Stream<ParkingArea> stream();
    int size();
    void create(int size);
    void dropAll();
    void update(ParkingArea parkingArea);
    void log(ParkingArea parkingArea);
}
