package de.iisys.smartgrids.libvdew.demo;

import de.iisys.smartgrids.libiec62056.client.IEC62056Client;
import de.iisys.smartgrids.libiec62056.message.BreakMessage;
import de.iisys.smartgrids.libiec62056.message.CommandMessage;
import de.iisys.smartgrids.libiec62056.message.ErrorMessage;
import de.iisys.smartgrids.libiec62056.message.content.CommandMessageIdentifier;
import de.iisys.smartgrids.libvdew.message.LoadProfileCommandMessage;
import de.iisys.smartgrids.libvdew.message.LoadProfileMessage;
import de.iisys.smartgrids.libvdew.message.VDEWIdentificationMessage;
import de.iisys.smartgrids.libvdew.message.content.ReadLoadProfile;
import de.iisys.smartgrids.libvdew.protocol.VDEWProtocol;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to read and print the load profile.
  */
public class VDEWReadLoadprofileDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        //Used to log messages
        Logger logger = Logger.getLogger(IEC62056Client.class.getName());
        //Used to publish log records for a named subsystem.
        ConsoleHandler handler = new ConsoleHandler();
        /*
         * Sets the log level specifying which message levels will be logged.
         * Message levels lower than this value will be discarded.
         */
        handler.setLevel(Level.ALL);
        /*
         * Sets the log level specifying which message levels will be logged.
         * Message levels lower than this value will be discarded.
         */
        logger.setLevel(Level.ALL);

        //Adds handler
        logger.addHandler(handler);

        //Tries to create the network client with the given ip and port
        try (IEC62056Client iec62056Client = IEC62056Client.createNetworkClient("10.70.7.10", 8000)) {
            //sets the maximum timeout to 5000
            iec62056Client.setMaxTimeout(5000);

            //Creates new VDEWProtocol and enters the programming mode
            VDEWProtocol vdewProtocol = new VDEWProtocol(iec62056Client, true);

            /*
             * Gets the manufacturer identification,
             * the identification, the protocol mode, the baud rate and whether
             * it is extended mode c or not from the identification message.
             */
            vdewProtocol.listenTo(VDEWIdentificationMessage.class, (identification) -> {
                System.out.println();
                System.out.println("Manufacturer Identification: " + identification.getManufacturerIdentification());
                System.out.println("Identification: " + identification.getIdentification());
                System.out.println("Protocol Mode: " + identification.getProtocolMode());
                System.out.println("Baud Rate: " + identification.getBaudRate());
                System.out.println("Extended Mode C: " + identification.isExtendedModeC());
                System.out.println();
            });
            /*
             * If the command message is a password_command then the calender will be set. After
             * that the load profile will be read from that starting date to the
             * ending date and will be printed.
             */
            vdewProtocol.listenTo(CommandMessage.class, (commandMessage) -> {
                if (commandMessage.getCommandMessageIdentifier() == CommandMessageIdentifier.PASSWORD_COMMAND) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.clear();
                    calendar.set(Calendar.YEAR, 2018);
                    calendar.set(Calendar.MONTH, 10);
                    calendar.set(Calendar.DATE, 21);
                    Calendar start = (Calendar) calendar.clone();
                    calendar.set(Calendar.DATE, 22);
                    Calendar end = (Calendar) calendar.clone();

                    vdewProtocol.supplyFor(CommandMessage.class, () -> new LoadProfileCommandMessage(new ReadLoadProfile(1, start, end)));

                    vdewProtocol.listenTo(LoadProfileMessage.class, (loadProfileMessage) -> {
                        System.out.println(loadProfileMessage);

                        vdewProtocol.supplyFor(CommandMessage.class, () -> new BreakMessage());
                    });

                    vdewProtocol.listenTo(ErrorMessage.class, (errorMessage) -> {
                        System.err.println(errorMessage.getError());

                        vdewProtocol.supplyFor(CommandMessage.class, () -> new BreakMessage());
                    });
                } else {
                    vdewProtocol.supplyFor(CommandMessage.class, () -> new BreakMessage());
                }
            });

            /*
             * Runs the VDEW protocol.
             */
            vdewProtocol.runProtocol();
        }
    }

}
