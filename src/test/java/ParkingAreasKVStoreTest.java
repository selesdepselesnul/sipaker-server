import selesdepselesnul.sipakerserver.Manager.KVStoreManager;
import selesdepselesnul.sipakerserver.model.MemberParkingsKVStore;
import selesdepselesnul.sipakerserver.model.ParkingAreasKVStore;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class ParkingAreasKVStoreTest {
    public static void main(String[] args) {
        MemberParkingsKVStore memberParkingsKVStore = new MemberParkingsKVStore(new KVStoreManager());
//        memberParkingsKVStore.dropAll();
        memberParkingsKVStore.init();
        new ParkingAreasKVStore(new KVStoreManager()).create(2);
//        new ParkingAreasKVStore(new KVStoreManager()).dropAll();

    }
}
