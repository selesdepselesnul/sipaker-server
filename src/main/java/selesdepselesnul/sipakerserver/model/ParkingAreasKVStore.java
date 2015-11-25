package selesdepselesnul.sipakerserver.model;

import selesdepselesnul.sipakerserver.Manager.KVStoreManager;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class ParkingAreasKVStore {
    private static final String PARKING_AREAS_COLLECTION = "ParkingAreas";
    private static final String PARKING_AREA = "ParkingArea";
    private final KVStoreManager kvStoreManager;

    public ParkingAreasKVStore(KVStoreManager kvStoreManager) {
        this.kvStoreManager = kvStoreManager;
    }

    private boolean updateSize(int newSize) {
        return this.kvStoreManager.storeValue(PARKING_AREAS_COLLECTION, "size", String.valueOf(newSize));
    }


    public boolean increase() {
        final int nextSize = this.size() + 1;
        return createDefaultParkingArea(nextSize)
                &&
                updateSize(nextSize);
    }


    public boolean decrease() {
        final int currentSize = this.size();
        return this.kvStoreManager.deleteCollection(PARKING_AREA + currentSize)
                &&
                updateSize(currentSize - 1);
    }

    public Optional<ParkingArea> get(int id) {
        final String parkingArea = PARKING_AREA + id;
        if (this.kvStoreManager.getValue(parkingArea, "isAvailable").isPresent())
            return Optional.of(
                    new ParkingArea(
                            id,
                            Boolean.parseBoolean(this.kvStoreManager.getValue(parkingArea, "isAvailable").get()),
                            Integer.valueOf(this.kvStoreManager.getValue(parkingArea, "memberId").get()),
                            this.kvStoreManager.getValue(parkingArea, "policeNumber").get(),
                            this.kvStoreManager.getValue(parkingArea, "checkIn").get(),
                            this.kvStoreManager.getValue(parkingArea, "checkOut").get())
            );
        else
            return Optional.empty();
    }

    public Stream<Optional<ParkingArea>> stream() {
        return IntStream.rangeClosed(1, size())
                .mapToObj(
                        i -> Optional.of(
                                new ParkingArea(
                                        i,
                                        Boolean.parseBoolean(this.kvStoreManager.getValue(PARKING_AREA + i, "isAvailable").get()),
                                        Integer.valueOf(this.kvStoreManager.getValue(PARKING_AREA + i, "memberId").get()),
                                        this.kvStoreManager.getValue(PARKING_AREA + i, "policeNumber").get(),
                                        this.kvStoreManager.getValue(PARKING_AREA + i, "checkIn").get(),
                                        this.kvStoreManager.getValue(PARKING_AREA + i, "checkOut").get()
                                )));
    }

    public int size() {
        return Integer.valueOf(this.kvStoreManager.getValue(PARKING_AREAS_COLLECTION, "size").orElse("-1"));
    }

    public boolean create(int size) {
        IntStream.rangeClosed(1, size).forEachOrdered(this::createDefaultParkingArea);
        return this.kvStoreManager.createCollection(PARKING_AREAS_COLLECTION)
                &&
                this.kvStoreManager.storeValue(PARKING_AREAS_COLLECTION, "size", String.valueOf(size));
    }

    private boolean createDefaultParkingArea(int id) {
        return store(new ParkingArea(id, true, -1, "", "", ""));
    }

    public boolean store(ParkingArea parkingArea) {
        final String parkingAreaNumber = PARKING_AREA + parkingArea.id;
        return this.kvStoreManager.createCollection(parkingAreaNumber)
                &&
                this.kvStoreManager.storeValue(parkingAreaNumber, "id", String.valueOf(parkingArea.id))
                &&
                this.kvStoreManager.storeValue(parkingAreaNumber, "isAvailable", String.valueOf(parkingArea.isAvailable))
                &&
                this.kvStoreManager.storeValue(parkingAreaNumber, "memberId", String.valueOf(parkingArea.memberParking.memberId))
                &&
                this.kvStoreManager.storeValue(parkingAreaNumber, "policeNumber", parkingArea.memberParking.policeNumber)
                &&
                this.kvStoreManager.storeValue(parkingAreaNumber, "checkIn", parkingArea.memberParking.checkIn)
                &&
                this.kvStoreManager.storeValue(parkingAreaNumber, "checkOut", parkingArea.memberParking.checkOut);
    }

    public boolean dropCollection() {
        IntStream.rangeClosed(1, size()).forEachOrdered(i -> kvStoreManager.deleteCollection(PARKING_AREA + i));
        return this.kvStoreManager.deleteCollection(PARKING_AREAS_COLLECTION);
    }

    public boolean update(ParkingArea parkingArea) {
        return store(parkingArea);
    }
}
