package selesdepselesnul.sipakerserver.Manager;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Optional;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class KVStoreManager {

    private static final String KV_STORE_URL = "https://kvstore.p.mashape.com/collections/";
    private static final String X_MASHAPE_KEY_KEY = "X-Mashape-Key";
    private static final String X_MASHAPE_KEY_VALUE = "S60LBMB0ivmshGLcOVyPhT6KTFITp1jjiszjsnQpNmujBNVPuS";

    @FunctionalInterface
    interface UnirestRunnable {
        void run() throws UnirestException;
    }

    private boolean isItWork(UnirestRunnable unirestRunnable) {
        try {
            unirestRunnable.run();
            return true;
        } catch (UnirestException e) {
            return false;
        }
    }


    public boolean deleteCollection(String collectionName) {
        return isItWork(() -> Unirest.delete(KV_STORE_URL + collectionName)
                .header(X_MASHAPE_KEY_KEY, X_MASHAPE_KEY_VALUE)
                .asJson());
    }

    public boolean createCollection(String collectionName) {
        return isItWork(() -> Unirest.post(KV_STORE_URL)
                .header(X_MASHAPE_KEY_KEY, X_MASHAPE_KEY_VALUE)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body("{\"collection\":\"" + collectionName + "\"}")
                .asJson());
    }

    public boolean storeValue(String collectionName, String key, String value) {
        return isItWork(() -> Unirest.put(KV_STORE_URL + collectionName
                + "/items/" + key).header(X_MASHAPE_KEY_KEY, X_MASHAPE_KEY_VALUE)
                .body(value)
                .asJson());
    }

    public Optional<String> getValue(String collectionName, String key) {
        try {
            return Optional.of(Unirest.get(KV_STORE_URL + collectionName
                    + "/items/" + key).header(X_MASHAPE_KEY_KEY, X_MASHAPE_KEY_VALUE)
                    .asJson().getBody().getObject().getString("value"));
        } catch (UnirestException e) {
            return Optional.empty();
        }
    }

}
