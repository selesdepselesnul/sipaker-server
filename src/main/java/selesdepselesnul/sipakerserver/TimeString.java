package selesdepselesnul.sipakerserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Moch Deden (https://github.com/selesdepselesnul)
 */
public class TimeString {
    public static final DateTimeFormatter INDONESIAN_D_T_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");

    public static String now() {
        return LocalDateTime.now().format(INDONESIAN_D_T_FORMATTER);
    }

}
