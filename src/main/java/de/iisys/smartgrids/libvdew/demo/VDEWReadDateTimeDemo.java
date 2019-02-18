package de.iisys.smartgrids.libvdew.demo;

import de.iisys.smartgrids.libiec62056.client.IEC62056Client;
import de.iisys.smartgrids.libiec62056.message.BreakMessage;
import de.iisys.smartgrids.libiec62056.message.CommandMessage;
import de.iisys.smartgrids.libiec62056.message.content.CommandMessageIdentifier;
import de.iisys.smartgrids.libvdew.message.DateCommandMessage;
import de.iisys.smartgrids.libvdew.message.DateMessage;
import de.iisys.smartgrids.libvdew.message.TimeCommandMessage;
import de.iisys.smartgrids.libvdew.message.TimeMessage;
import de.iisys.smartgrids.libvdew.message.VDEWIdentificationMessage;
import de.iisys.smartgrids.libvdew.protocol.VDEWProtocol;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to read the date and time. <br>
  */
public class VDEWReadDateTimeDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        //Used to log messages.
        Logger logger = Logger.getLogger(IEC62056Client.class.getName());
        
         //Used to publish log records for a named subsystem.
        ConsoleHandler handler = new ConsoleHandler();
        /*
         * Sets the log level specifying which message levels will be logged.
         * Message levels lower than this value will be discarded.
         */
        handler.setLevel(Level.FINE);
        /*
         * Sets the log level specifying which message levels will be logged.
         * Message levels lower than this value will be discarded.
         */
        logger.setLevel(Level.ALL);
       
        //Adds handler
        logger.addHandler(handler);


        //Tries to create the network client with the given ip and port
        try (IEC62056Client iec62056Client = IEC62056Client.createNetworkClient("10.70.7.10", 8000)) {
            //Sets the maximum timeout to 5000
            iec62056Client.setMaxTimeout(5000);

            /*
             * Creates a {@link VDEWProtocol} object with the given {@link iec62056]
             * object and enables the programming mode.
             */
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
             * If the command message is a password command then the command message will be read.
             * After that the date and the time of the message will be printed.
             */
            vdewProtocol.listenTo(CommandMessage.class, (commandMessage) -> {
                if (commandMessage.getCommandMessageIdentifier() == CommandMessageIdentifier.PASSWORD_COMMAND) {
                    vdewProtocol.supplyFor(CommandMessage.class, () -> new DateCommandMessage());

                    vdewProtocol.listenTo(DateMessage.class, (dateMessage) -> {
                        System.out.println(dateMessage + " [" + dateMessage.getDataSet() + "]");

                        vdewProtocol.supplyFor(CommandMessage.class, () -> new TimeCommandMessage());

                        vdewProtocol.listenTo(TimeMessage.class, (timeMessage) -> {
                            System.out.println(timeMessage + " [" + timeMessage.getDataSet() + "]");

                            vdewProtocol.supplyFor(CommandMessage.class, () -> new BreakMessage());
                        });
                    });
                } else {
                    vdewProtocol.supplyFor(CommandMessage.class, () -> new BreakMessage());
                }
            });

            /**
             * Runs the VDEW protocol.
             */
            vdewProtocol.runProtocol();
        }
    }

}
