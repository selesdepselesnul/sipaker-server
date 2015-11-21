import selesdepselesnul.sipakerserver.KVStoreManager;
import selesdepselesnul.sipakerserver.model.ParkingAreasKVStore;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class ParkingAreasKVStoreTest {
    public static void main(String[] args) {
        new ParkingAreasKVStore(new KVStoreManager()).create(2);
    }
}
