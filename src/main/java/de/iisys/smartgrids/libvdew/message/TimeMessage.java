package de.iisys.smartgrids.libvdew.message;

import de.iisys.libinterface.message.annotation.Callback;
import de.iisys.libinterface.message.annotation.MessageTemplate;
import de.iisys.smartgrids.libiec62056.message.DataBlockMessage;
import de.iisys.smartgrids.libvdew.service.DateTimeService;
import java.util.Calendar;

/**
 * Class to return the parsed time.
 *
  */
@MessageTemplate("<STX>~[{address}]\\([{value}][*{unit}]\\)<ETX>:bcc~!bcc!")
@Callback("processDataSet")
public class TimeMessage extends DataBlockMessage {

    /**
     * Gets the parsed time.
     *
     * @return parsed time
     */
    public Calendar getTime() {
        return DateTimeService.parseTime(getDataSet().getValue());
    }

    @Override
    public String toString() {
        return String.format("%1$tH:%1$tM:%1$tS", getTime());
    }

}
