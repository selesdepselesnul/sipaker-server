package selesdepselesnul.sipakerserver.controller;

import java.net.URL;

public class Resource {

    public static class Ui{
        public static final URL MAIN_LAYOUT = ClassLoader.getSystemResource("fxml/main.fxml");
    }

    public static class Image {
        public static final String lock = "image/lock.png";

        public static final String unlock = "image/unlock.png";
    }
}
