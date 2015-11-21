package selesdepselesnul.sipakerserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {
    public static final DateTimeFormatter D_T_FORMATTER_READABLE = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
    public static final DateTimeFormatter D_T_FORMATTER_URL_FRIENDLY = DateTimeFormatter.ofPattern("HHmmssddMMyyyy");

    public static String toUrlFriendly(String dateTime) {
        return LocalDateTime.parse(dateTime, D_T_FORMATTER_READABLE).format(D_T_FORMATTER_URL_FRIENDLY);
    }
}
