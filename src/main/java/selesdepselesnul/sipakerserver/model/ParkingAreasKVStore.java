package selesdepselesnul.sipakerserver.model;

import selesdepselesnul.sipakerserver.KVStoreManager;

import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class ParkingAreasKVStore implements ParkingAreas {
    private static final String PARKING_AREAS_COLLECTION = "ParkingAreas";
    private static final String PARKING_AREA = "ParkingArea";
    private final KVStoreManager kvStoreManager;

    public ParkingAreasKVStore(KVStoreManager kvStoreManager) {
        this.kvStoreManager = kvStoreManager;
    }

    private void updateSize(int newSize) {
        this.kvStoreManager.storeValue(PARKING_AREAS_COLLECTION, "size", String.valueOf(newSize));
    }


    @Override
    public void increase() {
        final int nextSize = this.size() + 1;
        createDefaultParkingArea(nextSize);
        updateSize(nextSize);
    }


    @Override
    public void decerease() {
        final int currentSize = this.size();
        this.kvStoreManager.deleteCollection(PARKING_AREA + currentSize);
        updateSize(currentSize - 1);
    }

    @Override
    public Optional<ParkingArea> get(int id) {
        final String parkingArea = PARKING_AREA + id;
        return Optional.of(
                new ParkingArea(
                        id,
                        Boolean.getBoolean(this.kvStoreManager.getValue(parkingArea, "isAvailable").orElse("true")),
                        Integer.valueOf(this.kvStoreManager.getValue(parkingArea, "memberId").orElse("")),
                        this.kvStoreManager.getValue(parkingArea, "policeNumber").orElse(""),
                        this.kvStoreManager.getValue(parkingArea, "checkIn").orElse(""),
                        this.kvStoreManager.getValue(parkingArea, "checkOut").orElse(""))
        );
    }

    @Override
    public Stream<ParkingArea> stream() {
        return IntStream.rangeClosed(1, size()).mapToObj(i ->
                new ParkingArea(
                        i,
                        Boolean.parseBoolean(this.kvStoreManager.getValue(PARKING_AREA + i, "isAvailable").orElse("true")),
                        Integer.valueOf(this.kvStoreManager.getValue(PARKING_AREA + i, "memberId").orElse("")),
                        this.kvStoreManager.getValue(PARKING_AREA + i, "policeNumber").orElse(""),
                        this.kvStoreManager.getValue(PARKING_AREA + i, "checkIn").orElse(""),
                        this.kvStoreManager.getValue(PARKING_AREA + i, "checkOut").orElse("")
                ));
    }

    @Override
    public int size() {
        return Integer.valueOf(this.kvStoreManager.getValue(PARKING_AREAS_COLLECTION, "size").orElse("-1"));
    }

    @Override
    public void create(int size) {
        IntStream.rangeClosed(1, size).forEachOrdered(this::createDefaultParkingArea);
        this.kvStoreManager.createCollection(PARKING_AREAS_COLLECTION);
        this.kvStoreManager.storeValue(PARKING_AREAS_COLLECTION, "size", String.valueOf(size));
    }

    private void createDefaultParkingArea(int id) {
        final String parkingArea = PARKING_AREA + id;
        this.kvStoreManager.createCollection(parkingArea);
        this.kvStoreManager.storeValue(parkingArea, "id", String.valueOf(id));
        this.kvStoreManager.storeValue(parkingArea, "isAvailable", "true");
        this.kvStoreManager.storeValue(parkingArea, "memberId", "-1");
        this.kvStoreManager.storeValue(parkingArea, "policeNumber", "");
        this.kvStoreManager.storeValue(parkingArea, "checkIn", "");
        this.kvStoreManager.storeValue(parkingArea, "checkOut", "");
    }

    @Override
    public void dropAll() {
        IntStream.rangeClosed(1, size()).forEachOrdered(i -> kvStoreManager.deleteCollection(PARKING_AREA + i));
        this.kvStoreManager.deleteCollection(PARKING_AREAS_COLLECTION);
    }
}
