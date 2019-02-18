package de.iisys.smartgrids.libvdew.message;

import de.iisys.libinterface.message.annotation.Callback;
import de.iisys.libinterface.message.annotation.MessageTemplate;
import de.iisys.libinterface.message.interfaces.Message;
import de.iisys.smartgrids.libvdew.message.content.LoadProfile;
import de.iisys.smartgrids.libvdew.service.DateTimeService;
import java.util.Calendar;
import java.util.List;

/**
 * Class to get the chronological reconstruction of the load profile records.
  */
@MessageTemplate("<STX>~{profileName}\\({startingDate}\\)\\({statusField}\\)\\({timePeriod}\\)\\({measurementsPerPeriod}\\)\\(({columns}:\\)\\()\\)<CR><LF>(\\(({rows}:\\)\\()\\):<CR><LF>)<CR><LF><ETX>:bcc~!bcc!")
@Callback("parseRows")
public class LoadProfileMessage implements Message {

    /**
     * Name of the load profile.
     */
    private String profileName;
    /**
     * The starting date of the load profile.
     */
    private String startingDate;
    /**
     * The status field of the load profile.
     */
    private String statusField;
    /**
     * The period of time of the load profile.
     */
    private int timePeriod;
    /**
     * The measurements per period of the load profile.
     */
    private int measurementsPerPeriod;
    private List<String> columns;
    @Callback("parseColumnHeader")
    private String columnHeader;
    private String[] columnObisHeaders;
    private String[] columnFieldUnits;
    private List<String> rows;
    private String[][] rowMatrix;

    private LoadProfile loadProfile;

    /**
     * Default constructor.
     */
    public LoadProfileMessage() {
    }

    protected void parseColumnHeader() {

    }

    protected void parseRows() {

    }

    public String getProfileName() {
        return profileName;
    }

    /**
     * Gets the starting date.
     *
     * @return parsed {@link #startingDate}
     */
    public Calendar getStartingDate() {
        return DateTimeService.parseDateTime(startingDate);
    }

    public String getStatusField() {
        return statusField;
    }

    public int getTimePeriod() {
        return timePeriod;
    }

    public int getMeasurementsPerPeriod() {
        return measurementsPerPeriod;
    }

}
