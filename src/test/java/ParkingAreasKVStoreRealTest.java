import selesdepselesnul.sipakerserver.Manager.KVStoreManager;
import selesdepselesnul.sipakerserver.TimeString;
import selesdepselesnul.sipakerserver.model.*;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class ParkingAreasKVStoreRealTest {
    public static void main(String[] args) {
        MemberParkingsKVStore memberParkingsKVStore = new MemberParkingsKVStore(new KVStoreManager());
//        memberParkingsKVStore.dropCollection();
//        memberParkingsKVStore.makeEmptyCollection();
//        new ParkingAreasKVStore(new KVStoreManager()).create(2);
//        new ParkingAreasKVStore(new KVStoreManager()).dropCollection();
        MemberRequestsKVStore memberRequest = new MemberRequestsKVStore(new KVStoreManager());
//        memberRequest.dropCollection();
//        memberRequest.makeEmptyCollection();
        memberRequest.store(new MemberRequest(1, 2, "A1234", TimeString.now()));
//        System.out.println(memberRequest.dequeu().getMemberId());
//        memberRequest.store(new MemberRequest(1, 2, "A1234", TimeString.now()));
    }
}
