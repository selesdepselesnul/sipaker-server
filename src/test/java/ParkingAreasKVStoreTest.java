import selesdepselesnul.sipakerserver.KVStoreManager;
import selesdepselesnul.sipakerserver.model.MemberParkingsKVStore;
import selesdepselesnul.sipakerserver.model.ParkingAreasKVStore;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class ParkingAreasKVStoreTest {
    public static void main(String[] args) {
        MemberParkingsKVStore parkingAreasLogs = new MemberParkingsKVStore(new KVStoreManager());
        parkingAreasLogs.dropAll();
        new ParkingAreasKVStore(new KVStoreManager()).dropAll();
    }
}
