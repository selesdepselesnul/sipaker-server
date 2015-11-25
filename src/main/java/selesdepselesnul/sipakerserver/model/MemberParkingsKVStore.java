package selesdepselesnul.sipakerserver.model;

import selesdepselesnul.sipakerserver.Manager.KVStoreManager;

import java.util.stream.IntStream;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class MemberParkingsKVStore {

    private static final String COLLECTION_NAME = "MemberParkings";
    private static final String ITEM_NAME = "MemberParking";
    private final KVStoreManager kvStoreManager;

    public MemberParkingsKVStore(KVStoreManager kvStoreManager) {
        this.kvStoreManager = kvStoreManager;

    }

    public boolean makeEmptyCollection() {
        return kvStoreManager.createCollection(COLLECTION_NAME)
                &&
                kvStoreManager.storeValue(COLLECTION_NAME, "length", "0");
    }

    /**
     *
     * @return -1 if collection isn't created
     */
    public int length() {
        return Integer.parseInt(kvStoreManager.getValue(COLLECTION_NAME, "length").orElse("-1"));
    }

    public boolean update(MemberParking memberParking) {
        final int length = length();
        return storeMemberParking(ITEM_NAME + length, memberParking, length);
    }

    public boolean store(MemberParking memberParking) {
        final int nextLength = length() + 1;
        final String memberParkingItem = ITEM_NAME + nextLength;
        return storeMemberParking(memberParkingItem, memberParking, nextLength);
    }

    private boolean storeMemberParking(String collectionName, MemberParking memberParking, int length) {

        return kvStoreManager.createCollection(collectionName)
                &&
                kvStoreManager.storeValue(collectionName, "parkingAreaId", String.valueOf(memberParking.parkingAreaId))
                &&
                kvStoreManager.storeValue(collectionName, "memberId", String.valueOf(memberParking.memberId))
                &&
                kvStoreManager.storeValue(collectionName, "policeNumber", memberParking.policeNumber)
                &&
                kvStoreManager.storeValue(collectionName, "checkIn", memberParking.checkIn)
                &&
                kvStoreManager.storeValue(collectionName, "checkOut", memberParking.checkOut)
                &&
                kvStoreManager.createCollection(COLLECTION_NAME)
                &&
                kvStoreManager.storeValue(COLLECTION_NAME, "length", String.valueOf(length));

    }

    public boolean dropCollection() {
        IntStream.rangeClosed(1, length()).forEachOrdered(i -> kvStoreManager.deleteCollection(ITEM_NAME + i));
        return kvStoreManager.deleteCollection(COLLECTION_NAME);
    }

}
