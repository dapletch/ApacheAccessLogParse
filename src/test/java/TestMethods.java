import com.logparse.beans.timeaccessed.AvgTimeAccessedDayCnt;
import com.logparse.beans.timeaccessed.TimeAccessedDayCnt;
import com.logparse.beans.timeaccessed.TimeAccessedDayPreReqs;
import com.logparse.dao.timeaccessed.GetTimeAccessedDayCnts;
import com.logparse.dao.jdbc.JDBCConnectionUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seth on 11/21/2016.
 */
public class TestMethods {

    private GetTimeAccessedDayCnts getTimeAccessedDayCnts = new GetTimeAccessedDayCnts();

    private TimeAccessedDayPreReqs timeAccessedDayPreReqs = new TimeAccessedDayPreReqs();

    private AvgTimeAccessedDayCnt avgTimeAccessedDayCnt = new AvgTimeAccessedDayCnt();

    private JDBCConnectionUtils jdbcConnectionUtils = new JDBCConnectionUtils();

    private List<TimeAccessedDayCnt> timeAccessedDayCntList = new ArrayList<TimeAccessedDayCnt>();

    private Connection connection = null;

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

    /*
    @Test
    public void getPreRequisiteDates() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            connection = jdbcConnectionUtils.getConnection();
        }

        timeAccessedDayPreReqs = getTimeAccessedDayCnts.getMaxTimeEntered(connection);
        System.out.println("Max Time Entered: " + timeAccessedDayPreReqs.getMaxTimeEntered());

        timeAccessedDayPreReqs = getTimeAccessedDayCnts.getTimeAccessedDateRange(connection, timeAccessedDayPreReqs);
        System.out.println("Prerequisite Date Ranges: " + timeAccessedDayPreReqs.toString());

        timeAccessedDayCntList = getTimeAccessedDayCnts.timeAccessedDayCntReport(connection, timeAccessedDayPreReqs);
        getTimeAccessedDayCnts.insertDayCntReportToDatabase(connection, timeAccessedDayCntList);

        avgTimeAccessedDayCnt = getTimeAccessedDayCnts.getAvgTimeAccessedDayCnt(connection, timeAccessedDayPreReqs.getMaxTimeEntered());

        jdbcConnectionUtils.closeConnection();
    }
    */
}
