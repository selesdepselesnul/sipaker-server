package selesdepselesnul.sipakerserver.model;

import selesdepselesnul.sipakerserver.Manager.KVStoreManager;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class MemberRequestsKVStore implements MemberRequests {

    private final KVStoreManager kvStoreManager;

    private static final String COLLECTION_NAME = "MemberRequests";
    private static final String ITEM_NAME = "MemberRequest";

    private int headIndex = 1;

    public MemberRequestsKVStore(KVStoreManager kvStoreManager) {
        this.kvStoreManager = kvStoreManager;
    }

    @Override
    public void init() {
        this.kvStoreManager.createCollection(COLLECTION_NAME);
        this.kvStoreManager.storeValue(COLLECTION_NAME, "length", "0");
    }

    @Override
    public void store(MemberRequest memberRequest) {
        final int nextLength = length() + 1;
        final String memberRequestItem = ITEM_NAME + nextLength;

        this.kvStoreManager.createCollection(memberRequestItem);
        this.kvStoreManager.storeValue(memberRequestItem, "id", String.valueOf(nextLength));
        this.kvStoreManager.storeValue(memberRequestItem, "memberId", String.valueOf(memberRequest.getMemberId()));
        this.kvStoreManager.storeValue(memberRequestItem, "policeNumber", memberRequest.getPoliceNumber());
        this.kvStoreManager.storeValue(memberRequestItem, "requestTime", memberRequest.getRequestTime());

        this.kvStoreManager.storeValue(COLLECTION_NAME, "length", String.valueOf(nextLength));
    }

    @Override
    public MemberRequest get(int index) {
        final String selectedItem = ITEM_NAME + index;
        return new MemberRequest(
                Integer.parseInt(this.kvStoreManager.getValue(selectedItem, "id").get()),
                Integer.parseInt(this.kvStoreManager.getValue(selectedItem, "memberId").get()),
                this.kvStoreManager.getValue(selectedItem, "policeNumber").get(),
                this.kvStoreManager.getValue(selectedItem, "requestTime").get()
        );
    }

    @Override
    public Stream<MemberRequest> stream() {
        return IntStream.rangeClosed(1, length()).mapToObj(this::get);
    }

    @Override
    public int length() {
        return Integer.parseInt(this.kvStoreManager.getValue(COLLECTION_NAME, "length").orElse("0"));
    }

    @Override
    public void dropAll() {
        IntStream.rangeClosed(1, length()).forEachOrdered(i -> kvStoreManager.deleteCollection(ITEM_NAME + i));
        this.kvStoreManager.deleteCollection(COLLECTION_NAME);
    }

    @Override
    public MemberRequest dequeu() {
        int currentLength = length();
        if (this.headIndex <= currentLength) {
            MemberRequest memberRequest = get(this.headIndex);
            this.kvStoreManager.deleteCollection(ITEM_NAME + this.headIndex++);
            this.kvStoreManager.storeValue(COLLECTION_NAME, "length", String.valueOf(currentLength - 1));
            return memberRequest;
        }
        return null;
    }
}
