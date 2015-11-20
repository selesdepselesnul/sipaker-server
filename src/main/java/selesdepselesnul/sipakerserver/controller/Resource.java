package selesdepselesnul.sipakerserver.controller;

import java.io.InputStream;
import java.net.URL;

public class Resource {

    public static class Ui{
        public static final URL MAIN_LAYOUT = ClassLoader.getSystemResource("fxml/main.fxml");
    }

    public static class Image {
        public static InputStream lock() {
            return ClassLoader.getSystemResourceAsStream("image/lock.png");
        }
    }
}
