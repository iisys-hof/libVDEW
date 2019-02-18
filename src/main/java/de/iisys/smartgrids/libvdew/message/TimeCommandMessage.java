package de.iisys.smartgrids.libvdew.message;

import de.iisys.libinterface.message.annotation.Callback;
import de.iisys.libinterface.message.annotation.MessageTemplate;
import de.iisys.smartgrids.libiec62056.message.CommandMessage;
import de.iisys.smartgrids.libiec62056.message.content.CommandMessageIdentifier;
import de.iisys.smartgrids.libvdew.message.content.ReadTime;
import de.iisys.smartgrids.libvdew.message.content.VDEWCommandType;
import de.iisys.smartgrids.libvdew.message.content.WriteTime;
import de.iisys.smartgrids.libvdew.service.DateTimeService;
import java.util.Calendar;

/**
 * Class to process the VDEW write and read command with the parsed time.
 *
  */
@MessageTemplate("<SOH>~{1:commandMessageIdentifier}{1:commandType}<STX>[{address}]\\([{value}][*{unit}]\\)[\\({password}\\)]<ETX>:bcc~!bcc!")
@Callback("prepareCommand")
public class TimeCommandMessage extends CommandMessage {

    private String password;

    /**
     * Initializes the constructor of {@link CommandMessage}. <br>
     * Identifies the command message as 'read command', sets the read command and 
     * creates a new {@link ReadTime} object.
     * @see de.iisys.smartgrids.libiec62056.message.CommandMessage#CommandMessage(char, de.iisys.smartgrids.libiec62056.message.content.ReadCommand)
     */
    public TimeCommandMessage() {
        super(CommandMessageIdentifier.READ_COMMAND, VDEWCommandType.ReadCommand.VDEW_READ, new ReadTime());
    }

    /**
     * Initializes the constructor of {@link CommandMessage}. <br>
     * Identifies the command message as 'write command', sets the write command and 
     * creates a new {@link WriteDate} object. Also initializes {@link #password}
     * with the given password.
     * @see de.iisys.smartgrids.libiec62056.message.CommandMessage#CommandMessage(char, de.iisys.smartgrids.libiec62056.message.content.WriteCommand)
     *
     * @param time the time 
     * @param password the password
     */
    public TimeCommandMessage(Calendar time, String password) {
        super(CommandMessageIdentifier.WRITE_COMMAND, VDEWCommandType.WriteCommand.VDEW_WRITE, new WriteTime(time));
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Gets the parsed time.
     *
     * @return the parsed time.
     */
    public Calendar getTime() {
        return DateTimeService.parseTime(getDataSet().getValue());
    }

}
