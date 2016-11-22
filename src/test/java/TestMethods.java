import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

/**
 * Created by Seth on 11/21/2016.
 */
public class TestMethods {

    @Test
    public void formatDate() {
        String dateStr = "02/Jun/2016:21:16:24 -0400";
        System.out.println(DateTime.parse(dateStr, DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss Z")).toDateTime());
    }

    @Test
    public void getCurrentDateTime() {
        String dateStr = String.valueOf(DateTime.now());
        System.out.println(DateTime.parse(dateStr, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).toDateTime());
    }
}
