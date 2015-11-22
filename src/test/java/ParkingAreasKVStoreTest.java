import selesdepselesnul.sipakerserver.Manager.KVStoreManager;
import selesdepselesnul.sipakerserver.TimeString;
import selesdepselesnul.sipakerserver.model.*;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class ParkingAreasKVStoreTest {
    public static void main(String[] args) {
//        MemberParkingsKVStore memberParkingsKVStore = new MemberParkingsKVStore(new KVStoreManager());
//        memberParkingsKVStore.dropAll();
//        memberParkingsKVStore.init();
//        new ParkingAreasKVStore(new KVStoreManager()).create(2);
//        new ParkingAreasKVStore(new KVStoreManager()).dropAll();
        MemberRequests memberRequest = new MemberRequestsKVStore(new KVStoreManager());
//        memberRequest.dropAll();
//        memberRequest.init();
        memberRequest.store(new MemberRequest(1, 2, "A1234", TimeString.now()));
//        System.out.println(memberRequest.dequeu().getMemberId());
//        memberRequest.store(new MemberRequest(1, 2, "A1234", TimeString.now()));
    }
}
