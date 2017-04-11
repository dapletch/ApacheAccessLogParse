import com.logparse.beans.timeaccessed.AvgTimeAccessedDayCnt;
import com.logparse.beans.timeaccessed.TimeAccessedDayCnt;
import com.logparse.beans.timeaccessed.TimeAccessedDayPreReqs;
import com.logparse.dao.timeaccessed.GetTimeAccessedDayCnts;
import com.logparse.dao.jdbc.JDBCConnectionUtils;
import com.logparse.reports.GenerateXLSXReport;
import com.logparse.utils.LogUtils;
import com.logparse.utils.OSUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.*;
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

    private Logger logger = Logger.getLogger(TestMethods.class);

    public static final int NUM_FIELDS = 9;

    @Test
    public void testMultipleSheetCreation() {
        File excelFile = new File(OSUtils.getFilePathAboveCurrentOne() + "PletcherWebDesignUsage" + LogUtils.generateFileTimeStamp() + ".xlsx");
        Boolean fileCreated;

        try {
            if (!excelFile.exists()) {
                fileCreated = excelFile.createNewFile();
                logger.info("File Created: " + excelFile + " " + fileCreated);
            } else {
                logger.info("File already exists: " + excelFile);
            }
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet1 = workbook.createSheet("Test1");
            XSSFSheet sheet2 = workbook.createSheet("Test3");
            XSSFSheet sheet3 = workbook.createSheet("Test4");

            Row row1 = sheet1.createRow(0);
            row1.createCell(0).setCellValue("Time Accessed");
            row1.createCell(1).setCellValue("12:00 am");
            row1.createCell(2).setCellValue("1:00 am");
            row1.createCell(3).setCellValue("2:00 am");
            row1.createCell(4).setCellValue("3:00 am");
            row1.createCell(5).setCellValue("4:00 am");
            row1.createCell(6).setCellValue("5:00 am");
            row1.createCell(7).setCellValue("6:00 am");
            row1.createCell(8).setCellValue("7:00 am");
            row1.createCell(9).setCellValue("8:00 am");
            row1.createCell(10).setCellValue("9:00 am");
            row1.createCell(11).setCellValue("10:00 am");
            row1.createCell(12).setCellValue("11:00 am");
            row1.createCell(13).setCellValue("12:00 pm");
            row1.createCell(14).setCellValue("1:00 pm");
            row1.createCell(15).setCellValue("2:00 pm");
            row1.createCell(17).setCellValue("3:00 pm");
            row1.createCell(18).setCellValue("4:00 pm");
            row1.createCell(19).setCellValue("5:00 pm");
            row1.createCell(20).setCellValue("6:00 pm");
            row1.createCell(21).setCellValue("7:00 pm");
            row1.createCell(22).setCellValue("8:00 pm");
            row1.createCell(23).setCellValue("9:00 pm");
            row1.createCell(24).setCellValue("10:00 pm");
            row1.createCell(25).setCellValue("11:00 pm");
            row1.createCell(26).setCellValue("Total Day Cnt");
            row1.createCell(27).setCellValue("Time Entered");

            Row row2 = sheet2.createRow(0);
            row2.createCell(0).setCellValue("IP Address");
            row2.createCell(1).setCellValue("Country Code");
            row2.createCell(2).setCellValue("Country Name");
            row2.createCell(3).setCellValue("Region");
            row2.createCell(4).setCellValue("Region Name");
            row2.createCell(5).setCellValue("City");
            row2.createCell(6).setCellValue("Postal Code");
            row2.createCell(7).setCellValue("Latitude");
            row2.createCell(8).setCellValue("Longitude");
            row2.createCell(9).setCellValue("DMA Code");
            row2.createCell(10).setCellValue("Area Code");
            row2.createCell(11).setCellValue("Metro Code");
            row2.createCell(12).setCellValue("Total Count");

            Row row3 = sheet3.createRow(0);
            row3.createCell(0).setCellValue("Country Name");
            row3.createCell(1).setCellValue("Country Code");
            row3.createCell(2).setCellValue("Region Name");
            row3.createCell(3).setCellValue("City");
            row3.createCell(4).setCellValue("Region Name");
            row3.createCell(5).setCellValue("Distinct IP Address Cnt");

            FileOutputStream outputStream = new FileOutputStream(excelFile);
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            logger.error("There was an error creating, and or, accessing the file: \n" + e);
        }
    }
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
