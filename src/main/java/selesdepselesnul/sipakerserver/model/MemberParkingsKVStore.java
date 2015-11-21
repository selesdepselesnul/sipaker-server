package selesdepselesnul.sipakerserver.model;

import selesdepselesnul.sipakerserver.KVStoreManager;

import java.util.stream.IntStream;

/**
 * Created by morrisseymarr on 11/21/15.
 */
public class MemberParkingsKVStore implements MemberParkings {

    private static final String COLLECTION_NAME = "MemberParkings";
    private static final String ITEM_NAME = "ParkingAreaLog";
    private final KVStoreManager kvStoreManager;

    public MemberParkingsKVStore(KVStoreManager kvStoreManager) {
        this.kvStoreManager = kvStoreManager;

    }
    public void init() {
        kvStoreManager.createCollection(COLLECTION_NAME);
        kvStoreManager.storeValue(COLLECTION_NAME, "length", "0");
    }

    @Override
    public int length() {
        return Integer.parseInt(kvStoreManager.getValue(COLLECTION_NAME, "length").get());
    }

    @Override
    public void store(ParkingArea parkingArea) {
        final int nextLength = length() + 1;
        final String parkingAreaLog = ITEM_NAME + nextLength;

        kvStoreManager.createCollection(parkingAreaLog);
        kvStoreManager.storeValue(parkingAreaLog, "parkingAreaId", String.valueOf(parkingArea.id));
        kvStoreManager.storeValue(parkingAreaLog, "memberId", String.valueOf(parkingArea.memberId));
        kvStoreManager.storeValue(parkingAreaLog, "policeNumber", parkingArea.policeNumber);
        kvStoreManager.storeValue(parkingAreaLog, "checkIn", parkingArea.checkIn);
        kvStoreManager.storeValue(parkingAreaLog, "checkOut", parkingArea.checkOut);

        kvStoreManager.createCollection(COLLECTION_NAME);
        kvStoreManager.storeValue(COLLECTION_NAME, "length", String.valueOf(nextLength));
    }

    @Override
    public void dropAll() {
        IntStream.rangeClosed(1, length()).forEachOrdered(i -> kvStoreManager.deleteCollection(ITEM_NAME + i));
        kvStoreManager.deleteCollection(COLLECTION_NAME);
    }

}
