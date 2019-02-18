package de.iisys.smartgrids.libvdew.message;

import de.iisys.libinterface.message.annotation.Callback;
import de.iisys.libinterface.message.annotation.MessageTemplate;
import de.iisys.smartgrids.libiec62056.message.DataBlockMessage;
import de.iisys.smartgrids.libvdew.service.DateTimeService;
import java.util.Calendar;

/**
 * Class to get the parsed date.
 *
  */
@MessageTemplate("<STX>~[{address}]\\([{value}][*{unit}]\\)<ETX>:bcc~!bcc!")
@Callback("processDataSet")
public class DateMessage extends DataBlockMessage {

    /**
     * Gets the parsed date.
     *
     * @return parsed date
     */
    public Calendar getDate() {
        return DateTimeService.parseDate(getDataSet().getValue());
    }

    @Override
    public String toString() {
        return String.format("%1$tY-%1$tm-%1$td", getDate());
    }

}
