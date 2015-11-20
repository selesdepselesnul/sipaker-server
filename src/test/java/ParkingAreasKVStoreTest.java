import selesdepselesnul.sipakerserver.KVStoreManager;
import selesdepselesnul.sipakerserver.model.ParkingAreasKVStore;

/**
 * Created by morrisseymarr on 11/20/15.
 */
public class ParkingAreasKVStoreTest {
    public static void main(String[] args) {
        new ParkingAreasKVStore(new KVStoreManager()).dropAll();
    }
}
