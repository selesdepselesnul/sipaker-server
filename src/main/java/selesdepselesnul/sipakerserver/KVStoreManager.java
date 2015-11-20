package selesdepselesnul.sipakerserver;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.Optional;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class KVStoreManager {

    public Optional<HttpResponse<JsonNode>> deleteCollection(String collectionName) {
        try {
            return Optional.of(Unirest.delete("https://kvstore.p.mashape.com/collections/" + collectionName)
                    .header("X-Mashape-Key", "S60LBMB0ivmshGLcOVyPhT6KTFITp1jjiszjsnQpNmujBNVPuS")
                    .asJson());
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<HttpResponse<JsonNode>> createCollection(String collectionName) {
        try {
            return Optional.of(Unirest.post("https://kvstore.p.mashape.com/collections")
                    .header("X-Mashape-Key", "S60LBMB0ivmshGLcOVyPhT6KTFITp1jjiszjsnQpNmujBNVPuS")
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .body("{\"collection\":\"" + collectionName + "\"}")
                    .asJson());
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<HttpResponse<JsonNode>> storeValue(String collectionName, String key, String value) {
        try {
            return Optional.of(Unirest.put("https://kvstore.p.mashape.com/collections/" + collectionName
                    + "/items/" + key).header("X-Mashape-Key", "S60LBMB0ivmshGLcOVyPhT6KTFITp1jjiszjsnQpNmujBNVPuS")
                    .body(value)
                    .asJson());
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<String> getValue(String collectionName, String key) {
        try {
            return Optional.of(Unirest.get("https://kvstore.p.mashape.com/collections/" + collectionName
                    + "/items/" + key).header("X-Mashape-Key", "S60LBMB0ivmshGLcOVyPhT6KTFITp1jjiszjsnQpNmujBNVPuS")
                    .asJson().getBody().getObject().getString("value"));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


}
