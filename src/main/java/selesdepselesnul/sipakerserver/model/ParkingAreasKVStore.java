package selesdepselesnul.sipakerserver.model;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
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
//            HttpResponse<JsonNode> response = Unirest.put("https://kvstore.p.mashape.com/collections/" + PARKING_AREAS_COLLECTION
//                    + "/items/size").header("X-Mashape-Key", "S60LBMB0ivmshGLcOVyPhT6KTFITp1jjiszjsnQpNmujBNVPuS")
//                    .body(String.valueOf(newSize))
//                    .asJson();
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

//    private String getValue(String collectionName, String key) {
//        try {
//            return Unirest.get("https://kvstore.p.mashape.com/collections/" + collectionName
//                    + "/items/" + key).header("X-Mashape-Key", "S60LBMB0ivmshGLcOVyPhT6KTFITp1jjiszjsnQpNmujBNVPuS")
//                    .asJson().getBody().getObject().getString("value");
//        } catch (UnirestException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    @Override
    public int size() {
        try {
            HttpResponse<JsonNode> response = Unirest.get("https://kvstore.p.mashape.com/collections/" + PARKING_AREAS_COLLECTION
                    + "/items/size")
                    .header("X-Mashape-Key", "S60LBMB0ivmshGLcOVyPhT6KTFITp1jjiszjsnQpNmujBNVPuS")
                    .asJson();
            return response.getBody().getObject().getInt("value");
        } catch (UnirestException e) {
            return 0;
        }
    }

    @Override
    public void create(int size) {
        IntStream.rangeClosed(1, size).forEachOrdered(i -> {
            createDefaultParkingArea(i);
        });
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

//    private Optional<HttpResponse<JsonNode>> createCollection(String collectionName) {
//        try {
//            return Optional.of(Unirest.post("https://kvstore.p.mashape.com/collections")
//                    .header("X-Mashape-Key", "S60LBMB0ivmshGLcOVyPhT6KTFITp1jjiszjsnQpNmujBNVPuS")
//                    .header("Content-Type", "application/json")
//                    .header("Accept", "application/json")
//                    .body("{\"collection\":\"" + collectionName + "\"}")
//                    .asJson());
//        } catch (UnirestException e) {
//            e.printStackTrace();
//        }
//        return Optional.empty();
//    }

//    private Optional<HttpResponse<JsonNode>> storeValue(String collectionName, String key, String value) {
//        try {
//            return Optional.of(Unirest.put("https://kvstore.p.mashape.com/collections/" + collectionName
//                    + "/items/" + key).header("X-Mashape-Key", "S60LBMB0ivmshGLcOVyPhT6KTFITp1jjiszjsnQpNmujBNVPuS")
//                    .body(value)
//                    .asJson());
//        } catch (UnirestException e) {
//            e.printStackTrace();
//        }
//        return Optional.empty();
//    }

//    private Optional<HttpResponse<JsonNode>> deleteCollection(String collectionName) {
//        try {
//            return Optional.of(Unirest.delete("https://kvstore.p.mashape.com/collections/" + collectionName)
//                    .header("X-Mashape-Key", "S60LBMB0ivmshGLcOVyPhT6KTFITp1jjiszjsnQpNmujBNVPuS")
//                    .asJson());
//        } catch (UnirestException e) {
//            e.printStackTrace();
//        }
//        return Optional.empty();
//    }

    @Override
    public void dropAll() {
        IntStream.rangeClosed(1, size()).forEachOrdered(i -> kvStoreManager.deleteCollection(PARKING_AREA + i));
        this.kvStoreManager.deleteCollection(PARKING_AREAS_COLLECTION);
    }
}
