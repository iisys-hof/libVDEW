package de.iisys.smartgrids.libvdew.message;

import de.iisys.libinterface.message.annotation.Callback;
import de.iisys.libinterface.message.annotation.MessageTemplate;
import de.iisys.smartgrids.libiec62056.message.CommandMessage;
import de.iisys.smartgrids.libiec62056.message.content.CommandMessageIdentifier;
import de.iisys.smartgrids.libvdew.message.content.DeleteLoadProfile;
import de.iisys.smartgrids.libvdew.message.content.ReadLoadProfile;
import de.iisys.smartgrids.libvdew.message.content.VDEWCommandType;
import de.iisys.smartgrids.libvdew.service.DateTimeService;
import java.util.Calendar;

/**
 * Class to read or delete the load profile.
  */
@MessageTemplate("<SOH>~{1:commandMessageIdentifier}{1:commandType}<STX>[{address}]\\([{value}][*{unit}]\\)[\\({password}\\)]<ETX>:bcc~!bcc!")
@Callback("prepareCommand")
public class LoadProfileCommandMessage extends CommandMessage {

    private String password;

    /**
     * Creates a new {@link ReadLoadProfile} and  gives it to 
     * {@link #LoadProfileCommandMessage(de.iisys.smartgrids.libvdew.message.content.ReadLoadProfile).
     */
    public LoadProfileCommandMessage() {
        this(new ReadLoadProfile());
    }

    /**
     * Initializes the constructor of {@link CommandMessage}. <br>
     * Identifies the command message as 'read command', sets the read command and reads the
     * load profile.
     *
     * @param readLoadProfile reads the load profile
     * @see
     * de.iisys.smartgrids.libiec62056.message.CommandMessage#CommandMessage(char,
     * de.iisys.smartgrids.libiec62056.message.content.ReadCommand)
     */
    public LoadProfileCommandMessage(ReadLoadProfile readLoadProfile) {
        super(CommandMessageIdentifier.READ_COMMAND, VDEWCommandType.ReadCommand.VDEW_READ, readLoadProfile);
    }

    /**
     * Creates a new {@link DeleteLoadProfile] and gives it to 
     * {@link #LoadProfileCommandMessage(de.iisys.smartgrids.libvdew.message.content.DeleteLoadProfile, java.lang.String)}
     * with the given password.
     *
     * @param password passwort for the command message
     */
    public LoadProfileCommandMessage(String password) {
        this(new DeleteLoadProfile(), password);
    }

    /**
     * Initializes the constructor of {@link DeleteLoadProfile} with the given
     * load profile to delete. Also initializes the {@link #password} with the
     * given password.
     *
     * @param deleteLoadProfile load profile to delete
     * @param password password for the command message
     */
    public LoadProfileCommandMessage(DeleteLoadProfile deleteLoadProfile, String password) {
        super(CommandMessageIdentifier.WRITE_COMMAND, VDEWCommandType.WriteCommand.VDEW_WRITE, deleteLoadProfile);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Gets the date.
     * @return date
     */
    public Calendar getDate() {
        return DateTimeService.parseDate(getDataSet().getValue());
    }

}
