import com.logparse.beans.TimeAccessedDayCnt;
import com.logparse.beans.TimeAccessedDayPreReqs;
import com.logparse.dao.GetTimeAccessedDayCnts;
import com.logparse.dao.JDBCConnectionUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seth on 11/21/2016.
 */
public class TestMethods {

    private GetTimeAccessedDayCnts getTimeAccessedDayCnts = new GetTimeAccessedDayCnts();

    private TimeAccessedDayPreReqs timeAccessedDayPreReqs = new TimeAccessedDayPreReqs();

    private JDBCConnectionUtils jdbcConnectionUtils = new JDBCConnectionUtils();

    private Connection connection = null;

    private List<TimeAccessedDayCnt> timeAccessedDayCntList = new ArrayList<TimeAccessedDayCnt>();

    /*
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

    @Test
    public void getCurrentWorkingDirectoryWindows() {
        String dir = System.getProperty("user.dir");
        Integer index = dir.lastIndexOf("\\");
        System.out.println(dir.substring(0, index) + "\\");
    }
    */

    @Test
    public void getPreRequisiteDates() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }

        timeAccessedDayPreReqs = getTimeAccessedDayCnts.getMaxTimeEntered();

        System.out.println("Max Time Entered: " + timeAccessedDayPreReqs.getMaxTimeEntered());

        timeAccessedDayPreReqs = getTimeAccessedDayCnts.getTimeAccessedDateRange(timeAccessedDayPreReqs);
        System.out.println("Prerequisite Date Ranges: " + timeAccessedDayPreReqs.toString());

        timeAccessedDayCntList = getTimeAccessedDayCnts.timeAccessedDayCntReport(timeAccessedDayPreReqs);

        jdbcConnectionUtils.closeConnection();
    }
}
