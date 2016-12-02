import com.logparse.beans.timeaccessed.AvgTimeAccessedDayCnt;
import com.logparse.beans.timeaccessed.TimeAccessedDayCnt;
import com.logparse.beans.timeaccessed.TimeAccessedDayPreReqs;
import com.logparse.dao.timeaccessed.GetTimeAccessedDayCnts;
import com.logparse.dao.jdbc.JDBCConnectionUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static final int NUM_FIELDS = 9;

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

    @Test
    public void apache2LogParse() {

        String logEntryLine = "";
        String logEntryPattern = "^([\\d.]+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"";

        Pattern p = Pattern.compile(logEntryPattern);
        Matcher matcher = null;

        try {
            BufferedReader in = new BufferedReader(new FileReader("C:/Users/Seth/Desktop/log-files/sigmadeltachi-logs/sigmadeltachicsc.txt"));

            while ((logEntryLine = in.readLine()) != null) {

                matcher = p.matcher(logEntryLine);
                matcher.find();

                if (!matcher.matches() ||
                        NUM_FIELDS != matcher.groupCount()) {
                    System.err.println("Problem with Expression Parsing");
                    return;
                }

                System.out.println("IP Address: " + matcher.group(1));
                System.out.println("Date&Time: " + matcher.group(4));
                System.out.println("Request: " + matcher.group(5));
                System.out.println("Response: " + matcher.group(6));
                System.out.println("Bytes Sent: " + matcher.group(7));

                if (!matcher.group(8).equals("-"))
                    System.out.println("Referer: " + matcher.group(8));

                System.out.println("Browser: " + matcher.group(9));

            } // end while

        } // end try

        catch (FileNotFoundException fne) {
            fne.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
