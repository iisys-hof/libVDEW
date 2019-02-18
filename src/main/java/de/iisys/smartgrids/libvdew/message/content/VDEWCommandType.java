package de.iisys.smartgrids.libvdew.message.content;

import de.iisys.smartgrids.libiec62056.message.content.CommandType;

/**
 * Classes to define the command types for writing and reading.
 *
  */
public class VDEWCommandType {

    /**
     * Defines the command type for writing.
     */
    public static class WriteCommand extends CommandType.WriteCommand {

        /**
         * @see
         * de.iisys.smartgrids.libiec62056.message.content.CommandType.WriteCommand
         */
        public static final char VDEW_WRITE = '5';

    }

    /**
     * Defines the command type for reading.
     */
    public static class ReadCommand extends CommandType.ReadCommand {

        /**
         * @see
         * de.iisys.smartgrids.libiec62056.message.content.CommandType.ReadCommand
         */
        public static final char VDEW_READ = '5';
        /**
         * @see
         * de.iisys.smartgrids.libiec62056.message.content.CommandType.ReadCommand
         */
        public static final char VDEW_BLOCK_READ = '6';

    }

}
