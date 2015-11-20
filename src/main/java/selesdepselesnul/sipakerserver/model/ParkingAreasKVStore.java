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

    private void updateSize(int newSize) {
        try {
            HttpResponse<JsonNode> response = Unirest.put("https://kvstore.p.mashape.com/collections/" + DB_NAME
                    + "/items/size").header("X-Mashape-Key", "S60LBMB0ivmshGLcOVyPhT6KTFITp1jjiszjsnQpNmujBNVPuS")
                    .body(String.valueOf(newSize))
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void increase() {
        updateSize(this.size() + 1);
    }


    @Override
    public void decerease() {
        updateSize(this.size() - 1);
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
