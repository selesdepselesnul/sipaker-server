package selesdepselesnul.sipakerserver.model;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class ParkingAreasKVStore implements ParkingAreas {
    private static final String DB_NAME = "ParkingAreas";

    @Override
    public void increase() {
    }

    @Override
    public void decerease() {

    }

    @Override
    public int size() {
        try {
            HttpResponse<JsonNode> response = Unirest.get("https://kvstore.p.mashape.com/collections/" + DB_NAME
                    + "/items/size")
                    .header("X-Mashape-Key", "S60LBMB0ivmshGLcOVyPhT6KTFITp1jjiszjsnQpNmujBNVPuS")
                    .asJson();
            return response.getBody().getObject().getInt("value");
        } catch (UnirestException e) {
            return -1;
        }
    }
}
