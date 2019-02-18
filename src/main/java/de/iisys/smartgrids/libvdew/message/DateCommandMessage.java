package de.iisys.smartgrids.libvdew.message;

import de.iisys.libinterface.message.annotation.Callback;
import de.iisys.smartgrids.libvdew.message.content.ReadDate;
import de.iisys.libinterface.message.annotation.MessageTemplate;
import de.iisys.smartgrids.libiec62056.message.CommandMessage;
import de.iisys.smartgrids.libiec62056.message.content.CommandMessageIdentifier;
import de.iisys.smartgrids.libvdew.message.content.VDEWCommandType;
import de.iisys.smartgrids.libvdew.message.content.WriteDate;
import de.iisys.smartgrids.libvdew.service.DateTimeService;
import java.util.Calendar;

/**
 * Class to process the VDEW write and read command with the parsed date.
 *
  */
@MessageTemplate("<SOH>~{1:commandMessageIdentifier}{1:commandType}<STX>[{address}]\\([{value}][*{unit}]\\)[\\({password}\\)]<ETX>:bcc~!bcc!")
@Callback("prepareCommand")
public class DateCommandMessage extends CommandMessage {

    private String password;

    /**
     * Initializes the constructor of {@link CommandMessage}. <br>
     * Identifies the command message as 'read command', sets the read command and 
     * creates a new {@link ReadDate} object.
     * @see de.iisys.smartgrids.libiec62056.message.CommandMessage#CommandMessage(char, de.iisys.smartgrids.libiec62056.message.content.ReadCommand)
     */
    public DateCommandMessage() {
        super(CommandMessageIdentifier.READ_COMMAND, VDEWCommandType.ReadCommand.VDEW_READ, new ReadDate());
    }

    /**
     * Initializes the constructor of {@link CommandMessage}. <br>
     * Identifies the command message as 'write command', sets the write command and 
     * creates a new {@link WriteDate} object. Also initializes {@link #password}
     * with the given password.
     * @see de.iisys.smartgrids.libiec62056.message.CommandMessage#CommandMessage(char, de.iisys.smartgrids.libiec62056.message.content.WriteCommand)
     *
     * @param date the date
     * @param password the password 
     */
    public DateCommandMessage(Calendar date, String password) {
        super(CommandMessageIdentifier.WRITE_COMMAND, VDEWCommandType.WriteCommand.VDEW_WRITE, new WriteDate(date));
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Gets the parsed date.
     *
     * @return parsed date
     */
    public Calendar getDate() {
        return DateTimeService.parseDate(getDataSet().getValue());
    }

}
